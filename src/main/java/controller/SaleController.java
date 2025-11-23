package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.EmployeeBookComponentFactory;
import launcher.EmployeeBookComponentFactory;
import mapper.UserMapper;
import model.User;
import service.sale.SaleService;
import view.SaleView;
import view.model.EmployeeBookDTO;
import view.model.UserDTO;

public class SaleController {
    private final SaleView saleView;
    private final SaleService saleService;
    private final EmployeeBookDTO bookToSell;
    private final int quantityToSell;
    private final Long employeeId;
    private final Boolean componentsForTest;

    public SaleController(SaleView saleView, SaleService saleService, Boolean componentsForTest, EmployeeBookDTO bookToSell, int quantityToSell, Long employeeId) {
        this.saleView = saleView;
        this.saleService = saleService;
        this.bookToSell = bookToSell;
        this.quantityToSell = quantityToSell;
        this.employeeId = employeeId;
        this.componentsForTest = componentsForTest;
        this.saleView.addFinalizeButtonListener(new FinalizeButtonListener());
    }

    private class FinalizeButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            UserDTO selectedCustomer = saleView.getCustomerTableView().getSelectionModel().getSelectedItem();

            if (selectedCustomer != null) {
                User customerEntity = UserMapper.convertUserDTOToUser(selectedCustomer);

                Long bookId = bookToSell.getId();

                boolean sold = saleService.sell(bookId, customerEntity.getId(), employeeId, quantityToSell);

                if (sold) {
                    saleView.addDisplayAlertMessage("Success", "Sale Complete",
                            "Sold " + quantityToSell + " copies of " + bookToSell.getTitle());

                    returnToBookView();

                } else {
                    saleView.addDisplayAlertMessage("Error", "Sale Failed", "Could not complete transaction (Check Stock or Database).");
                }

            } else {
                saleView.addDisplayAlertMessage("Error", "No Customer Selected", "Please select a customer.");
            }
        }
    }

    private void returnToBookView() {
        EmployeeBookComponentFactory factory = EmployeeBookComponentFactory.getInstance(componentsForTest, saleView.getPrimaryStage(), employeeId);

        factory.getEmployeeBookController().refreshData();

        factory.getEmployeeView().show();
    }
}