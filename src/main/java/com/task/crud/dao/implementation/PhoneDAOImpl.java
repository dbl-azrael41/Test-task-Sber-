package com.task.crud.dao.implementation;

import com.task.crud.dao.PhoneDAO;
import com.task.crud.entity.Phone;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с объектами {@link Phone}.
 * Предоставляет операции для взаимодействия с таблицей базы данных "phone".
 *
 * @author Zabnev Konstantin
 * @version 1.0
 * @since 2024-11-26
 */
@Repository
public class PhoneDAOImpl implements PhoneDAO {
    /**
     * Объект EntityManager для взаимодействия с базой данных.
     */
    @Autowired
    private EntityManager entityManager;

    /**
     * {@link PhoneDAO#getAllPhones()}
     */
    @Override
    public List<Phone> getAllPhones() {
        Session session = entityManager.unwrap(Session.class);

        Query query = session.createQuery("from Phone", Phone.class);

        return query.getResultList();
    }

    /**
     * {@link PhoneDAO#getPhone(Long)}
     */
    @Override
    public Phone getPhone(Long id) {
        Session session = entityManager.unwrap(Session.class);

        return session.get(Phone.class, id);
    }

    /**
     * {@link PhoneDAO#savePhone(Phone)}
     */
    @Override
    public Phone savePhone(Phone phone) {
        Session session = entityManager.unwrap(Session.class);

        Phone newPhone = session.merge(phone);
        phone.setId(newPhone.getId());

        return phone;
    }

    /**
     * {@link PhoneDAO#deletePhone(Long)}
     */
    @Override
    public void deletePhone(Long id) {
        Query query = entityManager.createQuery("delete from Phone where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
