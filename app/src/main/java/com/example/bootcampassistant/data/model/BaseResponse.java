package com.example.bootcampassistant.data.model;

public class BaseResponse {
    private UserResponse data;

    public UserResponse getUserResponse() {
        return data;
    }

    @Override
    public String toString() {
        return
                "BaseResponse{" +
                        "data = '" + data + '\'' +
                        "}";
    }
}
