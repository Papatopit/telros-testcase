package com.telros.telrostestcase.service;

import com.telros.telrostestcase.dto.UserContactInfoDto;
import com.telros.telrostestcase.dto.UserUpdateDto;
import com.telros.telrostestcase.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService extends UserDetailsService {

    User findUserByLogin(String login);

    User findUserById(long id);

    Page<UserContactInfoDto> getUsers(String text, Pageable pageable);

    User saveUser(User user);

    User updateUser(String login, UserUpdateDto userUpdateDto);

    void deleteUserByLogin(String login);

    void loadPhoto(String login, String filename);
}
