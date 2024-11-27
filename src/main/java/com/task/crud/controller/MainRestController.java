package com.task.crud.controller;

import com.task.crud.entity.Phone;
import com.task.crud.response_templates.ApiResponse;
import com.task.crud.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для работы с объектами {@link Phone} через REST API.
 * Обрабатывает HTTP-запросы и предоставляет соответствующие операции для работы с телефонами.
 *
 * Контроллер включает в себя методы для CRUD операций через RESTful сервис.
 *
 * @author Zabnev Konstantin
 * @version 1.0
 * @since 2024-11-26
 */
@RestController
@RequestMapping("/api")
public class MainRestController {
    /**
     * Сервис для работы с объектами {@link Phone}, предоставляющий бизнес-логику для CRUD операций.
     * Этот сервис используется для обработки запросов и выполнения операций с телефонами.
     */
    @Autowired
    private PhoneService phoneService;

    /**
     * Возвращает список всех телефонов.
     *
     * @return JSON, который содержит код состояние HTTP ответа и список объектов {@link Phone}.
     */
    @GetMapping("/phones")
    public ResponseEntity<ApiResponse<List<Phone>>> getAllPhones() {
        List<Phone> allPhones =  phoneService.getAllPhones();
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), allPhones));
    }

    /**
     * Возвращает телефон по его идентификатору.
     *
     * @param id идентификатор телефона.
     * @return JSON, который содержит код состояние HTTP ответа и объект {@link Phone}.
     */
    @GetMapping("/phones/{id}")
    public ResponseEntity<ApiResponse<Phone>> getPhone(@PathVariable Long id) {
        Phone phone = phoneService.getPhone(id);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), phone));
    }

    /**
     * Сохраняет новый объект {@link Phone} в базе данных.
     *
     * @param phone объект {@link Phone}, который необходимо сохранить.
     * @return JSON, который содержит код состояние HTTP ответа и сохранённый объект {@link Phone}.
     */
    @PostMapping("/phones")
    public ResponseEntity<ApiResponse<Phone>> savePhone(@RequestBody Phone phone) {
        Phone newPhone = phoneService.addPhone(phone);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), newPhone));
    }

    /**
     * Обновляет существующий объект {@link Phone} по его идентификатору.
     *
     * @param id идентификатор обновляемого объекта.
     * @param phone объект {@link Phone} с новыми данными.
     *
     * @return JSON, который содержит код состояние HTTP ответа и обновлённый объект {@link Phone}.
     */
    @PutMapping("/phones/{id}")
    public ResponseEntity<ApiResponse<Phone>> updatePhone(@PathVariable Long id, @RequestBody Phone phone) {
        Phone newPhone = phoneService.updatePhone(id, phone);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), newPhone));
    }

    /**
     * Удаляет телефон по его идентификатору.
     *
     * @param id идентификатор телефона, который нужно удалить.
     * @return JSON, который содержит код состояние HTTP ответа и информирование об успешном удалении.
     */
    @DeleteMapping("/phones/{id}")
    public ResponseEntity<ApiResponse<String>> deletePhone(@PathVariable Long id) {
        phoneService.deletePhone(id);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), String.format("Phone with id = %d was successfully deleted", id)));
    }

    /**
     * Отливливает выбрасываемые в процессе работы исключения.
     *
     * @param e объект выбрашенного {@link Exception}.
     * @return JSON, который содержит код состояние HTTP ответа и описание ошибки.
     */
    @ExceptionHandler
    public ResponseEntity<ApiResponse<String>> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }
}
