package repository.security;

import model.Right;
import model.Role;
import model.User;

import java.util.List;

public interface RightsRolesRepository {
    void addRole(String role);

    void addRight(String right);

    Role findRoleByTitle(String role);

    Role findRoleById(Long roleId);

    Right findRightByTitle(String right);

    void addRoleToUser(User user, Role role);

    Role findRoleForUser(Long userId);

    void addRoleRight(Long roleId, Long rightId);
}