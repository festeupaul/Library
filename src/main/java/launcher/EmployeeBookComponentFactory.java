package launcher;

import controller.EmployeeBookController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import mapper.EmployeeBookMapper;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import view.EmployeeView;
import view.model.EmployeeBookDTO;

import java.sql.Connection;
import java.util.List;

public class EmployeeBookComponentFactory {
    private final EmployeeView employeeView;
    private final EmployeeBookController employeeBookController;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final Boolean componentsForTest;
    private static EmployeeBookComponentFactory instance;

    public static EmployeeBookComponentFactory getInstance(Boolean componentsForTest, Stage primaryStage, Long currentUserId) {
        if (instance == null) {
            synchronized (EmployeeBookComponentFactory.class) {
                if (instance == null) {
                    instance = new EmployeeBookComponentFactory(componentsForTest, primaryStage, currentUserId);
                }
            }
        }
        return instance;
    }

    private EmployeeBookComponentFactory(Boolean componentsForTest, Stage primaryStage, Long currentUserId) {
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();

        this.bookRepository = new BookRepositoryMySQL(connection);
        this.bookService = new BookServiceImpl(bookRepository);
        this.componentsForTest = componentsForTest;

        List<EmployeeBookDTO> bookDTOs = EmployeeBookMapper.convertBookListToBookDTOList(bookService.findAll());

        this.employeeView = new EmployeeView(primaryStage, bookDTOs);

        this.employeeBookController = new EmployeeBookController(employeeView, bookService, componentsForTest, currentUserId);
    }

    public EmployeeView getEmployeeView() {
        return employeeView;
    }

    public EmployeeBookController getEmployeeBookController() {
        return employeeBookController;
    }
}