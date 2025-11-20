import controller.LoginController;
import database.DatabaseConnectionFactory;
import database.JDBConnectionWrapper;
import javafx.application.Application;
import javafx.stage.Stage;
import launcher.LoginComponentFactory;
import model.Book;
import model.builder.BookBuilder;
import model.validator.UserValidator;
import repository.book.BookRepository;
import repository.book.BookRepositoryCacheDecorator;
import repository.book.BookRepositoryMySQL;
import repository.book.Cache;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;
import view.BookView;
import view.model.LoginView;

import java.sql.Connection;
import java.time.LocalDate;

import static database.Constants.Schemas.PRODUCTION;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginComponentFactory.getInstance(false, primaryStage);
    }

    public static void main(String[] args) {

        launch(args);
//        System.out.println("Hello World");
//
//        Book book = new BookBuilder()
//                .setTitle("Ion")
//                .setAuthor("Liviu Rebreanu")
//                .setPublishedDate(LocalDate.of(1910, 10, 20))
//                .build();
//
//        System.out.println(book);
//
//        BookRepository bookRepository = new BookRepositoryMock();
//
//        bookRepository.save(book);
//        bookRepository.save(new BookBuilder()
//                .setTitle("Moara cu Noroc")
//                .setAuthor("Ioan Slavici")
//                .setPublishedDate(LocalDate.of(1950, 2, 10))
//                .build());
//        System.out.println(bookRepository.findAll());
//        bookRepository.removeAll();
//        System.out.println(bookRepository.findAll());
//
//        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(false).getConnection();
//        //BookRepository bookRepository = new BookRepositoryMySQL(connection);
//        BookRepository bookRepository = new BookRepositoryCacheDecorator(new BookRepositoryMySQL(connection), new Cache<>());
//        BookService bookService = new BookServiceImpl(bookRepository);
//
//        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
//        UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
//
//        AuthenticationService authenticationService = new AuthenticationServiceImpl(userRepository, rightsRolesRepository);
//
//        if(userRepository.existsByUsername("Paul")){
//            System.out.println("Username already exists");
//        }else{
//            authenticationService.register("Paul", "parola123!");
//        }
//
//        System.out.println(authenticationService.login("Paul", "parola123!"));
//
//        bookService.save(book);
//        System.out.println(bookService.findAll());
//        System.out.println(bookService.findAll());
//        Book bookMoaraCuNoroc = new BookBuilder()
//                .setTitle("Moara cu Noroc")
//                .setAuthor("Ioan Slavici")
//                .setPublishedDate(LocalDate.of(1950, 2, 10))
//                .build();
//        bookService.save(bookMoaraCuNoroc);
//        System.out.println(bookService.findAll());
//        bookService.delete(bookMoaraCuNoroc);
//        bookService.delete(book);
//        System.out.println(bookService.findAll());
    }
}
