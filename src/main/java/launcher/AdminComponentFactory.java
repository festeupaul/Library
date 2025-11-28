package launcher;

import controller.AdminController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import repository.sale.SaleRepository;
import repository.sale.SaleRepositoryMySQL;
import service.report.ReportService;
import view.AdminView;

import java.sql.Connection;

public class AdminComponentFactory {
    private static AdminComponentFactory instance;
    private final AdminView adminView;
    private final AdminController adminController;
    private final SaleRepository saleRepository;
    private final ReportService reportService;

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
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.adminView = new AdminView(primaryStage);
        this.reportService = new ReportService();
        this.saleRepository = new SaleRepositoryMySQL(connection);
        this.adminController = new AdminController(adminView, primaryStage, componentsForTest, currentUserId, reportService, saleRepository);
    }

    public AdminView getAdminView() {
        return adminView;
    }

    public AdminController getAdminController() {
        return adminController;
    }
}