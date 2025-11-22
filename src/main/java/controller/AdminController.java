package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import launcher.BookComponentFactory;
import launcher.UserComponentFactory;
import view.AdminView;

public class AdminController {
    private final AdminView adminView;
    private final Stage primaryStage;
    private final Boolean componentsForTest;

    public AdminController(AdminView adminView, Stage primaryStage, Boolean componentsForTest) {
        this.adminView = adminView;
        this.primaryStage = primaryStage;
        this.componentsForTest = componentsForTest;

        this.adminView.addLibraryButtonListener(new LibraryButtonListener());
        this.adminView.addUsersButtonListener(new UsersButtonListener());
    }

    private class LibraryButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            BookComponentFactory.getInstance(componentsForTest, primaryStage)
                    .getBookView()
                    .show();
        }
    }

    private class UsersButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            UserComponentFactory.getInstance(componentsForTest, primaryStage)
                    .getUserView()
                    .show();
        }
    }
}