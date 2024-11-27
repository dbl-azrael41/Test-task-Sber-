package com.task.crud.service.implementation;

import com.task.crud.dao.PhoneDAO;
import com.task.crud.entity.Phone;
import com.task.crud.exceptions.IncorrectPhoneDataException;
import com.task.crud.exceptions.NoSuchPhoneException;
import com.task.crud.service.PhoneService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Сервис для работы с объектами {@link Phone}.
 * Предоставляет операции для выполнения бизнес-логики приложения с объектами {@link Phone}.
 *
 * @author Zabnev Konstantin
 * @version 1.0
 * @since 2024-11-26
 */
@Service
@Transactional
public class PhoneServiceImpl implements PhoneService {
    /**
     * Репозиторий для работы с объектами {@link Phone} на уровне доступа к данным.
     *
     * @see PhoneDAO
     */
    @Autowired
    private PhoneDAO phoneDAO;

    private void validatePhoneData(Phone phone) throws IncorrectPhoneDataException {
        if (phone == null) {
            throw new IncorrectPhoneDataException("Phone cannot be null");
        }
        if (phone.getId() != null) {
            throw new IncorrectPhoneDataException("Undefined property 'id'");
        }
        if (phone.getModel() == null || phone.getModel().isEmpty() || phone.getModel().length() > 256) {
            throw new IncorrectPhoneDataException("Incorrect value for 'model'");
        }
        if (phone.getBrand() == null || phone.getBrand().isEmpty() || phone.getBrand().length() > 256) {
            throw new IncorrectPhoneDataException("Incorrect value for 'brand'");
        }
        if (phone.getPrice() < 0) {
            throw new IncorrectPhoneDataException("Incorrect value for 'price'");
        }
    }

    /**
     * {@link PhoneService#getAllPhones()}
     */
    @Override
    public List<Phone> getAllPhones() {
        return phoneDAO.getAllPhones();
    }

    /**
     * {@link PhoneService#getPhone(Long)}
     */
    @Override
    public Phone getPhone(Long id) {
        Phone phone = phoneDAO.getPhone(id);

        // Проверка наличия в базе записи с нужным id
        if (phone == null) {
            throw new NoSuchPhoneException("There is no phone with 'id' = " + id);
        }

        return phone;
    }

    /**
     * {@link PhoneService#addPhone(Phone)}
     */
    @Override
    public Phone addPhone(Phone phone) {
        // Валидация данных
        validatePhoneData(phone);

        return phoneDAO.savePhone(phone);
    }

    /**
     * {@link PhoneService#addPhone(Phone)}
     */
    @Override
    public Phone updatePhone(Long id, Phone phone) {
        // Получение из базы объекта, который будет обновлен
        Phone oldPhone = getPhone(id);

        // Валидация данных
        validatePhoneData(phone);

        oldPhone.setBrand(phone.getBrand());
        oldPhone.setModel(phone.getModel());
        oldPhone.setPrice(phone.getPrice());

        return phoneDAO.savePhone(oldPhone);
    }

    /**
     * {@link PhoneService#deletePhone(Long)}
     */
    @Override
    public void deletePhone(Long id) {
        // Проверка наличия в базе записи с нужным id
        getPhone(id);

        phoneDAO.deletePhone(id);
    }
}
