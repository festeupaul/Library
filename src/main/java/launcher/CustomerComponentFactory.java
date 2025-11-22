package launcher;

import controller.CustomerBookController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import mapper.CustomerBookMapper;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import view.CustomerView;
import view.model.CustomerBookDTO;

import java.sql.Connection;
import java.util.List;

public class CustomerComponentFactory {
    private final CustomerView customerView;
    private final CustomerBookController customerController;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private static CustomerComponentFactory instance;

    public static CustomerComponentFactory getInstance(Boolean componentsForTest, Stage primaryStage) {
        if (instance == null) {
            synchronized (CustomerComponentFactory.class) {
                if (instance == null) {
                    instance = new CustomerComponentFactory(componentsForTest, primaryStage);
                }
            }
        }
        return instance;
    }

    private CustomerComponentFactory(Boolean componentsForTest, Stage primaryStage) {
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.bookRepository = new BookRepositoryMySQL(connection);
        this.bookService = new BookServiceImpl(bookRepository);

        List<CustomerBookDTO> customerBookDTOs = CustomerBookMapper.convertBookListToCustomerDTOList(bookService.findAll());

        this.customerView = new CustomerView(primaryStage, customerBookDTOs);
        this.customerController = new CustomerBookController(customerView, bookService);
    }

    public CustomerView getCustomerView() {
        return customerView;
    }

    public CustomerBookController getCustomerController() {
        return customerController;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public BookService getBookService() {
        return bookService;
    }
}