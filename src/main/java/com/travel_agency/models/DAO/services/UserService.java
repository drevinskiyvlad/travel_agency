package com.travel_agency.models.DAO.services;

import com.travel_agency.DB.DAO.UserDAO;
import com.travel_agency.exceptions.DAOException;
import com.travel_agency.exceptions.ValidationException;
import com.travel_agency.models.DAO.User;
import com.travel_agency.models.DTO.UserDTO;
import com.travel_agency.utils.Validator;

public class UserService {
    private final UserDAO dao;

    public UserService(UserDAO dao) {
        this.dao = dao;
    }

    public void addUser(UserDTO userDTO, String password) throws ValidationException {
        validateUserDTO(userDTO);
        User user = convertDTOToUser(userDTO, password);
        try {
            dao.create(user);
        } catch (DAOException e) {
            throw new ValidationException("User with this email already exist");
        }
    }

    public UserDTO signIn(String email, String password) throws ValidationException {
        User user;
        try {
            user = dao.read(email);
        } catch (DAOException e) {
            throw new ValidationException("User is not founded");
        }
        validateUser(user, password);
        return convertUserToDTO(user);
    }

    private UserDTO convertUserToDTO(User user) {
        String email = user.getEmail();
        String role = user.getUserRole();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String phone = user.getPhone();
        boolean banned = user.isBanned();
        return new UserDTO(email, role, firstName, lastName, phone, banned);
    }

    private void validateUser(User user, String password) {
        if (user == null)
            throw new ValidationException("User is not founded");
        if(user.isBanned())
            throw new ValidationException("This user is banned");
        if (!Validator.validatePassword(password, user))
            throw new ValidationException("Invalid password");
    }

    private User convertDTOToUser(UserDTO userDTO, String password) {
        String email = userDTO.getEmail();
        String firstName = userDTO.getFirstName();
        String lastName = userDTO.getLastName();
        String phone = userDTO.getPhone();

        return new User(0, email, password, "user", firstName, lastName, phone);
    }

    private void validateUserDTO(UserDTO user) {
        if (!Validator.validateEmail(user.getEmail())) {
            throw new ValidationException("Email is not valid");
        }
        if (!Validator.validatePhone(user.getPhone())) {
            throw new ValidationException("Phone is not valid");
        }
    }

}
