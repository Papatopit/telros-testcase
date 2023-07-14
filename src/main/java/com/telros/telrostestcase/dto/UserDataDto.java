package com.telros.telrostestcase.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.telros.telrostestcase.model.role.EnumRole;
import com.telros.telrostestcase.model.role.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * dto для создания нового пользователя
 */
@RequiredArgsConstructor
@Getter
public class UserDataDto {

    private final String login;
    private final String password;

    private final String email;
    private final String lastname;
    private final String firstname;
    private final String surname;
    private final String phone;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final Date birthdate;
    @Enumerated(EnumType.STRING)
    private final Set<EnumRole> roles;

    public Set<Role> convertToRoleSet() {
        return roles.stream().map(r -> new Role(r)).collect(Collectors.toSet());
    }
}