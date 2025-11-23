package launcher;

import controller.SaleController;
import database.Constants;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage; // <--- Import necesar
import mapper.UserMapper;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
import repository.sale.SaleRepository;
import repository.sale.SaleRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.sale.SaleService;
import service.sale.SaleServiceImpl;
import service.user.UserService;
import service.user.UserServiceImpl;
import view.SaleView;
import view.model.EmployeeBookDTO;
import view.model.UserDTO;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

public class SaleComponentFactory {
    private static SaleComponentFactory instance;
    private final SaleView saleView;
    private final SaleController saleController;
    private final SaleService saleService;

    public static SaleComponentFactory getInstance(Boolean componentsForTest, Stage primaryStage, EmployeeBookDTO book, int quantity, Long employeeId) {

        synchronized (SaleComponentFactory.class) {
            instance = new SaleComponentFactory(componentsForTest, primaryStage, book, quantity, employeeId);
        }
        return instance;
    }

    private SaleComponentFactory(Boolean componentsForTest, Stage primaryStage, EmployeeBookDTO book, int quantity, Long employeeId) {
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();

        BookRepository bookRepository = new BookRepositoryMySQL(connection);
        SaleRepository saleRepository = new SaleRepositoryMySQL(connection);
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);

        BookService bookService = new BookServiceImpl(bookRepository);
        this.saleService = new SaleServiceImpl(bookService, saleRepository);
        UserService userService = new UserServiceImpl(userRepository, rightsRolesRepository);

        List<UserDTO> customers = UserMapper.convertUserListToUserDTOList(userService.findAll())
                .stream()
                .filter(userDTO -> Constants.Roles.CUSTOMER.equals(userDTO.getRole()))
                .collect(Collectors.toList());

        this.saleView = new SaleView(primaryStage, customers);

        this.saleController = new SaleController(saleView, saleService, componentsForTest, book, quantity, employeeId);
    }

    public SaleView getSaleView() {
        return saleView;
    }

    public SaleController getSaleController() {
        return saleController;
    }
}