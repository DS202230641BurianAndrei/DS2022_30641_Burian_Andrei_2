package com.example.ds2022_30641_burian_andrei_2;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Message {
    private String timestamp;
    private float measurement;
    private Long deviceId;

    @Override
    public String toString(){

        return new Gson().toJson(this);
    }
}
