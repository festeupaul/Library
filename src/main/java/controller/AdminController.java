package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import launcher.EmployeeBookComponentFactory;
import launcher.UserComponentFactory;
import model.Sale;
import repository.sale.SaleRepository;
import repository.sale.SaleRepositoryMySQL;
import service.report.ReportService;
import view.AdminView;

import java.util.List;

public class AdminController {
    private final AdminView adminView;
    private final Stage primaryStage;
    private final Boolean componentsForTest;
    private final Long currentUserId;
    private final ReportService reportService;
    private final SaleRepository saleRepository;

    public AdminController(AdminView adminView, Stage primaryStage, Boolean componentsForTest, Long currentUserId, ReportService reportService, SaleRepository saleRepository) {
        this.adminView = adminView;
        this.primaryStage = primaryStage;
        this.componentsForTest = componentsForTest;
        this.currentUserId = currentUserId;
        this.reportService = reportService;
        this.saleRepository = saleRepository;

        this.adminView.addReportButtonListener(new ReportButtonListener());
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

    private class ReportButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            List<Sale> sales = saleRepository.findAllSalesLastMonth();
            if (sales.isEmpty()) {
                adminView.addDisplayAlertMessage("Report Info", "No Sales", "No sales found in the last 30 days.");
            } else {
                reportService.generateReport(sales);
                adminView.addDisplayAlertMessage("Success", "Report Generated", "PDF report saved.");
            }
        }
    }
}