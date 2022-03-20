package controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import service.UserProfileService;

import java.util.Objects;

public class RegisterController {
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private TextField confirmPassword;

    @FXML
    private Label confirmPasswordLabel;

    @FXML
    private TextArea passwordLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private void singUp() {
        if(Objects.equals(password.getText(), confirmPassword.getText())) {
            UserProfileService userProfileService = new UserProfileService();
            userProfileService.newUser(username.getText(), password.getText());
            if(userProfileService.getDuplicateUserError() == null &&
                    userProfileService.getConstraintsError() == null)
                usernameLabel.setText("User created.");
            else {
                if(userProfileService.getDuplicateUserError() != null)
                    usernameLabel.setText(userProfileService.getDuplicateUserError());
                if(userProfileService.getConstraintsError() != null)
                    usernameLabel.setText(userProfileService.getConstraintsError());
            }
        }
        else {
            confirmPasswordLabel.setText("Passwords must match!");
        }
    }

}
