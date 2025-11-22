import javafx.application.Application;
import javafx.stage.Stage;
import launcher.AdminComponentFactory;
import launcher.LoginComponentFactory;
import launcher.UserComponentFactory;
import view.AdminView;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginComponentFactory.getInstance(false, primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
