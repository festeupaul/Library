package controller;

import database.Constants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.AdminComponentFactory;
import mapper.UserMapper;
import model.User;
import service.user.UserService;
import view.UserView;
import view.model.UserDTO;

public class UserController {
    private final UserView userView;
    private final UserService userService;

    public UserController(UserView userView, UserService userService) {
        this.userView = userView;
        this.userService = userService;

        this.userView.addDeleteButtonListener(new DeleteButtonListener());
        this.userView.addMarkAdminButtonListener(new MarkAdminButtonListener());
        this.userView.addMarkEmployeeButtonListener(new MarkEmployeeButtonListener());
        this.userView.addBackButtonListener(new BackButtonListener());
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            UserDTO userDTO = userView.getUserTableView().getSelectionModel().getSelectedItem();

            if (userDTO != null) {
                User user = UserMapper.convertUserDTOToUser(userDTO);

                boolean deletionSuccessful = userService.delete(user);

                if (deletionSuccessful) {
                    userView.addDisplayAlertMessage("Delete Successful",
                            "User Deleted",
                            "User " + userDTO.getUsername() + " was successfully deleted.");
                    userView.removeUserFromObservableList(userDTO);
                } else {
                    userView.addDisplayAlertMessage("Delete Error",
                            "Problem at deleting User",
                            "There was a problem with the database. Please try again.");
                }
            } else {
                userView.addDisplayAlertMessage("Selection Error",
                        "No User Selected",
                        "You must select a user from the table before pressing Delete.");
            }
        }
    }

    private class MarkAdminButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            changeUserRole(Constants.Roles.ADMINISTRATOR);
        }
    }

    private class MarkEmployeeButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            changeUserRole(Constants.Roles.EMPLOYEE);
        }
    }

    private class BackButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            AdminComponentFactory.getInstance(false, userView.getPrimaryStage())
                    .getAdminView()
                    .show();
        }
    }

    private void changeUserRole(String newRole) {
        UserDTO userDTO = userView.getUserTableView().getSelectionModel().getSelectedItem();

        if (userDTO != null) {
            String oldRole = userDTO.getRole();

            if (newRole.equals(oldRole)) {
                userView.addDisplayAlertMessage("Info", "Role Check",
                        "The user is already marked as " + newRole + ".");
                return;
            }

            userDTO.setRole(newRole);

            User user = UserMapper.convertUserDTOToUser(userDTO);

            boolean updateSuccessful = userService.update(user);

            if (updateSuccessful) {
                userView.addDisplayAlertMessage("Update Successful",
                        "Role Changed",
                        "User " + userDTO.getUsername() + " is now " + newRole + ".");

                userView.getUserTableView().refresh();
            } else {
                userDTO.setRole(oldRole);
                userView.addDisplayAlertMessage("Update Error",
                        "Problem updating role",
                        "Could not update the role in the database.");
            }
        } else {
            userView.addDisplayAlertMessage("Selection Error",
                    "No User Selected",
                    "Please select a user to change their role.");
        }
    }
}