package com.telros.telrostestcase.security.service;


import com.telros.telrostestcase.dto.UserDataDto;
import com.telros.telrostestcase.model.User;

public interface AuthService {

    User register(UserDataDto registrationRequest);
}
