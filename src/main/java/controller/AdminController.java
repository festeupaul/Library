package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import launcher.EmployeeBookComponentFactory;
import launcher.UserComponentFactory;
import view.AdminView;

public class AdminController {
    private final AdminView adminView;
    private final Stage primaryStage;
    private final Boolean componentsForTest;
    private final Long currentUserId;

    public AdminController(AdminView adminView, Stage primaryStage, Boolean componentsForTest, Long currentUserId) {
        this.adminView = adminView;
        this.primaryStage = primaryStage;
        this.componentsForTest = componentsForTest;
        this.currentUserId = currentUserId;

        this.adminView.addLibraryButtonListener(new LibraryButtonListener());
        this.adminView.addUsersButtonListener(new UsersButtonListener());
    }

    private class LibraryButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            EmployeeBookComponentFactory.getInstance(componentsForTest, primaryStage, currentUserId)
                    .getEmployeeView()
                    .show();
        }
    }

    private class UsersButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            UserComponentFactory.getInstance(componentsForTest, primaryStage, currentUserId)
                    .getUserView()
                    .show();
        }
    }
}