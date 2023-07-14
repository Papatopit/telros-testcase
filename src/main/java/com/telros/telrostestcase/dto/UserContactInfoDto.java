package com.telros.telrostestcase.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * dto с контактной информацией о пользователе
 */
@RequiredArgsConstructor
@Getter
public class UserContactInfoDto {
    private final String lastname;
    private final String firstname;
    private final String surname;
    private final String email;
    private final String phone;
    private final String photo;
}

