package repository.security;

import model.Right;
import model.Role;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.*;

public class RightsRolesRepositoryMySQL implements RightsRolesRepository{
    private final Connection connection;

    public RightsRolesRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addRole(String role) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT IGNORE INTO role values (null, ?)");
            insertStatement.setString(1, role);
            insertStatement.executeUpdate();
        } catch (SQLException e) {

        }
    }

    @Override
    public void addRight(String right) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT IGNORE INTO `right` values (null, ?)");
            insertStatement.setString(1, right);
            insertStatement.executeUpdate();
        } catch (SQLException e) {

        }
    }

    @Override
    public Role findRoleByTitle(String role) {
        String sql = "SELECT * FROM role WHERE `role` = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, role);

            ResultSet roleResultSet = preparedStatement.executeQuery();
            if (roleResultSet.next()) {
                Long roleId = roleResultSet.getLong("id");
                String roleTitle = roleResultSet.getString("role");
                return new Role(roleId, roleTitle, null);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Role findRoleById(Long roleId) {

        String fetchRoleSql = "SELECT * FROM role WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(fetchRoleSql);

            preparedStatement.setLong(1, roleId);

            ResultSet roleResultSet = preparedStatement.executeQuery();
            roleResultSet.next();
            String roleTitle = roleResultSet.getString("role");
            return new Role(roleId, roleTitle, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Right findRightByTitle(String right) {

        String fetchRoleSql = "SELECT * FROM `right` WHERE `right` = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(fetchRoleSql);

            preparedStatement.setString(1, right);

            ResultSet rightResultSet = preparedStatement.executeQuery();
            rightResultSet.next();
            Long rightId = rightResultSet.getLong("id");
            String rightTitle = rightResultSet.getString("right");
            return new Right(rightId, rightTitle);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addRolesToUser(User user, List<Role> roles) {
        try {
            for (Role role : roles) {
                PreparedStatement insertUserRoleStatement = connection
                        .prepareStatement("INSERT INTO `user_role` values (null, ?, ?)");
                insertUserRoleStatement.setLong(1, user.getId());
                insertUserRoleStatement.setLong(2, role.getId());
                insertUserRoleStatement.executeUpdate();
            }
        } catch (SQLException e) {

        }
    }

    @Override
    public List<Role> findRolesForUser(Long userId) {
        String fetchRoleSql = "SELECT * FROM user_role WHERE user_id = ?";
        try {
            List<Role> roles = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(fetchRoleSql);

            preparedStatement.setLong(1, userId);

            ResultSet userRoleResultSet = preparedStatement.executeQuery();

            while (userRoleResultSet.next()) {
                long roleId = userRoleResultSet.getLong("role_id");
                roles.add(findRoleById(roleId));
            }
            return roles;
        } catch (SQLException e) {

        }
        return null;
    }

    @Override
    public void addRoleRight(Long roleId, Long rightId) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT IGNORE INTO role_right values (null, ?, ?)");
            insertStatement.setLong(1, roleId);
            insertStatement.setLong(2, rightId);
            insertStatement.executeUpdate();
        } catch (SQLException e) {

        }
    }
}
