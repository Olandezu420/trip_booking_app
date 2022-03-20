package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.User;
import model.UserHolder;
import org.example.App;
import service.UserService;

import java.io.IOException;

public class UserController {
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Label userLabel;

    @FXML
    private  Label passwordLabel;

    @FXML
    private Button register;

    @FXML
    public void onRegister() throws IOException {
        App.open("Register", "register", 200, 400);
    }

    @FXML
    public void onLogin() throws IOException {
        UserService userService = new UserService();
        User user = userService.login(username.getText(), password.getText());
        userLabel.setText(userService.getNotFoundError());
        passwordLabel.setText(userService.getWrongPasswordError());

        if(userService.getNotFoundError() != null || userService.getWrongPasswordError() != null)
            return;

        System.out.println(user.getAgencyProfile());
        if(user.getAgencyProfile() != null) {
            App.open("Agency Control", "agency", 950, 620);
        }
        else {
            UserHolder.getInstance().setUser(user);
            App.open("User Control", "user", 980, 600);
        }
    }
}
