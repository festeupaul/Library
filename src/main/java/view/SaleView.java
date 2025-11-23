package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.model.UserDTO;

import java.util.List;

public class SaleView {
    private TableView<UserDTO> customerTableView;
    private final ObservableList<UserDTO> customersObservableList;

    private Button finalizeButton;

    private Scene scene;
    private Stage primaryStage;

    public SaleView(Stage primaryStage, List<UserDTO> customers) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Select Customer for Sale");

        GridPane gridPane = new GridPane();
        initializeGridPage(gridPane);

        this.scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);

        customersObservableList = FXCollections.observableArrayList(customers);
        initTableView(gridPane);

        initActionOptions(gridPane);

        this.primaryStage = primaryStage;
        primaryStage.show();
    }

    private void initializeGridPage(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void initTableView(GridPane gridPane) {
        customerTableView = new TableView<UserDTO>();
        customerTableView.setPlaceholder(new Label("No customers found"));

        TableColumn<UserDTO, String> usernameColumn = new TableColumn<UserDTO, String>("Customer Name/Email");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        usernameColumn.setPrefWidth(450);

        customerTableView.getColumns().add(usernameColumn);
        customerTableView.setItems(customersObservableList);

        customerTableView.setPrefHeight(300);

        gridPane.add(customerTableView, 0, 0, 1, 1);
    }

    private void initActionOptions(GridPane gridPane) {
        finalizeButton = new Button("Finalize Sale");
        finalizeButton.setPrefWidth(200);
        gridPane.add(finalizeButton, 0, 1);
    }

    public void addFinalizeButtonListener(EventHandler<ActionEvent> listener) {
        finalizeButton.setOnAction(listener);
    }


    public void addDisplayAlertMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public TableView<UserDTO> getCustomerTableView() {
        return customerTableView;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void show() {
        primaryStage.setTitle("Select Customer for Sale");
        primaryStage.setScene(this.scene);
        primaryStage.show();
    }
}