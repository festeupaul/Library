package repository.user;

import model.User;
import model.builder.UserBuilder;
import model.validator.Notification;
import repository.security.RightsRolesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.USER;

public class UserRepositoryMySQL implements UserRepository {

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;

    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM `" + USER + "`";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                User user = new UserBuilder()
                        .setId(rs.getLong("id"))
                        .setUsername(rs.getString("username"))
                        .setPassword(rs.getString("password"))
                        .setRole(rightsRolesRepository.findRoleForUser(rs.getLong("id")))
                        .build();
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Notification<User> findByUsernameAndPassword(String username, String password) {
        Notification<User> findByUsernameAndPasswordNotification = new Notification<>();
        try {
            String fetchUserSql = "SELECT * FROM `" + USER + "` WHERE `username` = ? AND `password` = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(fetchUserSql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        User user = new UserBuilder()
                                .setId(resultSet.getLong("id"))
                                .setUsername(resultSet.getString("username"))
                                .setPassword(resultSet.getString("password"))
                                .setRole(rightsRolesRepository.findRoleForUser(resultSet.getLong("id")))
                                .build();
                        findByUsernameAndPasswordNotification.setResult(user);
                    } else {
                        findByUsernameAndPasswordNotification.addError("Invalid username or password!");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
            findByUsernameAndPasswordNotification.addError("Something is wrong with the Database!");
        }
        return findByUsernameAndPasswordNotification;
    }

    @Override
    public boolean save(User user) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO " + USER + " values (null, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            user.setId(userId);

            rightsRolesRepository.addRoleToUser(user, user.getRole());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM `" + USER + "` WHERE id = ?");
            statement.setLong(1, user.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(User user) {
        try {
            String oldPassword = "";
            String getPassSql = "SELECT password FROM `" + USER + "` WHERE id = ?";
            try (PreparedStatement getPassStmt = connection.prepareStatement(getPassSql)) {
                getPassStmt.setLong(1, user.getId());
                ResultSet rs = getPassStmt.executeQuery();
                if (rs.next()) {
                    oldPassword = rs.getString("password");
                }
            }

            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE `" + USER + "` SET username = ?, password = ? WHERE id = ?"
            );
            statement.setString(1, user.getUsername());
            statement.setString(2, oldPassword);
            statement.setLong(3, user.getId());
            statement.executeUpdate();

            if (user.getRole() != null) {
                rightsRolesRepository.addRoleToUser(user, user.getRole());
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from " + USER + " where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean existsByUsername(String email) {
        try {
            String fetchUserSql = "SELECT * FROM `" + USER + "` WHERE `username` = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(fetchUserSql);
            preparedStatement.setString(1, email);
            ResultSet userResultSet = preparedStatement.executeQuery();
            return userResultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}