package com.example.common.dto;


import com.example.common.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {

    private int id;
    private String name;
    private Category category;
    private String picUrl;
    private String duration;
    private boolean isLiked;

}
