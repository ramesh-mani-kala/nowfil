package com.turf.playarena.dto;

import lombok.Data;

import java.util.List;

@Data
public class SignUpDto {

    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String roles;
}
