package com.travel_agency.DB.DAO;

import com.travel_agency.DB.Constants;
import com.travel_agency.DB.Fields;
import com.travel_agency.exceptions.DAOException;
import com.travel_agency.models.DAO.User;
import com.travel_agency.utils.HashPassword;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserDAO implements DAO<User, String> {
    private static final Logger logger = LogManager.getLogger(UserDAO.class);
    private final Connection con;
    @Getter
    private int numberOfPages; // for pagination

    public UserDAO(Connection con) {
        this.con = con;
    }

    @Override
    public boolean create(User user) throws DAOException {
        try (PreparedStatement ps = con.prepareStatement(Constants.ADD_USER)) {

            setVariablesToCreateStatement(user, ps);
            ps.executeUpdate();

            return true;
        } catch (SQLException | IllegalArgumentException e) {
            logger.error("Unable to create user: " + e.getMessage(), e);
            throw new DAOException("Unable to create user: " + e.getMessage());
        }
    }

    @Override
    public User read(String email) throws DAOException {
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(Constants.FIND_USER);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                return initializeUser(rs);
            }
        } catch (SQLException e) {
            logger.error("Unable to read user: " + e.getMessage(), e);
            throw new DAOException("Unable to read user: " + e.getMessage());
        } finally {
            close(rs);
            close(ps);
        }
        return null;
    }

    public User read(int id) throws DAOException {
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_USER_BY_ID)) {

            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return initializeUser(rs);
            }
        } catch (SQLException e) {
            logger.error("Unable to read user: " + e.getMessage(), e);
            throw new DAOException("Unable to read user: " + e.getMessage());
        } finally {
            close(rs);
        }
        return null;
    }

    @Override
    public boolean update(User user, String newRole) throws DAOException {
        try (PreparedStatement ps = con.prepareStatement(Constants.CHANGE_USER_ROLE)) {
            int roleId = readUserRole(newRole);
            ps.setInt(1, roleId);
            ps.setString(2, user.getEmail());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Unable to update user role: " + e.getMessage(), e);
            throw new DAOException("Unable to update user role: " + e.getMessage());
        }
    }

    public boolean update(boolean value, String email) throws DAOException {
        try (PreparedStatement ps = con.prepareStatement(Constants.CHANGE_USER_BLOCKED)) {
            ps.setBoolean(1, value);
            ps.setString(2, email);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Unable to update user blocked: " + e.getMessage(), e);
            throw new DAOException("Unable to update user blocked: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(User user) throws DAOException {
        try (PreparedStatement ps = con.prepareStatement(Constants.DELETE_USER)) {
            ps.setString(1, user.getEmail());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Unable to delete user: " + e.getMessage(), e);
            throw new DAOException("Unable to delete user: " + e.getMessage());
        }
    }

    @Override
    public List<User> readAll(int offset, int numOfRecords) throws DAOException {
        List<User> result = new CopyOnWriteArrayList<>();
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_ALL_USERS)) {
            ps.setInt(1, offset);
            ps.setInt(2, numOfRecords);
            rs = ps.executeQuery();
            addUsersToList(result, rs);

            rs.close();

            rs = ps.executeQuery(Constants.USER_GET_NUMBER_OF_RECORDS);
            if (rs.next())
                this.numberOfPages = (int) Math.ceil(rs.getInt(1) * 1.0 / numOfRecords);

        } catch (SQLException e) {
            logger.error("Unable to read list user: " + e.getMessage(), e);
            throw new DAOException("Unable to real list user: " + e.getMessage());
        } finally {
            close(rs);
        }
        return result;
    }

    public List<String> readAllUserRoles() throws DAOException {
        List<String> result = new CopyOnWriteArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_ALL_USER_ROLES);
             ResultSet rs = ps.executeQuery()) {
            addUserRolesToList(result, rs);
        } catch (SQLException e) {
            logger.error("Unable to read list of user roles: " + e.getMessage(), e);
            throw new DAOException("Unable to read list user roles: " + e.getMessage());
        }
        return result;
    }

    private void addUserRolesToList(List<String> result, ResultSet rs) throws SQLException {
        while (rs.next()) {
            int id = rs.getInt(Fields.USER_ROLE_ID);
            result.add(readUserRole(id));
        }
    }

    public int readUserRole(String name) throws IllegalArgumentException {
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_USER_ROLE_BY_NAME)) {
            ps.setString(1, name);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(Fields.USER_ROLE_ID);
            }

        } catch (SQLException e) {
            logger.error("Unable to read user role: " + e.getMessage(), e);
        } finally {
            close(rs);
        }
        throw new IllegalArgumentException("Unknown user role name");
    }

    public String readUserRole(int id) throws IllegalArgumentException {
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_USER_ROLE_BY_ID)) {

            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString(Fields.USER_ROLE_NAME);
            }

        } catch (SQLException e) {
            logger.error("Unable to read user role: " + e.getMessage(), e);
        } finally {
            close(rs);
        }
        throw new IllegalArgumentException("Unknown user role name");
    }

    private void setVariablesToCreateStatement(User user, PreparedStatement ps) throws SQLException, IllegalArgumentException {
        String hashedPassword = HashPassword.hash(user.getPassword());
        ps.setString(1, user.getEmail());
        ps.setString(2, hashedPassword);
        ps.setInt(3, readUserRole(user.getUserRole()));
        ps.setString(4, user.getFirstName());
        ps.setString(5, user.getLastName());
        ps.setString(6, user.getPhone());
        ps.setBoolean(7, user.isBlocked());
    }

    private User initializeUser(ResultSet rs) throws SQLException {
        String role;
        int id = rs.getInt(Fields.USER_ID);
        String email = rs.getString(Fields.USER_EMAIL);
        String password = rs.getString(Fields.USER_PASSWORD);
        String firstName = rs.getString(Fields.USER_FIRST_NAME);
        String lastName = rs.getString(Fields.USER_LAST_NAME);
        String phone = rs.getString(Fields.USER_PHONE);
        boolean blocked = rs.getBoolean(Fields.USER_BLOCKED);

        try {
            role = readUserRole(rs.getInt(Fields.USER_ROLE));
        } catch (IllegalArgumentException e) {
            return null;
        }
        User user = new User(id, email, password, role, firstName, lastName, phone, blocked);
        user.setBlocked(blocked);
        return user;
    }

    private void addUsersToList(List<User> result, ResultSet rs) throws SQLException {
        while (rs.next()) {
            String email = rs.getString(Fields.USER_EMAIL);
            result.add(read(email));
        }
    }

    private void close(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (Exception e) {
                logger.error("Error while close: " + e.getMessage(), e);
            }
        }
    }
}

