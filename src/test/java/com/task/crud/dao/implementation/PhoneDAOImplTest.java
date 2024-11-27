package com.task.crud.dao.implementation;

import com.task.crud.entity.Phone;
import jakarta.persistence.EntityManager;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class PhoneDAOImplTest {
    @Mock
    private EntityManager entityManager;

    @Mock
    private Session session;

    @Mock
    private Query<Phone> query;

    @InjectMocks
    private PhoneDAOImpl phoneDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(entityManager.unwrap(Session.class)).thenReturn(session);
    }

    @Test
    void testGetAllPhones() {
        List<Phone> mockPhones = List.of(
                new Phone(1L, "Apple", "iPhone 14", 999.99),
                new Phone(2L, "Samsung", "Galaxy S23", 899.99)
        );

        when(session.createQuery("from Phone", Phone.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(mockPhones);

        List<Phone> result = phoneDAO.getAllPhones();

        assertNotNull(result);
        assertEquals(2, result.size());

        Phone firstPhone = result.get(0);
        assertEquals(1L, firstPhone.getId());
        assertEquals("Apple", firstPhone.getBrand());
        assertEquals("iPhone 14", firstPhone.getModel());
        assertEquals(999.99, firstPhone.getPrice());

        verify(session).createQuery("from Phone", Phone.class);
        verify(query).getResultList();
    }

    @Test
    void testGetPhone() {
        Phone mockPhone = new Phone(1L, "Apple", "iPhone 14", 999.99);

        when(session.get(Phone.class, 1)).thenReturn(mockPhone);

        Phone result = phoneDAO.getPhone(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Apple", result.getBrand());
        assertEquals("iPhone 14", result.getModel());
        assertEquals(999.99, result.getPrice());

        verify(session).get(Phone.class, 1);
    }

    @Test
    void testSavePhone() {
        Phone newPhone = new Phone(null, "Google", "Pixel 8", 799.99);
        Phone savedPhone = new Phone(1L, "Google", "Pixel 8", 799.99);

        when(session.merge(newPhone)).thenReturn(savedPhone);

        Phone result = phoneDAO.savePhone(newPhone);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Google", result.getBrand());
        assertEquals("Pixel 8", result.getModel());
        assertEquals(799.99, result.getPrice());

        verify(session).merge(newPhone);
    }

    @Test
    void testDeletePhone() {
        Long phoneId = 1L;

        when(session.createQuery("delete from Phone where id = :id")).thenReturn(query);

        phoneDAO.deletePhone(phoneId);

        verify(query).setParameter("id", phoneId);
        verify(query).executeUpdate();
    }
}
