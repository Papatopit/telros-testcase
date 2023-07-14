package com.telros.telrostestcase.security.service.impl;

import com.telros.telrostestcase.dto.UserDataDto;
import com.telros.telrostestcase.model.User;
import com.telros.telrostestcase.model.role.Role;
import com.telros.telrostestcase.repository.RoleRepo;
import com.telros.telrostestcase.repository.UserRepo;
import com.telros.telrostestcase.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(UserDataDto registrationRequest) {
        User user = new User(registrationRequest);

        if (userRepo.existsByLogin(registrationRequest.getLogin())) {
            throw new EntityExistsException("Пользователь с login: " + registrationRequest.getLogin() + " уже существует!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> roles = registrationRequest.getRoles().stream()
                .map(r -> roleRepo.findByRoleName(r)
                        .orElseThrow(() ->
                                new EntityNotFoundException("Роль " + r.name() + " не найдена!")))
                .collect(Collectors.toSet());

        user.setRoles(roles);
        log.info("Сохранение Юзера после регистрации: {}",user);
        return userRepo.save(user);
    }
}
