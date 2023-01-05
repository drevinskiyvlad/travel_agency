package com.travel_agency.DB.DAO;

import com.travel_agency.models.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class UserDAO implements DAO<User, String> {

    private final Connection con;

    public UserDAO(Connection con) {
        this.con = con;
    }

    @Override
    public boolean create(User user) {

        String createCommand = checkIfDetailsExist(user);

        try (PreparedStatement ps = con.prepareStatement(createCommand)){

            setVariablesToStatement(user, ps);

            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
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
            //todo: place here logger
        }

        throw new IllegalArgumentException
                ("Can`t read user from data base, because of invalid email");
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

    private void setVariablesToStatement(User user, PreparedStatement ps) throws SQLException {
        ps.setString(1, user.getEmail());
        ps.setString(2, user.getPassword());
        ps.setInt(3, User.getIdOfUserRole(user.getUserRole()));
        ps.setString(4, user.getFirstName());
        ps.setString(5, user.getLastName());
        ps.setString(6, user.getPhone());

        if(user.getDetails() != null)
            ps.setString(7, user.getDetails());
    }

    private User initializeUser(String email, ResultSet rs) throws SQLException {
        int id = rs.getInt(Fields.USER_ID);
        String password = rs.getString(Fields.USER_PASSWORD);
        String role = User.getNameOfUserRole(rs.getInt(Fields.USER_ROLE));
        String firstName = rs.getString(Fields.USER_FIRST_NAME);
        String lastName = rs.getString(Fields.USER_LAST_NAME);
        String phone = rs.getString(Fields.USER_PHONE);
        LocalDateTime userFrom = rs.getTimestamp(Fields.USER_FROM).toLocalDateTime();
        String details = rs.getString(Fields.USER_DETAILS);

        User user =  new User(id, email, password, role, firstName, lastName, phone, userFrom);

        if(details != null)
            user.setDetails(details);
        return user;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }

    @Override
    public List<User> readAll() {
        return null;
    }
}

