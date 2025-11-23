package launcher;

import controller.UserController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import mapper.UserMapper;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.UserService;
import service.user.UserServiceImpl;
import view.UserView;
import view.model.UserDTO;

import java.sql.Connection;
import java.util.List;

public class UserComponentFactory {
    private final UserView userView;
    private final UserController userController;
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final UserService userService;
    private static UserComponentFactory instance;
    private final Long currentUserId;

    public static UserComponentFactory getInstance(Boolean componentsForTest, Stage primaryStage, Long currentUserId) {
        if (instance == null) {
            synchronized (UserComponentFactory.class) {
                if (instance == null) {
                    instance = new UserComponentFactory(componentsForTest, primaryStage, currentUserId);
                }
            }
        }
        return instance;
    }

    private UserComponentFactory(Boolean componentsForTest, Stage primaryStage, Long currentUserId) {
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();

        this.currentUserId = currentUserId;

        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);

        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);

        this.userService = new UserServiceImpl(userRepository, rightsRolesRepository);

        List<UserDTO> userDTOs = UserMapper.convertUserListToUserDTOList(userService.findAll());

        this.userView = new UserView(primaryStage, userDTOs);

        this.userController = new UserController(userView, userService, componentsForTest, currentUserId);
    }

    public UserView getUserView() {
        return userView;
    }

    public UserController getUserController() {
        return userController;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }

    public UserService getUserService() {
        return userService;
    }
}