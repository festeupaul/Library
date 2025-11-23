package launcher;

import controller.AdminController;
import javafx.stage.Stage;
import view.AdminView;

public class AdminComponentFactory {
    private static AdminComponentFactory instance;
    private final AdminView adminView;
    private final AdminController adminController;

    public static AdminComponentFactory getInstance(Boolean componentsForTest, Stage primaryStage, Long currentUserId) {
        if (instance == null) {
            synchronized (AdminComponentFactory.class) {
                if (instance == null) {
                    instance = new AdminComponentFactory(componentsForTest, primaryStage, currentUserId);
                }
            }
        }
        return instance;
    }

    private AdminComponentFactory(Boolean componentsForTest, Stage primaryStage, Long currentUserId) {
        this.adminView = new AdminView(primaryStage);
        this.adminController = new AdminController(adminView, primaryStage, componentsForTest, currentUserId);
    }

    public AdminView getAdminView() {
        return adminView;
    }

    public AdminController getAdminController() {
        return adminController;
    }
}