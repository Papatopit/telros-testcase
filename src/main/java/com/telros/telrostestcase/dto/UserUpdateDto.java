package com.telros.telrostestcase.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * dto для обновления информации пользователя
 */
@RequiredArgsConstructor
@Getter
public class UserUpdateDto {

    private final String login;
    private final String password;

    private final String email;
    private final String lastname;
    private final String firstname;
    private final String surname;
    private final String phone;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final Date birthdate;
}


