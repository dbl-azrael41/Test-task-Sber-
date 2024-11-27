package com.task.crud.dao;

import com.task.crud.entity.Phone;

import java.util.List;

/**
 * Интерфейс для работы с объектами {@link Phone} на уровне доступа к данным.
 *
 * Этот интерфейс предоставляет методы для выполнения CRUD операций с данными для объектов {@link Phone}.
 * Реализация этого интерфейса будет отвечать за прямое взаимодействие с базой данных.
 *
 * @author Zabnev Konstantin
 * @version 1.0
 * @since 2024-11-26
 */
public interface PhoneDAO {
    /**
     * Возвращает список всех телефонов из базы данных.
     *
     * @return список объектов {@link Phone}.
     */
    public List<Phone> getAllPhones();

    /**
     * Возвращает телефон по его идентификатору из базы данных.
     *
     * @param id идентификатор телефона. Должен быть положительным.
     * @return объект {@link Phone} с указанным идентификатором или {@code null}, если телефон не найден.
     */
    public Phone getPhone(Long id);

    /**
     * Сохраняет или обновляет объект телефона в базе данных.
     *
     * @param phone объект {@link Phone} для сохранения.
     * @return обновлённый объект {@link Phone}.
     */
    public Phone savePhone(Phone phone);

    /**
     * Удаляет телефон по его идентификатору из базы данных.
     *
     * @param id идентификатор телефона.
     */
    public void deletePhone(Long id);
}
