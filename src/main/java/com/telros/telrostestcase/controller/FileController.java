package com.telros.telrostestcase.controller;

import com.telros.telrostestcase.service.PhotoService;
import com.telros.telrostestcase.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;


@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@Slf4j
public class FileController {

    private final PhotoService storageService;
    private final UserService userService;

    /**
     * Загрузка аватара текущему пользователю
     *
     * @param file      - файл картинки
     * @param principal - интерфейс, получаемый из запроса и предоставляющий логин текущего пользователя
     * @return строка с подтверждением успешной загрузки
     */
    @PostMapping("/photo")
    public ResponseEntity<?> uploadAvatar(@RequestParam("photo") MultipartFile file, Principal principal) {
        String filename = storageService.uploadPhoto(file);
        userService.loadPhoto(principal.getName(), filename);
        log.info("Фото {} для юзер {} загружено",filename,principal.getName());
        return ResponseEntity.ok().body(principal.getName() + ", ваше фото загружено.");
    }

    /**
     * Скачивание файла фото
     *
     * @param filename - имя файла
     * @return файл .jpeg
     */
    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFileByFilename(@PathVariable String filename) {
        Resource resource = storageService.loadAsResource(filename);
        log.info("Скачивание файла: {}", filename);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    /**
     * Удаление файла
     *
     * @param filename - имя файла
     * @param login    - логин пользователя, у которого нужно удалить аватар
     * @return строка с подтверждением удаления
     */
    @DeleteMapping("/{filename:.+}/{login}")
    public ResponseEntity<String> deletePhotoByFilenameAndLogin(@PathVariable String filename,
                                                                 @PathVariable String login) {
        storageService.deleteFile(filename);
        userService.loadPhoto(login, null);
        log.info("Файл " + filename + " удален.");
        return ResponseEntity.ok("Файл " + filename + " удален.");
    }
}
