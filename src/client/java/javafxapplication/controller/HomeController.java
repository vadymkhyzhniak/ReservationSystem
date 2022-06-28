package javafxapplication.controller;

import commonapplication.models.Restaurant;
import commonapplication.models.Speciality;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class HomeController implements Initializable {
    @FXML
    private WebView webView;
    @FXML
    private WebEngine engine;
    @FXML
    private Button exit;
    @FXML
    private Button search ;
    @FXML
    private TextField searchBar;
    @FXML
    private ChoiceBox<String> filterBox;


    public void setListView(ListView<String> listView) {
        this.listView = listView;
    }

    @FXML
private ListView<String> listView;
    private Stage stage;
    private Scene scene;
    private Parent root;

    //TODO: replace this list with the server database
    private final List<Restaurant> list= Arrays.asList
            (new Restaurant("McDonalds", Speciality.Hamburger,3,1,true)
                    ,new Restaurant("L'osteria", Speciality.Pizza,4,2,false),
                    new Restaurant("Thai Imbiss", Speciality.Thailändisch,5,3,true));

    private final RestaurantController controller;

    public HomeController() {
        this.controller =  new RestaurantController();
    }

    private List<String> toStringList(List<Restaurant> list) {
        return list.stream().map(Restaurant::getName).collect(Collectors.toList());
    }


    private final String[] filters= {"price range","cuisine","stars","open now"};
    public void initialize(URL location, ResourceBundle resources) {
        engine = webView.getEngine();
        listView.getItems().addAll(toStringList(list));
filterBox.getItems().addAll(filters);
        loadPage();
    }
public void search(ActionEvent e){
        if (e.getSource()==search){
            if (searchBar.getText().isBlank()) {
                listView.getItems().clear();
                listView.getItems().addAll(toStringList(list));
            }
            else{
                listView.getItems().clear();
                listView.getItems().addAll(searchList(searchBar.getText(),toStringList(list)));
            }

        }

}
private List<String> searchList(String words, List<String> list){
        List<String> wordsList =Arrays.asList(words.trim().split(" "));
        return list.stream()
                .filter(s ->wordsList.stream().allMatch(y-> s.toLowerCase().contains(y.toLowerCase())))
                .collect(Collectors.toList());
}
public void filter(ActionEvent e){
        //TODO:filter restaurants
 /*
    if (e.getSource()==filterBox){
    switch (filterBox.getValue()) {
            case "price range" ->;
            case "cuisine" -> ;
            case "stars" -> ;
            case "open now" ->;
            default ->
        }


    }
  */



}
// the map is now resizable
    public void loadPage() {
        File localMapLink = new File("src/client/resources/simple_map.html");
        engine.load(localMapLink.toURI().toString());
    }
    public void exit() throws IOException {
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent root = loader.load();

        stage= (Stage) exit.getScene().getWindow();
        stage.setWidth(600);
        stage.setHeight(400);
        Scene scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}

