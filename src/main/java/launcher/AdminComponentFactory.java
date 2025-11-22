package launcher;

import javafx.stage.Stage;
import view.AdminView;
import controller.AdminController;

public class AdminComponentFactory {
    private static AdminComponentFactory instance;
    private final AdminView adminView;
    private final AdminController adminController;

    public static AdminComponentFactory getInstance(Boolean componentsForTest, Stage primaryStage) {
        if (instance == null) {
            synchronized (AdminComponentFactory.class) {
                if (instance == null) {
                    instance = new AdminComponentFactory(componentsForTest, primaryStage);
                }
            }
        }
        return instance;
    }

    private AdminComponentFactory(Boolean componentsForTest, Stage primaryStage) {
        this.adminView = new AdminView(primaryStage);
        this.adminController = new AdminController(adminView, primaryStage, componentsForTest);
    }

    public AdminView getAdminView() {
        return adminView;
    }

    public AdminController getAdminController() {
        return adminController;
    }
}