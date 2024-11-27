package com.task.crud.service.implementation;

import com.task.crud.exceptions.IncorrectPhoneDataException;
import com.task.crud.exceptions.NoSuchPhoneException;
import com.task.crud.dao.PhoneDAO;
import com.task.crud.entity.Phone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PhoneServiceImplTest {
    @Mock
    private PhoneDAO phoneDAO;

    @InjectMocks
    private PhoneServiceImpl phoneService;

    private Phone validPhone;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validPhone = new Phone(null, "Apple", "iPhone 14", 999.99);
    }

    @Test
    void testGetAllPhones() {
        List<Phone> phones = List.of(
                new Phone(1L, "Apple", "iPhone 14", 999.99),
                new Phone(2L, "Samsung", "Galaxy S23", 899.99)
        );
        when(phoneDAO.getAllPhones()).thenReturn(phones);

        List<Phone> result = phoneService.getAllPhones();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(phoneDAO, times(1)).getAllPhones();
    }

    @Test
    void testGetPhone_Success() {
        Phone phone = new Phone(1L, "Apple", "iPhone 14", 999.99);
        when(phoneDAO.getPhone(1L)).thenReturn(phone);

        Phone result = phoneService.getPhone(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(phoneDAO, times(1)).getPhone(1L);
    }

    @Test
    void testGetPhone_Error() {
        when(phoneDAO.getPhone(999L)).thenReturn(null);

        NoSuchPhoneException exception = assertThrows(NoSuchPhoneException.class, () -> {
            phoneService.getPhone(999L);
        });
        assertEquals("There is no phone with 'id' = 999", exception.getMessage());
    }

    @Test
    void testAddPhone_Success() {
        when(phoneDAO.savePhone(validPhone)).thenReturn(validPhone);

        Phone result = phoneService.addPhone(validPhone);

        assertNotNull(result);
        assertEquals(validPhone, result);
        verify(phoneDAO, times(1)).savePhone(validPhone);
    }

    @Test
    void testAddPhone_Error() {
        Phone invalidPhone = new Phone(null, "Apple", "", -999.99); // Invalid phone data

        IncorrectPhoneDataException exception = assertThrows(IncorrectPhoneDataException.class, () -> {
            phoneService.addPhone(invalidPhone);
        });
        assertEquals("Incorrect value for 'model'", exception.getMessage());
    }

    @Test
    void testUpdatePhone_Success() {
        Phone existingPhone = new Phone(1L, "Apple", "iPhone 12", 899.99);
        Phone updatedPhone = new Phone(null, "Apple", "iPhone 14", 999.99);
        when(phoneDAO.getPhone(1L)).thenReturn(existingPhone);
        when(phoneDAO.savePhone(existingPhone)).thenReturn(existingPhone);

        Phone result = phoneService.updatePhone(1L, updatedPhone);

        assertNotNull(result);
        assertEquals("iPhone 14", result.getModel());
        verify(phoneDAO, times(1)).savePhone(existingPhone);
    }

    @Test
    void testUpdatePhone_Error() {
        Phone updatedPhone = new Phone(null, "Apple", "iPhone 14", 999.99);
        when(phoneDAO.getPhone(1L)).thenReturn(null);

        NoSuchPhoneException exception = assertThrows(NoSuchPhoneException.class, () -> {
            phoneService.updatePhone(1L, updatedPhone);
        });
        assertEquals("There is no phone with 'id' = 1", exception.getMessage());
    }

    @Test
    void testDeletePhone_Success() {
        when(phoneDAO.getPhone(1L)).thenReturn(new Phone(1L, "Apple", "iPhone 14", 999.99));

        phoneService.deletePhone(1L);

        verify(phoneDAO, times(1)).deletePhone(1L);
    }

    @Test
    void testDeletePhone_Error() {
        when(phoneDAO.getPhone(999L)).thenReturn(null);

        NoSuchPhoneException exception = assertThrows(NoSuchPhoneException.class, () -> {
            phoneService.deletePhone(999L);
        });
        assertEquals("There is no phone with 'id' = 999", exception.getMessage());
    }
}
