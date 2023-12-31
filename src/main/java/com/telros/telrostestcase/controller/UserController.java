package com.telros.telrostestcase.controller;


import com.telros.telrostestcase.dto.UserContactInfoDto;
import com.telros.telrostestcase.dto.UserUpdateDto;
import com.telros.telrostestcase.model.User;
import com.telros.telrostestcase.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * Получение контактной информации всех пользователей с учетом опциональных параметров
     *
     * @param text  - ключевое слово, по которому можно осуществить поиск пользователей
     * @param sort  - атрибут, по которому сортируется список
     * @param order - порядок элементов (DESC или ASC)
     * @param page  - номер страницы с 0
     * @param size  - количество элементов на странице
     * @return JSON со списком типа UserContactInfoDto в теле ответа
     */
    @GetMapping("/all")
    public ResponseEntity<List<UserContactInfoDto>> getUsersContactInfo(
            @RequestParam(value = "text", required = false, defaultValue = "") String text,
            @RequestParam(value = "sort", required = false, defaultValue = "id") String sort,
            @RequestParam(value = "order", required = false, defaultValue = "DESC") String order,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.valueOf(order), sort));
        log.info("Получение всех юзеров");
        return ResponseEntity.ok().body(userService.getUsers(text, pageable).getContent());
    }

    /**
     * Получение детальной информации о пользователе по user_id
     *
     * @param id - id пользователя
     * @return JSON с детальной информацией о пользователе
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        log.info("Получение детальной информации о пользователе по ID: {}", userService.findUserById(id));
        return ResponseEntity.ok().body(userService.findUserById(id));
    }

    /**
     * Получение детальной информации о пользователе по login
     *
     * @param login - логин пользователя
     * @return JSON с детальной информацией о пользователе
     */
    @GetMapping("/by-login/{login}")
    public ResponseEntity<User> getUserByLogin(@PathVariable("login") String login) {
        log.info("Получение детальной информации о пользователе по LOGIN: {}", userService.findUserByLogin(login));
        return ResponseEntity.ok().body(userService.findUserByLogin(login));
    }


    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        log.info("Получение текущего пользователя {}", principal);
        return ResponseEntity.ok(userService.findUserByLogin(principal.getName()));
    }

    /**
     * Обновление всех полей текущего пользователя, кроме user_id и roles
     *
     * @param userUpdateDto - dto с полями для обновления
     * @param principal     - интерфейс, получаемый из запроса и предоставляющий логин текущего пользователя
     * @return JSON с обновленной детальной информацией о пользователе
     */
    @PutMapping("/current")
    public ResponseEntity<User> updateCurrentUser(@RequestBody UserUpdateDto userUpdateDto, Principal principal) {
        User user = userService.updateUser(principal.getName(), userUpdateDto);
        SecurityContextHolder.clearContext();
        log.info("Обновление всех полей текущего пользователя, кроме user_id и roles");
        return ResponseEntity.ok().body(user);
    }

    /**
     * Удаление текущего пользователя
     *
     * @param principal - интерфейс, получаемый из запроса и предоставляющий логин текущего пользователя
     * @return строка с подтверждением удаления
     */
    @DeleteMapping("/current")
    public ResponseEntity<String> deleteCurrentUser(Principal principal) {
        userService.deleteUserByLogin(principal.getName());
        SecurityContextHolder.clearContext();
        log.info("Пользователь " + principal.getName() + " удалён.");
        return ResponseEntity.ok().body("Пользователь " + principal.getName() + " удалён.");
    }

    /**
     * Обновление всех полей пользователя по его логину, кроме user_id и roles (доступно только для администратора)
     *
     * @param login         - логин пользователя
     * @param userUpdateDto - dto с полями для обновления
     * @return JSON с обновленной детальной информацией о пользователе
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{login}")
    public ResponseEntity<User> updateUserByLogin(@PathVariable("login") String login,
                                                  @RequestBody UserUpdateDto userUpdateDto) {
        log.info("Обновление всех полей пользователя по его логину, кроме user_id и roles");
        return ResponseEntity.ok().body(userService.updateUser(login, userUpdateDto));
    }

    /**
     * Удаление пользователя по его логину (доступно только для администратора)
     *
     * @param login - логин пользователя
     * @return строка с подтверждением удаления
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{login}")
    public ResponseEntity<String> deleteUserByLogin(@PathVariable("login") String login) {
        userService.deleteUserByLogin(login);
        log.info("Пользователь " + login + " удалён.");
        return ResponseEntity.ok().body("Пользователь " + login + " удалён.");
    }
}
