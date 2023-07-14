package com.telros.telrostestcase.service.impl;

import com.telros.telrostestcase.dto.UserContactInfoDto;
import com.telros.telrostestcase.dto.UserUpdateDto;
import com.telros.telrostestcase.model.User;
import com.telros.telrostestcase.repository.TokenRepo;
import com.telros.telrostestcase.repository.UserRepo;
import com.telros.telrostestcase.service.PhotoService;
import com.telros.telrostestcase.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepo tokenRepo;
    private final PhotoService storageService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return findUserByLogin(login);
    }

    @Override
    public User findUserByLogin(String login) {
        return userRepo.findByLogin(login)
                .orElseThrow(() ->
                        new EntityNotFoundException("Пользователь с login: " + login + " не найден!"));
    }

    @Override
    public User findUserById(long id) {
        return userRepo.findUserById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с id = " + id + " не найден!"));
    }

    @Override
    public PageImpl<UserContactInfoDto> getUsers(String text, Pageable pageable) {
        return new PageImpl<>(userRepo.findAll(text, pageable).stream().map(
                u -> new UserContactInfoDto(
                        u.getLastname(), u.getFirstname(),
                        u.getSurname(), u.getEmail(),
                        u.getPhone(), u.getPhoto())).collect(Collectors.toList()));
    }

    @Override
    public User saveUser(User user) {
        try {
            return userRepo.save(user);

        } catch (RuntimeException e) {
            throw new PersistenceException(String.format("Пользователь %s не сохранён! " +
                    "Error: [%s]", user.getLogin(), e));
        }
    }

    @Override
    public User updateUser(String login, UserUpdateDto userUpdateDto) {
        User user = findUserByLogin(login);

        tokenRepo.deleteByUser(user);

        user.setLogin(userUpdateDto.getLogin());
        user.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));

        user.setEmail(userUpdateDto.getEmail());
        user.setLastname(userUpdateDto.getLastname());
        user.setFirstname(userUpdateDto.getFirstname());
        user.setSurname(userUpdateDto.getSurname());
        user.setPhone(userUpdateDto.getPhone());
        user.setBirthday(userUpdateDto.getBirthdate());

        return saveUser(user);
    }

    @Override
    public void deleteUserByLogin(String login) {
        User user = findUserByLogin(login);
        userRepo.delete(user);
        tokenRepo.deleteByUser(user);
        storageService.deleteFile(user.getPhoto());
    }

    @Override
    public void loadPhoto(String login, String filename) {
        User user = findUserByLogin(login);
        user.setPhoto(filename);
        saveUser(user);
    }
}
