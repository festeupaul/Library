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

public class UserView {
    private TableView<UserDTO> userTableView;
    private final ObservableList<UserDTO> usersObservableList;

    private Button deleteButton;
    private Button markAdminButton;
    private Button markEmployeeButton;
    private Button backButton;
    private Scene scene;
    private Stage primaryStage;

    public UserView(Stage primaryStage, List<UserDTO> users) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("User Administration");

        GridPane gridPane = new GridPane();
        initializeGridPage(gridPane);

        this.scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);

        usersObservableList = FXCollections.observableArrayList(users);
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
        userTableView = new TableView<UserDTO>();
        userTableView.setPlaceholder(new Label("No users to display"));

        TableColumn<UserDTO, Long> idColumn = new TableColumn<UserDTO, Long>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<UserDTO, String> usernameColumn = new TableColumn<UserDTO, String>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<UserDTO, String> roleColumn = new TableColumn<UserDTO, String>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        idColumn.setPrefWidth(75);
        usernameColumn.setPrefWidth(237);
        roleColumn.setPrefWidth(154);

        userTableView.getColumns().addAll(idColumn, usernameColumn, roleColumn);
        userTableView.setItems(usersObservableList);

        gridPane.add(userTableView, 0, 0, 5, 1);
    }

    private void initActionOptions(GridPane gridPane) {
        deleteButton = new Button("Delete");
        gridPane.add(deleteButton, 0, 1);

        markAdminButton = new Button("Mark as Administrator");
        markAdminButton.setPrefWidth(172);
        gridPane.add(markAdminButton, 2, 1);

        markEmployeeButton = new Button("Mark as Employee");
        markEmployeeButton.setPrefWidth(172);
        gridPane.add(markEmployeeButton, 3, 1);

        backButton = new Button("Back");
        gridPane.add(backButton, 4, 1);
    }

    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener){
        deleteButton.setOnAction(deleteButtonListener);
    }

    public void addMarkAdminButtonListener(EventHandler<ActionEvent> listener){
        markAdminButton.setOnAction(listener);
    }

    public void addMarkEmployeeButtonListener(EventHandler<ActionEvent> listener){
        markEmployeeButton.setOnAction(listener);
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

    public void addUserToObservableList(UserDTO userDTO) {
        this.usersObservableList.add(userDTO);
    }

    public void removeUserFromObservableList(UserDTO userDTO) {
        this.usersObservableList.remove(userDTO);
    }

    public TableView<UserDTO> getUserTableView() {
        return userTableView;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void show() {
        primaryStage.setTitle("User Administration");
        primaryStage.setScene(this.scene);
        primaryStage.show();
    }
}