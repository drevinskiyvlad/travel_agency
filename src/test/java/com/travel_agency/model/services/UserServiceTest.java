package com.travel_agency.model.services;

import com.travel_agency.utils.exceptions.DAOException;
import com.travel_agency.utils.exceptions.ValidationException;
import com.travel_agency.model.DB.DAO.UserDAO;
import com.travel_agency.model.DTO.UserDTO;
import com.travel_agency.model.entity.User;
import com.travel_agency.utils.HashPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserDAO<User> dao;

    @InjectMocks
    private UserService service;

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    public void setUp() {
        userDTO = new UserDTO("test@email.com", "user", "John", "Doe", "+1234567890", false);
        user = new User(1, "test@email.com", "password", "user", "John", "Doe", "+1234567890", false);
    }

    @Test
    void testAddUser() throws ValidationException, DAOException {
        when(dao.create(user)).thenReturn(true);

        service.signUp(userDTO, "password");

        verify(dao).create(user);
    }

    @Test
    void testAddUser_DAOException() throws ValidationException, DAOException {
        when(dao.create(user)).thenThrow(DAOException.class);

        assertThrows(ValidationException.class, () -> service.signUp(userDTO, "password"));
    }

    @Test
    void testSignIn() throws ValidationException, DAOException {
        when(dao.read("test@email.com")).thenReturn(user);

        String hashedPassword = HashPassword.hash(user.getPassword());
        user.setPassword(hashedPassword);

        UserDTO result = service.signIn("test@email.com", "password");

        verify(dao).read("test@email.com");
        assertEquals(userDTO, result);
    }

    @Test
    void testSignIn_DAOException() throws ValidationException, DAOException {
        when(dao.read("test@email.com")).thenThrow(DAOException.class);

        assertThrows(ValidationException.class, () -> service.signIn("test@email.com", "password"));
    }

    @Test
    void testSignIn_InvalidPassword() throws ValidationException, DAOException {
        when(dao.read("test@email.com")).thenReturn(user);

        assertThrows(ValidationException.class, () ->
                service.signIn("test@email.com", "wrongpassword"));
    }

    @Test
    void testGetAllUsers() throws DAOException {
        List<User> users = new ArrayList<>();
        users.add(user);
        when(dao.readAll(0, 10)).thenReturn(users);

        List<UserDTO> result = service.getAllUsers(0, 10);

        verify(dao).readAll(0, 10);
        assertEquals(1, result.size());
        assertEquals(userDTO, result.get(0));
    }

    @Test
    void changeUserBlocked_userFound() throws DAOException {
        user.setBlocked(false);
        when(dao.read("test@email.com")).thenReturn(user);
        service.changeUserBlocked("test@email.com");
        verify(dao).update("test@email.com", true);
    }

    @Test
    void changeUserBlocked_DAOExceptionThrown() throws DAOException {
        doThrow(new DAOException("DAO exception message")).when(dao).read("test@email.com");
        assertThrows(DAOException.class, () -> service.changeUserBlocked("test@email.com"));
    }

}