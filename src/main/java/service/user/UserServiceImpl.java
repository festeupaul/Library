package service.user;

import model.Role;
import model.User;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public UserServiceImpl(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean save(User user) {
        updateRoleWithId(user);
        return userRepository.save(user);
    }

    @Override
    public boolean update(User user) {
        updateRoleWithId(user);
        return userRepository.update(user);
    }

    @Override
    public boolean delete(User user) {
        return userRepository.delete(user);
    }

    private void updateRoleWithId(User user) {
        if (user.getRole() != null && user.getRole().getRole() != null) {
            Role databaseRole = rightsRolesRepository.findRoleByTitle(user.getRole().getRole());

            if (databaseRole != null) {
                user.setRole(databaseRole);
            }
        }
    }
}