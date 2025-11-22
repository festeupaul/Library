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

public class BookComponentFactory {
    private final EmployeeView bookView;
    private final EmployeeBookController bookController;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private static BookComponentFactory instance;

    public static BookComponentFactory getInstance(Boolean componentsForTest, Stage primaryStage) {
         //lazy load singleton
        if (instance == null) {
            synchronized (BookComponentFactory.class) {
                if (instance == null) {
                    instance = new BookComponentFactory(componentsForTest, primaryStage);
                }
            }
        }
//        if(instance == null) {
//            instance = new ComponentFactory(componentsForTest, primaryStage);
//        }
        return instance;
    }

    /*public*/private BookComponentFactory(Boolean componentsForTest, Stage primaryStage) {
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.bookRepository = new BookRepositoryMySQL(connection);
        this.bookService = new BookServiceImpl(bookRepository);
        List<EmployeeBookDTO> employeeBookDTOS = EmployeeBookMapper.convertBookListToBookDTOList(bookService.findAll());
        this.bookView = new EmployeeView(primaryStage, employeeBookDTOS);
        this.bookController = new EmployeeBookController(bookView, bookService);
    }

    public EmployeeView getBookView() {
        return bookView;
    }

    public EmployeeBookController getBookController() {
        return bookController;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public BookService getBookService() {
        return bookService;
    }
}
