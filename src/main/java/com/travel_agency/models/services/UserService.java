package com.travel_agency.models.services;

import com.travel_agency.DB.DAO.UserDAO;
import com.travel_agency.exceptions.DAOException;
import com.travel_agency.exceptions.ValidationException;
import com.travel_agency.models.DAO.Order;
import com.travel_agency.models.DAO.User;
import com.travel_agency.models.DTO.UserDTO;
import com.travel_agency.utils.ValidationMessageConstants;
import com.travel_agency.utils.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);
    private final UserDAO dao;

    public UserService(UserDAO dao) {
        this.dao = dao;
    }

    public void addUser(UserDTO userDTO, String password) throws ValidationException {
        validateUserDTO(userDTO, password);
        User user = convertDTOToUser(userDTO, password);
        try {
            dao.create(user);
        } catch (DAOException e) {
            logger.error("Unable to register user: " + e.getMessage(), e);
            throw new ValidationException(ValidationMessageConstants.EXIST_EMAIL);
        }
    }

    public UserDTO signIn(String email, String password) throws ValidationException {
        User user;
        try {
            user = dao.read(email);
        } catch (DAOException e) {
            logger.error("Unable to signIn user: " + e.getMessage(), e);
            throw new ValidationException(ValidationMessageConstants.USER_NOT_FOUNDED);
        }
        validateUser(user, password);
        return convertUserToDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        List<User> result = new CopyOnWriteArrayList<>();
        try {
            result = dao.readAll();
        } catch (DAOException e) {
            logger.error("Unable to get all users" + e.getMessage(), e);
        }
        return makeListOfDTOs(result);
    }

    public boolean changeUserBlocked(String email) throws DAOException {
        User user = dao.read(email);
        return dao.update(!user.isBanned(), email);
    }

    private List<UserDTO> makeListOfDTOs(List<User> users) {
        List<UserDTO> userDTOs = new CopyOnWriteArrayList<>();
        for(User u: users){
            userDTOs.add(convertUserToDTO(u));
        }
        return userDTOs;
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
            throw new ValidationException(ValidationMessageConstants.USER_NOT_FOUNDED);
        if (user.isBanned())
            throw new ValidationException(ValidationMessageConstants.USER_BANNED);
        if (!Validator.checkPasswordCorrect(password, user))
            throw new ValidationException(ValidationMessageConstants.INCORRECT_PASSWORD);
    }

    protected User convertDTOToUser(UserDTO userDTO, String password) {
        String email = userDTO.getEmail();
        String firstName = userDTO.getFirstName();
        String lastName = userDTO.getLastName();
        String phone = userDTO.getPhone();

        return new User(0, email, password, "user", firstName, lastName, phone);
    }

    private void validateUserDTO(UserDTO user, String password) {
        if (!Validator.validateEmail(user.getEmail())) {
            throw new ValidationException(ValidationMessageConstants.INVALID_EMAIL);
        }
        if (!Validator.validatePhone(user.getPhone())) {
            throw new ValidationException(ValidationMessageConstants.INVALID_PHONE);
        }
        if (!Validator.validatePassword(password)) {
            throw new ValidationException(ValidationMessageConstants.INVALID_REGISTRATION_PASSWORD);
        }
    }

}
