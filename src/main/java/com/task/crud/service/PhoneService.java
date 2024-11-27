package com.task.crud.service;

import com.task.crud.entity.Phone;
import com.task.crud.exceptions.IncorrectPhoneDataException;
import com.task.crud.exceptions.NoSuchPhoneException;

import java.util.List;

/**
 * Интерфейс для предоставления методов работы с объектами {@link Phone} в рамках бизнес-логики приложения.
 *
 * Этот интерфейс определяет основные (CRUD) операции с объектами {@link Phone}.
 * Реализация этого интерфейса реализует бизнес-логику для работы с телефонами.
 *
 * @author Zabnev Konstantin
 * @version 1.0
 * @since 2024-11-26
 */
public interface PhoneService {
    /**
     * Возвращает список всех телефонов из базы данных.
     *
     * @return список объектов {@link Phone}.
     */
    public List<Phone> getAllPhones();

    /**
     * Возвращает телефон по его идентификатору.
     *
     * @param id идентификатор телефона. Должен быть положительным.
     * @return объект {@link Phone} с указанным идентификатором или {@code null}, если телефон не найден.
     * @exception NoSuchPhoneException выбрасывается, если в базе не найдена запись с передаваемым идентификатором
     */
    public Phone getPhone(Long id);

    /**
     * Сохраняет новую запись в базе данных.
     *
     * @param phone объект {@link Phone} для сохранения.
     * @return обновлённый объект {@link Phone}.
     * @exception IncorrectPhoneDataException выбрасывается, если предоставленные данные объекта {@link Phone} некорректны или нарушают бизнес-логику.
     */
    public Phone addPhone(Phone phone);

    /**
     * Сохраняет новую запись в базе данных.
     *
     * @param phone объект {@link Phone} с новыми данными.
     * @param id идентификатор обновляемого объекта {@link Phone}.
     * @return обновлённый объект {@link Phone}.
     * @exception IncorrectPhoneDataException выбрасывается, если предоставленные данные объекта {@link Phone} некорректны или нарушают бизнес-логику.
     */
    public Phone updatePhone(Long id, Phone phone);

    /**
     * Удаляет телефон по его идентификатору.
     *
     * @param id идентификатор телефона.
     * @exception NoSuchPhoneException выбрасывается, если в базе не найдена запись с передаваемым идентификатором
     */
    public void deletePhone(Long id);
}
