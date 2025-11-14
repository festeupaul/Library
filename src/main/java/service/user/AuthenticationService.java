package service.user;

import model.User;
import model.validator.Notification;

import javax.management.remote.NotificationResult;

public interface AuthenticationService {
    Notification<Boolean> register(String username, String password);

    Notification<User> login(String username, String password);

    boolean logout(User user);
}