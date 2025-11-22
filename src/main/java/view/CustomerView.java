package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.model.CustomerBookDTO;

import java.util.List;

public class CustomerView {
    private TableView<CustomerBookDTO> bookTableView;
    private final ObservableList<CustomerBookDTO> booksObservableList;
    private Stage primaryStage;
    private Scene scene;

    public CustomerView(Stage primaryStage, List<CustomerBookDTO> books) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Library (Customer)");

        GridPane gridPane = new GridPane();
        initializeGridPage(gridPane);

        this.scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);

        booksObservableList = FXCollections.observableArrayList(books);
        initTableView(gridPane);

        primaryStage.show();
    }

    private void initializeGridPage(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void initTableView(GridPane gridPane) {
        bookTableView = new TableView<>();
        bookTableView.setPlaceholder(new Label("No books to display"));

        TableColumn<CustomerBookDTO, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<CustomerBookDTO, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<CustomerBookDTO, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        bookTableView.getColumns().addAll(titleColumn, authorColumn, priceColumn);
        bookTableView.setItems(booksObservableList);

        bookTableView.setPrefWidth(680);
        bookTableView.setPrefHeight(400);

        bookTableView.setRowFactory(tv -> {
            TableRow<CustomerBookDTO> row = new TableRow<>();

            row.itemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null && newVal.getStock() <= 0) {
                    row.setStyle("-fx-opacity: 0.5; -fx-background-color: #f0f0f0;");
                } else {
                    row.setStyle("");
                }
            });
            return row;
        });

        gridPane.add(bookTableView, 0, 0);
    }

    public void addDisplayAlertMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void show() {
        primaryStage.setTitle("Library (Customer)");
        primaryStage.setScene(this.scene);
        primaryStage.show();
    }

    public TableView<CustomerBookDTO> getBookTableView() {
        return bookTableView;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}