package controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
//import launcher.EmployeeComponentFactory;
//import launcher.LoginComponentFactory;
import launcher.BookComponentFactory;
import launcher.CustomerComponentFactory;
import launcher.LoginComponentFactory;
import launcher.UserComponentFactory;
import model.User;
import model.validator.Notification;
import service.login.AuthenticationService;
import view.LoginView;
import database.Constants;

public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authenticationService;


    public LoginController(LoginView loginView, AuthenticationService authenticationService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = authenticationService.login(username, password);

            if (loginNotification.hasErrors()){
                loginView.setActionTargetText(loginNotification.getFormattedErrors());
            }else{
                loginView.setActionTargetText("LogIn Successfull!");
                if(loginNotification.getResult().getRole().getRole().equals(Constants.Roles.ADMINISTRATOR)){
                    UserComponentFactory.getInstance(LoginComponentFactory.getComponentsForTests(), LoginComponentFactory.getStage());
                } else if (loginNotification.getResult().getRole().getRole().equals(Constants.Roles.EMPLOYEE)) {
                    BookComponentFactory.getInstance(LoginComponentFactory.getComponentsForTests(), LoginComponentFactory.getStage());
                } else {
                    CustomerComponentFactory.getInstance(LoginComponentFactory.getComponentsForTests(), LoginComponentFactory.getStage());
                }
            }
        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password);

            if (registerNotification.hasErrors()) {
                loginView.setActionTargetText(registerNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("Register successful!");
             }
        }
    }
}