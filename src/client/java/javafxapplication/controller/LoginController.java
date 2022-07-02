package javafxapplication.controller;

import commonapplication.models.User;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// login as guest to view app, still working on client-server connection
public class LoginController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private final List<User> userList;

    public List<User> getUserList() {
        return userList;
    }

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
    private long id;

    public LoginController() {
        this.userList =new ArrayList<>();
        this.controller = new UserController();
        this.id = 0;
    }
    public void exit(ActionEvent e){
        stage= (Stage) cancel.getScene().getWindow();
        stage.close();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void register(ActionEvent e) throws IOException {
     if (e.getSource()==register){

         var newUser = new User(userTxt.getText(), passTxt.getText().hashCode());
             controller.addUser(newUser,this::setUserList);

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
            //else if (Parser.userExists(getId())){
            //TODO: authenticate user
            //    controller.authenticateUser(userList.get(0),this::setUserList);
            //}
                    else
                {
                loginLabel.setText("Invalid user, please enter correct username and password");
            }


        }
            else if(e.getSource()==loginGuest){
                Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Homepage.fxml"));
                Parent root = loader.load();

                stage= (Stage) ((Node)e.getSource()).getScene().getWindow();
                stage.setWidth(600);
                stage.setHeight(400);
                Scene scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();

            }


    }

    private void setUserList(List<User> users) {
        Platform.runLater(()-> {
            for (int i = 0; i < users.size(); i++) {
                userList.set(i,users.get(i));

            }
        });
    }

    // We change the scene here if the authentication was successful
    // use on the authentication method like
    // userController.authenticate(user, this::changeToMainScene);
    private void changeToMainScene() {

    }
 private boolean parseMatch(String username)  {
        StringBuilder parser= new StringBuilder();
        try{
            BufferedReader r= new BufferedReader(new FileReader(this.file));
            String line=r.readLine();
            while(line!=null){
                parser.append(line).append("\n");
                line = r.readLine();
            }
            r.close();
           if (!parser.isEmpty()){
               String [] s= parser.toString().split("\\R");
             return Arrays.asList(s).contains(username);
           }
           else {
               return false;
           }
        }
       catch(Exception ignored){

       }
        return false;
    }

    public static void main(String[] args) throws IOException {
      LoginController controller =new LoginController();
    //  controller.register();
       controller.userList.forEach(System.out::println);
    }
}

