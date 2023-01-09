package com.travel_agency.DB.DAO;

import com.travel_agency.DB.Constants;
import com.travel_agency.DB.Fields;
import com.travel_agency.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserDAO implements DAO<User, String> {
    private final Logger logger = LogManager.getLogger();
    private final Connection con;

    public UserDAO(Connection con) {
        this.con = con;
    }

    @Override
    public boolean create(User user) {
        try (PreparedStatement ps = con.prepareStatement(Constants.ADD_USER)) {

            setVariablesToCreateStatement(user, ps);
            ps.executeUpdate();

            return true;
        } catch (SQLException | IllegalArgumentException e) {
            logger.error("Unable to create user: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public User read(String email) {
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_USER)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return initializeUser(rs);
            }
        } catch (SQLException e) {
            logger.error("Unable to read user: " + e.getMessage(), e);
        }
        return null;
    }

    public User read(int id) {
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_USER_BY_ID)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return initializeUser(rs);
            }
        } catch (SQLException e) {
            logger.error("Unable to read user: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public boolean update(User user, String newEmail) {
        try (PreparedStatement ps = con.prepareStatement(Constants.CHANGE_USER_EMAIL);) {
            ps.setString(1, newEmail);
            ps.setString(2, user.getPhone());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Unable to update user: " + e.getMessage(), e);
            return false;
        }
    }

    public boolean updateUserRole(User user, String newRole) {
        try (PreparedStatement ps = con.prepareStatement(Constants.CHANGE_USER_ROLE)) {
            int roleId = readUserRole(newRole);
            ps.setInt(1, roleId);
            ps.setString(2, user.getEmail());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Unable to update user role: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean delete(User user) {
        try (PreparedStatement ps = con.prepareStatement(Constants.DELETE_USER)) {
            ps.setString(1, user.getEmail());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Unable to delete user: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public List<User> readAll() {
        List<User> result = new CopyOnWriteArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_ALL_USERS);
             ResultSet rs = ps.executeQuery();) {
            addUsersToList(result, rs);
        } catch (SQLException e) {
            logger.error("Unable to read list user: " + e.getMessage(), e);
        }
        return result;
    }

    public int readUserRole(String name) throws IllegalArgumentException {
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_USER_ROLE_BY_NAME)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(Fields.USER_ROLE_ID);
            }

        } catch (SQLException e) {
            logger.error("Unable to read user role: " + e.getMessage(), e);
        }
        throw new IllegalArgumentException("Unknown user role name");
    }

    public String readUserRole(int id) throws IllegalArgumentException {
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_USER_ROLE_BY_ID)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString(Fields.USER_ROLE_NAME);
            }

        } catch (SQLException e) {
            logger.error("Unable to read user role: " + e.getMessage(), e);
        }
        throw new IllegalArgumentException("Unknown user role name");
    }

    private void setVariablesToCreateStatement(User user, PreparedStatement ps) throws SQLException, IllegalArgumentException {
        ps.setString(1, user.getEmail());
        ps.setString(2, user.getPassword());
        ps.setInt(3, readUserRole(user.getUserRole()));
        ps.setString(4, user.getFirstName());
        ps.setString(5, user.getLastName());
        ps.setString(6, user.getPhone());
    }

    private User initializeUser(ResultSet rs) throws SQLException {
        String role;
        int id = rs.getInt(Fields.USER_ID);
        String email = rs.getString(Fields.USER_EMAIL);
        String password = rs.getString(Fields.USER_PASSWORD);
        String firstName = rs.getString(Fields.USER_FIRST_NAME);
        String lastName = rs.getString(Fields.USER_LAST_NAME);
        String phone = rs.getString(Fields.USER_PHONE);

        try {
            role = readUserRole(rs.getInt(Fields.USER_ROLE));
        } catch (IllegalArgumentException e) {
            return null;
        }

        return new User(id, email, password, role, firstName, lastName, phone);
    }

    private void addUsersToList(List<User> result, ResultSet rs) throws SQLException {
        while (rs.next()) {
            String email = rs.getString(Fields.USER_EMAIL);
            result.add(read(email));
        }
    }
}

