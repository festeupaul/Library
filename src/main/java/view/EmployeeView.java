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
import view.model.EmployeeBookDTO;

import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import view.model.builder.EmployeeBookDTOBuilder;

import java.util.List;
import java.util.Optional;

public class EmployeeView {
    private TableView<EmployeeBookDTO> bookTableView;
    private final ObservableList<EmployeeBookDTO> booksObservableList;

    private TextField authorTextField;
    private TextField titleTextField;
    private TextField priceTextField;
    private TextField stockTextField;

    private Label authorLabel;
    private Label titleLabel;
    private Label priceLabel;
    private Label stockLabel;

    private Button saveButton;
    private Button deleteButton;
    private Button sellButton;

    private Stage primaryStage;
    private Scene scene;

    public EmployeeView(Stage primaryStage, List<EmployeeBookDTO> books) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Library");

        GridPane gridPane = new GridPane();
        initializeGridPage(gridPane);

        this.scene = new Scene(gridPane, 850, 550);
        primaryStage.setScene(scene);

        booksObservableList = FXCollections.observableArrayList(books);
        initTableView(gridPane);

        initSaveOptions(gridPane);

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

        TableColumn<EmployeeBookDTO, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<EmployeeBookDTO, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<EmployeeBookDTO, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<EmployeeBookDTO, Integer> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        bookTableView.getColumns().addAll(titleColumn, authorColumn, priceColumn, stockColumn);
        bookTableView.setItems(booksObservableList);

        gridPane.add(bookTableView, 0, 0, 8, 1);
    }

    private void initSaveOptions(GridPane gridPane) {
        titleLabel = new Label("Title");
        gridPane.add(titleLabel, 0, 1);

        titleTextField = new TextField();
        gridPane.add(titleTextField, 1, 1);

        authorLabel = new Label("Author");
        gridPane.add(authorLabel, 2, 1);

        authorTextField = new TextField();
        gridPane.add(authorTextField, 3, 1);

        priceLabel = new Label("Price");
        gridPane.add(priceLabel, 0, 2);

        priceTextField = new TextField();
        gridPane.add(priceTextField, 1, 2);

        stockLabel = new Label("Stock");
        gridPane.add(stockLabel, 2, 2);

        stockTextField = new TextField();
        gridPane.add(stockTextField, 3, 2);

        saveButton = new Button("Save");
        gridPane.add(saveButton, 4, 2);

        deleteButton = new Button("Delete");
        gridPane.add(deleteButton, 5, 2);

        sellButton = new Button("Sell");
        gridPane.add(sellButton, 6, 2);
    }

    public EmployeeBookDTO showUpdateDialog(EmployeeBookDTO currentBook) {
        Dialog<EmployeeBookDTO> dialog = new Dialog<>();
        dialog.setTitle("Update Book");
        dialog.setHeaderText("Update: " + currentBook.getTitle());

        ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField titleField = new TextField();
        titleField.setPromptText(currentBook.getTitle());

        TextField authorField = new TextField();
        authorField.setPromptText(currentBook.getAuthor());

        TextField priceField = new TextField();
        priceField.setPromptText(String.valueOf(currentBook.getPrice()));

        TextField stockField = new TextField();
        stockField.setPromptText(String.valueOf(currentBook.getStock()));

        grid.add(new Label("Title:"), 0, 0); grid.add(titleField, 1, 0);
        grid.add(new Label("Author:"), 0, 1); grid.add(authorField, 1, 1);
        grid.add(new Label("Price:"), 0, 2); grid.add(priceField, 1, 2);
        grid.add(new Label("Stock:"), 0, 3); grid.add(stockField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                double newPrice = priceField.getText().isEmpty() ? -1 : Double.parseDouble(priceField.getText());
                int newStock = stockField.getText().isEmpty() ? -1 : Integer.parseInt(stockField.getText());

                return new EmployeeBookDTOBuilder()
                        .setId(currentBook.getId())
                        .setTitle(titleField.getText())
                        .setAuthor(authorField.getText())
                        .setPrice(newPrice)
                        .setStock(newStock)
                        .build();
            }
            return null;
        });

        Optional<EmployeeBookDTO> result = dialog.showAndWait();
        return result.orElse(null);
    }

    public void addSaveButtonListener(EventHandler<ActionEvent> saveButtonListener){
        saveButton.setOnAction(saveButtonListener);
    }

    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener){
        deleteButton.setOnAction(deleteButtonListener);
    }

    public void addTableMouseListener(EventHandler<MouseEvent> mouseListener) {
        bookTableView.setOnMouseClicked(mouseListener);
    }

    public void addSellButtonListener(EventHandler<ActionEvent> sellButtonListener){
        sellButton.setOnAction(sellButtonListener);
    }

    public void addDisplayAlertMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public String getAuthor() { return authorTextField.getText(); }
    public String getTitle() { return titleTextField.getText(); }
    public String getPrice() { return priceTextField.getText(); }
    public String getStock() { return stockTextField.getText(); }

    public void removeBookFromObservableList(EmployeeBookDTO employeeBookDTO) {
        this.booksObservableList.remove(employeeBookDTO);
    }

    public TableView<EmployeeBookDTO> getBookTableView() {
        return bookTableView;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void show(){
        primaryStage.setTitle("Library (Employee)");
        primaryStage.setScene(this.scene);
        primaryStage.show();
    }

    public void refreshTableData(List<EmployeeBookDTO> newBooks) {
        this.booksObservableList.clear();
        this.booksObservableList.addAll(newBooks);
    }
}