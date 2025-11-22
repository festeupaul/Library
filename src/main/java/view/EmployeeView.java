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

import java.util.List;

public class EmployeeView {
    private TableView<EmployeeBookDTO> bookTableView;
    private final ObservableList<EmployeeBookDTO> booksObservableList;
    private TextField authorTextField;
    private TextField titleTextField;
    private Label authorLabel;
    private Label titleLabel;
    private TextField priceTextField;
    private TextField stockTextField;
    private Label priceLabel;
    private Label stockLabel;
    private Button saveButton;
    private Button deleteButton;
    private Stage primaryStage;
    private Button backButton;
    private  Scene scene;

    public EmployeeView(Stage primaryStage, List<EmployeeBookDTO> books) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Library");

        GridPane gridPane = new GridPane();
        initializeGridPage(gridPane);

        this.scene = new Scene(gridPane, 780, 500);
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

        gridPane.add(bookTableView, 0, 0, 6, 1);
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

        backButton = new Button("Back");
        gridPane.add(backButton, 7, 1);
    }

    public void addSaveButtonListener(EventHandler<ActionEvent> saveButtonListener){
        saveButton.setOnAction(saveButtonListener);
    }

    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener){
        deleteButton.setOnAction(deleteButtonListener);
    }

    public void addBackButtonListener(EventHandler<ActionEvent> listener) {
        backButton.setOnAction(listener);
    }

    public void addDisplayAlertMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public String getAuthor() {
        return authorTextField.getText();
    }

    public String getTitle() {
        return titleTextField.getText();
    }

    public String getPrice() {
        return priceTextField.getText();
    }

    public String getStock() {
        return stockTextField.getText();
    }

    public void addBookToObservableList(EmployeeBookDTO employeeBookDTO) {
        this.booksObservableList.add(employeeBookDTO);
    }

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
}