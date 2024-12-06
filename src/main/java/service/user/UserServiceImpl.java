package service.user;

import model.User;
import model.validation.Notification;
import repository.user.UserRepository;

import java.util.List;

public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean save(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean delete(User user) {
        return userRepository.delete(user);
    }

    @Override
    public Notification<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
