package javafxapplication.controller;
import commonapplication.models.*;
import commonapplication.persistancemanagement.Parser;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
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
@FXML
private Button reserve;

//TODO : get restaurants from database, connection is yet to be tested
    public void setListView(ListView<String> listView) {
        this.listView = listView;
    }

    @FXML
private ListView<String> listView;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private final RestaurantController controller;

    private final ObservableList<Restaurant> list;
    private final String[] filters;


    public HomeController() {

        this.list= FXCollections.observableArrayList();
        this.controller =  new RestaurantController();
        controller.getAllRestaurants(this::setRestaurantList);
        filters= new String[]
        {"$", "$$", "$$$", "★", "★★","★★★","★★★★","★★★★★", "open now",
                Speciality.Vietnamesisch.name(),Speciality.Deutsch.name(),Speciality.Japanisch.name(),
        Speciality.Italienisch.name(),Speciality.Thailändisch.name(),Speciality.Sushi.name(),Speciality.Hamburger.name()
        ,Speciality.Mexikanisch.name(),Speciality.Döner.name(),Speciality.Türkisch.name(),Speciality.Griechisch.name(),
        Speciality.Pizza.name(),Speciality.Pizza.name(),Speciality.Indisch.name(),Speciality.Chinesisch.name(),Speciality.Spanisch.name()};


    }

    private List<String> toStringList(ObservableList<Restaurant> list) {
        return  list.stream().map(Restaurant::getName).collect(Collectors.toList());
    }

    public ObservableList<Restaurant> getList() {
        return list;
    }

    public void initialize(URL location, ResourceBundle resources) {
        engine = webView.getEngine();
        listView.getItems().addAll(toStringList(list));
        filterBox.getItems().addAll(filters);
        loadPage();
        filterBox.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue)
                        -> filter(newValue));
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


    /**
 filters the search through the value chosen by the user in the ChoiceBox
     */
    private void filter(String value) {
        int stars = -1;
        int priceRange = -1;
        boolean openNow = false;
        Speciality speciality = Speciality.Unbekannt;


        if (value.equals("$")) {
            priceRange = 1;
        }
        if (value.equals("$$")) {
            priceRange = 2;
        }
        if (value.equals("$$$")) {
            priceRange = 3;
        }
        if (value.equals("open now")) {
            openNow = true;
        }
        if (value.equals("★")) {
            stars = 1;
        }
        if (value.equals("★★")) {
            stars = 2;
        }
        if (value.equals("★★★")) {
            stars = 3;
        }
        if (value.equals("★★★★")) {
            stars = 4;
        }
        if (value.equals("★★★★★")) {
            stars = 5;
        }
        if (Arrays.stream(Speciality.values()).anyMatch(x -> x.name().equals(value))) {
            speciality = Speciality.valueOf(value);
        }

        controller.getAllRestaurantsFilter(stars, priceRange, openNow, speciality, this::setRestaurantList);
}
    private void setRestaurantList(List<Restaurant> restaurants) {
        Platform.runLater(()-> {
            list.clear();
            listView.getItems().clear();
            list.setAll(restaurants);
            listView.getItems().addAll(toStringList(list));
        });
    }

    public void makeReservation(ActionEvent e) throws IOException {
        if (e.getSource()==reserve){
            listView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
                String selectedItem = listView.getSelectionModel().getSelectedItem();
                Restaurant r = (Restaurant) list.stream().map(x-> x.getName().equals(selectedItem)? x : null);
            });

            goToReservation();
        }


    }
// the map is now resizable
    public void loadPage() {
        File localMapLink = new File("src/client/resources/simple_map.html");
        engine.load(localMapLink.toURI().toString());
    }

    /**
     returns to login page
     */
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
    public void goToReservation() throws IOException{

        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reservation.fxml"));
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

