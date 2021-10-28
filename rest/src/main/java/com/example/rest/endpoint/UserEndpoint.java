package com.example.rest.endpoint;

import com.example.common.entity.User;
import com.example.common.service.UserService;
import com.example.rest.dto.UserAuthDto;
import com.example.rest.dto.UserAuthResponseDto;
import com.example.rest.dto.UserDto;
import com.example.rest.dto.UserSaveDto;
import com.example.rest.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserEndpoint {
    private  final UserService userService;
    private  final ModelMapper mapper;
    private  final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping("/users")
    public List<UserDto> getUsers(){
        List<User> all = userService.getAllUsers();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user:all) {
            UserDto userDto = mapper.map(user, UserDto.class);
            userDtos.add(userDto);
        }
        return userDtos;
    }
    @PostMapping("/sign-up")
    public UserDto user(@RequestParam UserSaveDto user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return mapper.map(userService.add(mapper.map(user, User.class)),UserDto.class);
    }
    @PostMapping("/users/auth")
    public ResponseEntity<?> auth(@RequestBody UserAuthDto userAuthDto){
        Optional<User> byEmail = userService.findByEmail(userAuthDto.getEmail());
        if (byEmail.isEmpty()){
          return ResponseEntity
                  .status(HttpStatus.UNAUTHORIZED)
                  .build();
        }
        User user = byEmail.get();
        if(passwordEncoder.matches(userAuthDto.getPassword(),user.getPassword())){
            return ResponseEntity.ok(UserAuthResponseDto.builder()
                    .token(jwtTokenUtil.generateToken(user.getEmail()))
                    .userDto(mapper.map(user, UserDto.class))
                    .build());
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .build();
    }

}
