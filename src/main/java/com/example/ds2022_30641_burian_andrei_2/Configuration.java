package com.example.ds2022_30641_burian_andrei_2;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class Configuration {
    @Value("${deviceId}")
    private Long deviceId;
}
