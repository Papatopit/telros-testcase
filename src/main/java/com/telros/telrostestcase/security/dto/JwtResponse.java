package com.telros.telrostestcase.security.dto;

import com.telros.telrostestcase.model.role.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;


@RequiredArgsConstructor
@Getter
public class JwtResponse {

    private final String token;
    private final String type = "Bearer";
    private final Long id;
    private final String login;
    private final Set<Role> role;

}