package service.user;

import model.User;
import model.validation.Notification;

import java.util.List;

public interface UserService {
    List<User> findAll();
    boolean save(User user);
    boolean delete(User user);
    Notification<User> findByUsername(String username);
}
