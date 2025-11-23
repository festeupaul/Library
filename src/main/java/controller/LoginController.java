package controller;

import database.Constants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.AdminComponentFactory;
import launcher.CustomerComponentFactory;
import launcher.EmployeeBookComponentFactory;
import model.User;
import model.validator.Notification;
import service.login.AuthenticationService;
import view.LoginView;

public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final Boolean componentsForTest;

    public LoginController(LoginView loginView, AuthenticationService authenticationService, Boolean componentsForTest) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.componentsForTest = componentsForTest;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = authenticationService.login(username, password);

            if (loginNotification.hasErrors()){
                loginView.setActionTargetText(loginNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("LogIn Successful!");

                User user = loginNotification.getResult();

                String role = user.getRole().getRole();

                if (role.equals(Constants.Roles.ADMINISTRATOR)){
                    AdminComponentFactory.getInstance(componentsForTest, loginView.getPrimaryStage(), user.getId())
                            .getAdminView()
                            .show();

                } else if (role.equals(Constants.Roles.EMPLOYEE)) {
                    EmployeeBookComponentFactory.getInstance(componentsForTest, loginView.getPrimaryStage(), user.getId())
                            .getEmployeeView()
                            .show();

                } else {
                    CustomerComponentFactory.getInstance(componentsForTest, loginView.getPrimaryStage())
                            .getCustomerView()
                            .show();
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