package javafxapplication;
import javafx.application.Platform;
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


import java.io.IOException;
import java.util.List;


public class LoginController {

    private Stage stage;
    private Scene scene;
    private Parent root;

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

    public void exit(ActionEvent e){
        stage= (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void login(ActionEvent e) throws IOException {
        if (e.getSource() == login){
            if (userTxt.getText().isBlank()||passTxt.getText().isBlank()){
                loginLabel.setText("Please enter username and password");
                return;
            }
        }
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Homepage.fxml"));
        root=loader.load();

        stage= (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.setWidth(600);
        stage.setHeight(400);
        scene= new Scene(root,visualBounds.getWidth(), visualBounds.getHeight());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }


    // We change the scene here if the authentication was successful
    // use on the authentication method like
    // userController.authenticate(user, this::changeToMainScene);
    private void changeToMainScene() {

    }
}

