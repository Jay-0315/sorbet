// src/main/java/com/sorbet/dto/UserRegisterDto.java
package com.sorbet.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDto {
    private String userId;
    private String password;
    private String nickname;
    private String email;
}
