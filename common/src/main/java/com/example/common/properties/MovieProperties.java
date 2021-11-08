package com.example.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("movie")
public class MovieProperties {

    private String movieImg;


}