package service.user;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;

import static database.Constants.Roles.CUSTOMER;


public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public AuthenticationServiceImpl(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public Notification<Boolean> register(String username, String password, String role) {
        Notification<Boolean> userRegisterNotification = new Notification<>();

        boolean usernameExists = userRepository.existsByUsername(username);

        if (usernameExists) {
            userRegisterNotification.addError("User already exits!");
            userRegisterNotification.setResult(Boolean.FALSE);
            return userRegisterNotification;
        }

        Role userRole = rightsRolesRepository.findRoleByTitle(role);

        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(userRole))
                .build();

        UserValidator userValidator = new UserValidator(user);

        boolean userValid = userValidator.validate();


        if (!userValid) {
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
        } else {
            user.setPassword(hashPassword(password));
            userRegisterNotification.setResult(userRepository.save(user));
        }

        return userRegisterNotification;
    }

    @Override
    public Notification<User> login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, hashPassword(password));
    }

    @Override
    public boolean logout(User user) {
        return false;
    }

    private String hashPassword(String password) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
