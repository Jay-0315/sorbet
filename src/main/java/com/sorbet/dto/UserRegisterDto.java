// src/main/java/com/sorbet/dto/UserRegisterDto.java
package com.sorbet.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
@Getter
@Setter
public class UserRegisterDto {


    @NotBlank(message = "아이디는 필수입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$",
            message = "4~20자의 영문자 및 숫자만 사용할 수 있습니다.")
    private String userId;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*?])[A-Za-z\\d!@#$%^&*?]{8,16}$",
            message = "8~16자,영문자,숫자,특수문자를 포함해야 합니다.")

    private String password;

    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일은 필수입니다.")
    private String email;
}

