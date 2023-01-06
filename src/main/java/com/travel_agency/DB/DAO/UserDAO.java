package com.travel_agency.DB.DAO;

import com.travel_agency.models.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserDAO implements DAO<User, String> {

    private final Connection con;

    public UserDAO(Connection con) {
        this.con = con;
    }

    @Override
    public boolean create(User user) {

        String createCommand = checkIfDetailsExist(user);

        try (PreparedStatement ps = con.prepareStatement(createCommand)){

            setVariablesToCreateStatement(user, ps);
            ps.executeUpdate();

            return true;
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
            //todo: place here logger
            return false;
        }
    }

    @Override
    public User read(String email) {
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_USER)){

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return initializeUser(email, rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
            //todo: place here logger
        }
        return null;
    }

    @Override
    public boolean update(User user, String newEmail) {
        try (PreparedStatement ps = con.prepareStatement(Constants.CHANGE_USER_EMAIL);){
            ps.setString(1, newEmail);
            ps.setString(2, user.getPhone());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            //todo:add logger here
            return false;
        }
    }

    public boolean updateUserRole(User user, String newRole){
        try (PreparedStatement ps = con.prepareStatement(Constants.CHANGE_USER_ROLE)){
            int roleId = readUserRole(newRole);
            ps.setInt(1, roleId);
            ps.setString(2, user.getEmail());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            //todo:add logger here
            return false;
        }
    }

    @Override
    public boolean delete(User user) {
        try (PreparedStatement ps = con.prepareStatement(Constants.DELETE_USER)){
            ps.setString(1, user.getEmail());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            //todo:add logger here
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
            e.printStackTrace();
            //todo: add logger here
        }
        return result;
    }

    private int readUserRole(String name) throws IllegalArgumentException{
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_USER_ROLE_BY_NAME)){
            System.out.println("user role name=" + name);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return rs.getInt(Fields.USER_ROLE_ID);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            //todo: place here logger
        }
        throw new IllegalArgumentException("Unknown user role name");
    }

    private String readUserRole(int id) throws IllegalArgumentException{
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_USER_ROLE_BY_ID)){

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return rs.getString(Fields.USER_ROLE_NAME);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            //todo: place here logger
        }
        throw new IllegalArgumentException("Unknown user role name");
    }

    private static String checkIfDetailsExist(User user) {
        String createCommand;
        if(user.getDetails() != null){
            createCommand = Constants.ADD_USER_WITH_DETAILS;
        } else{
            createCommand = Constants.ADD_USER;
        }
        return createCommand;
    }

    private void setVariablesToCreateStatement(User user, PreparedStatement ps) throws SQLException, IllegalArgumentException {
        ps.setString(1, user.getEmail());
        ps.setString(2, user.getPassword());
        ps.setInt(3, readUserRole(user.getUserRole()));
        ps.setString(4, user.getFirstName());
        ps.setString(5, user.getLastName());
        ps.setString(6, user.getPhone());

        if(user.getDetails() != null)
            ps.setString(7, user.getDetails());
    }

    private User initializeUser(String email, ResultSet rs) throws SQLException {
        String role;
        int id = rs.getInt(Fields.USER_ID);
        String password = rs.getString(Fields.USER_PASSWORD);
        String firstName = rs.getString(Fields.USER_FIRST_NAME);
        String lastName = rs.getString(Fields.USER_LAST_NAME);
        String phone = rs.getString(Fields.USER_PHONE);
        LocalDateTime userFrom = rs.getTimestamp(Fields.USER_FROM).toLocalDateTime();
        String details = rs.getString(Fields.USER_DETAILS);

        try {
            role = readUserRole(rs.getInt(Fields.USER_ROLE));
        } catch(IllegalArgumentException e){
            e.printStackTrace();
            //todo: add logger here
            return null;
        }

        User user =  new User(id, email, password, role, firstName, lastName, phone, userFrom);

        if(details != null)
            user.setDetails(details);
        return user;
    }

    private void addUsersToList(List<User> result, ResultSet rs) throws SQLException {
        while(rs.next()) {
            String email = rs.getString(Fields.USER_EMAIL);
            result.add(read(email));
        }
    }
}

