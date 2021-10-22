package com.example.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Data
@ConfigurationProperties("food")
public class FoodProperties {

    private String foodImg;

    private Long fileMaxSize;
}

