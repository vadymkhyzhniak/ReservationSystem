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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

// login as guest to view app, still working on client-server connection
public class LoginController {

    private Stage stage;
    private Parent root;
    private final ObservableList<User> userList;
    private String username;
    private final File file= new File("src/server/resources/Users.dat");
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
    public void exit(ActionEvent e){
        stage= (Stage) cancel.getScene().getWindow();
        stage.close();
    }


    public void register(ActionEvent e) throws IOException {
     if (e.getSource()==register){

         var newUser = new User(userTxt.getText(), passTxt.getText().hashCode());
         if (Parser.userExists(userTxt.getText())){
             loginLabel.setText("User already exists, please login");
         }
         else{
             controller.addUser(newUser,this::setUserList);
         }



          userTxt.setText("");
          passTxt.setText("");
     }
   }
    public void login(ActionEvent e) throws IOException {


        if (e.getSource() == login) {
            String user = userTxt.getText();
            String pass = passTxt.getText();
            if (user.isBlank() || pass.isBlank()) {
                loginLabel.setText("Please enter username and password");
            }
          else if (Parser.userExists(user)){
              //    controller.authenticateUser(newUser, e, this::setAuthenticate);

                  setUsername(user);
                  changeToMainScene(e);
            }
          else {
              loginLabel.setText("User does not exist, please register");
            }

        }
        else if (e.getSource()==loginGuest){
            setUsername( "SomePinguin");
            changeToMainScene(e);
        }


    }

 /*   public void setAuthenticate(Boolean authenticate, ActionEvent e) {
        if(authenticate){
            changeToMainScene(e);
        }
    }*/

    private void setUserList(List<User> users) {
        Platform.runLater(()-> {
            userList.setAll(users);

        });
    }



    private void changeToMainScene(ActionEvent e) throws IOException {

        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Homepage.fxml"));
       root = loader.load();
HomeController controller1 = loader.getController();
controller1.showName(username);
            stage= (Stage) ((Node)e.getSource()).getScene().getWindow();

            stage.setWidth(600);
            stage.setHeight(400);
            Scene scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();


    }


}

