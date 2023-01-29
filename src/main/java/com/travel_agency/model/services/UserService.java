package com.travel_agency.model.services;

import com.travel_agency.exceptions.DAOException;
import com.travel_agency.exceptions.ValidationException;
import com.travel_agency.model.DB.DAO.UserDAO;
import com.travel_agency.model.DTO.UserDTO;
import com.travel_agency.model.entity.User;
import com.travel_agency.utils.Constants.ValidationMessageConstants;
import com.travel_agency.utils.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);
    private final UserDAO<User> dao;

    /**
     * Constructor
     */
    public UserService(UserDAO<User> dao) {
        this.dao = dao;
    }

    /**
     * Add user to database
     * @throws ValidationException If parameters are not valid
     */
    public void signUp(UserDTO userDTO, String password) throws ValidationException {
        validateUserDTO(userDTO, password);
        User user = convertDTOToUser(userDTO, password);
        try {
            dao.create(user);
        } catch (DAOException e) {
            logger.error("Unable to register user: " + e.getMessage(), e);
            throw new ValidationException(ValidationMessageConstants.EXIST_EMAIL);
        }
    }


    /**
     * Check if user with this email is exist and then check password
     * @return UserDTO if this user exist and password correct
     * @throws ValidationException if user not found or user password incorrect
     */
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

    /**
     * Get all users from database with defined parameters
     * @param offset index of first user from database
     * @param numOfRecords number of records that given from database
     * @return List of user
     */
    public List<UserDTO> getAllUsers(int offset, int numOfRecords) {
        List<User> result = new CopyOnWriteArrayList<>();
        try {
            result = dao.readAll(offset,numOfRecords);
        } catch (DAOException e) {
            logger.error("Unable to get all users: " + e.getMessage(), e);
        }
        return makeListOfDTOs(result);
    }

    /**
     * Change user blocked status
     */
    public void changeUserBlocked(String email) throws DAOException {
        User user = dao.read(email);
        dao.update(email, !user.isBlocked());
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
        boolean banned = user.isBlocked();
        return new UserDTO(email, role, firstName, lastName, phone, banned);
    }

    private void validateUser(User user, String password) {
        if (user == null)
            throw new ValidationException(ValidationMessageConstants.USER_NOT_FOUNDED);
        if (user.isBlocked())
            throw new ValidationException(ValidationMessageConstants.USER_BANNED);
        if (!Validator.checkPasswordCorrect(password, user))
            throw new ValidationException(ValidationMessageConstants.INCORRECT_PASSWORD);
    }

    protected User convertDTOToUser(UserDTO userDTO, String password) {
        String email = userDTO.getEmail();
        String firstName = userDTO.getFirstName();
        String lastName = userDTO.getLastName();
        String phone = userDTO.getPhone();
        boolean blocked = userDTO.isBlocked();

        return new User(0, email, password, "user", firstName, lastName, phone,blocked);
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
