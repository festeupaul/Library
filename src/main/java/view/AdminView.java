package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AdminView {
    private Button libraryButton;
    private Button usersButton;
    private Stage primaryStage;
    private Scene scene;

    public AdminView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Admin Dashboard");

        GridPane grid = new GridPane();
        initializeGridPage(grid);

        this.scene = new Scene(grid, 780, 500);
        primaryStage.setScene(scene);

        initSaveOptions(grid);

        primaryStage.show();
    }

    private void initializeGridPage(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void initSaveOptions(GridPane gridPane) {
        libraryButton = new Button("Library Management");
        libraryButton.setPrefSize(200, 50);
        gridPane.add(libraryButton, 0, 0);

        usersButton = new Button("User Management");
        usersButton.setPrefSize(200, 50);
        gridPane.add(usersButton, 0, 1);
    }

    public void addLibraryButtonListener(EventHandler<ActionEvent> libraryManagementListener) {
        libraryButton.setOnAction(libraryManagementListener);
    }

    public void addUsersButtonListener(EventHandler<ActionEvent> userManagementListener) {
        usersButton.setOnAction(userManagementListener);
    }

    public void show() {
        primaryStage.setTitle("Admin Dashboard");
        primaryStage.setScene(this.scene);
        primaryStage.show();
    }
}