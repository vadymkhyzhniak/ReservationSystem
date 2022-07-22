package javafxapplication.controller;

import commonapplication.models.User;
import commonapplication.persistancemanagement.Parser;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

// login as guest to view app, still working on client-server connection
public class LoginController {

    private Stage stage;
    private Parent root;
    private final ObservableList<User> userList;
    private String username;
    private final File file = new File("src/server/resources/Users.dat");
    private final UserController controller;
    @FXML
    private Button cancel;
    @FXML
    private Button loginGuest;
    @FXML
    private Button login;
    @FXML
    private Label loginLabel;
    @FXML
    private TextField userTxt;
    @FXML
    private PasswordField passTxt;
    @FXML
    private Button register;

    public void setUsername(String username) {
        this.username = username;
    }

    private Boolean authenticate;

    public LoginController() {
        this.userList = FXCollections.observableArrayList();
        this.controller = new UserController();
    }

    public void exit(ActionEvent e) {
        stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }


    public void register(ActionEvent e) throws IOException {
        if (e.getSource() == register) {
            loginLabel.setText("");
            var newUser = new User(userTxt.getText(), passTxt.getText().hashCode());
            if (userTxt.getText().isBlank() == true || passTxt.getText().isBlank() == true || userTxt.getText().contains(",") || userTxt.getText().contains("/")
                    || userTxt.getText().contains(";") || userTxt.getText().contains("<") || userTxt.getText().contains(">") || userTxt.getText().contains(":")
                    || userTxt.getText().toLowerCase(Locale.ROOT).contains("ö") || userTxt.getText().toLowerCase(Locale.ROOT).contains("ä")
                    || userTxt.getText().toLowerCase(Locale.ROOT).contains("ü") || userTxt.getText().toLowerCase(Locale.ROOT).contains("ß")) {
                if (userTxt.getText().isBlank() == true && passTxt.getText().isBlank() == true) {
                    loginLabel.setText("You do realise you need a username and a password, right?");
                } else if (userTxt.getText().isBlank() == true) {
                    loginLabel.setText("So.. How should we call you?");
                } else if (passTxt.getText().isBlank() == true) {
                    loginLabel.setText("Did you maybe forget something?");
                } else {
                    loginLabel.setText("Usernames must be a combination of " + System.lineSeparator() + "characters from the english alphabet,-,_ and numbers");
                }
            } else {

                if (Parser.userExists(userTxt.getText())) {
                    loginLabel.setText("User already exists, please login");
                } else {
                    controller.addUser(newUser, this::setUserList);
                }
                passTxt.setText("");
                loginLabel.setText("Registration successful!" + System.lineSeparator() + "Enter your password to log in");
            }
        }
    }

    public void login(ActionEvent e) throws IOException {

        if (e.getSource() == login) {
            String user = userTxt.getText();
            String pass = passTxt.getText();
            String passX = "";
            if (user != null && user.length() > 0) {
                User userX = Parser.getUserByUsername(user);
                if (userX != null) {
                    passX = String.valueOf(userX.getPasswordHash());
                }
            }
            if (user.isBlank() || pass.isBlank()) {
                loginLabel.setText("Please enter username and password"); //
            } else if (Parser.userExists(user)) {
                //    controller.authenticateUser(newUser, e, this::setAuthenticate);
                if (passX.equals(String.valueOf(pass.hashCode()))) {
                    setUsername(user);
                    changeToMainScene(e);
                } else {
                    loginLabel.setText("You have entered the wrong password! Try again!" + System.lineSeparator() + "Forgot your password? Sounds like a You problem..");
                }
            } else {
                loginLabel.setText("User does not exist, please register");
            }

        } else if (e.getSource() == loginGuest) {
            setUsername("SomePinguin");
            changeToMainScene(e);
        }


    }

 /*   public void setAuthenticate(Boolean authenticate, ActionEvent e) {
        if(authenticate){
            changeToMainScene(e);
        }
    }*/

    private void setUserList(List<User> users) {
        Platform.runLater(() -> {
            userList.setAll(users);

        });
    }


    private void changeToMainScene(ActionEvent e) throws IOException {

        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Homepage.fxml"));
        root = loader.load();
        HomeController controller1 = loader.getController();
        controller1.showName(username);
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

        stage.setWidth(600);
        stage.setHeight(400);
        Scene scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();


    }


}

