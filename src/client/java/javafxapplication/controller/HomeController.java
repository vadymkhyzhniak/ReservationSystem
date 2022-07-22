package javafxapplication.controller;

import commonapplication.models.Restaurant;
import commonapplication.models.Speciality;
import commonapplication.persistancemanagement.DataHandler;
import commonapplication.persistancemanagement.Parser;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private Button calendar;

    @FXML
    private Button search;
    @FXML
    private TextField searchBar;
    @FXML
    private ChoiceBox<String> filterBox;
    @FXML
    private Button reserve;
    @FXML
    private Label welcome;
    @FXML
    private ListView<String> listView;
    private Stage stage;
    private Parent root;


    private final RestaurantController controller;

    private final ObservableList<Restaurant> list;
    private Restaurant r;
    private final String[] filters;
    private String name;
    private String restaurant;

    public void showName(String name) {
        welcome.setText("Welcome " + name);
        this.name = welcome.getText().substring(7);
    }

    public HomeController() {

        this.list = FXCollections.observableArrayList();
        this.controller = new RestaurantController();
        r = new Restaurant(Speciality.Unbekannt, -1, -1, false);
        controller.getAllRestaurants(this::setRestaurantList);
        filters = new String[]
                {"$", "$$", "$$$", "★", "★★", "★★★", "★★★★", "★★★★★", "open now",
                        Speciality.Vietnamesisch.name(), Speciality.Deutsch.name(), Speciality.Japanisch.name(),
                        Speciality.Italienisch.name(), Speciality.Thailändisch.name(), Speciality.Sushi.name(), Speciality.Hamburger.name()
                        , Speciality.Mexikanisch.name(), Speciality.Döner.name(), Speciality.Türkisch.name(), Speciality.Griechisch.name(),
                        Speciality.Pizza.name(), Speciality.Pizza.name(), Speciality.Indisch.name(), Speciality.Chinesisch.name(), Speciality.Spanisch.name()};

    }

    public void setR(Restaurant r) {
        this.r = r;
    }

    private List<String> toStringList(ObservableList<Restaurant> list) {
        return list.stream().map(Restaurant::getName).collect(Collectors.toList());
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

    public void search(ActionEvent e) {
        if (e.getSource() == search) {
            if (searchBar.getText().isBlank()) {
                listView.getItems().clear();
                listView.getItems().addAll(toStringList(list));
            } else {
                listView.getItems().clear();
                listView.getItems().addAll(searchList(searchBar.getText(), toStringList(list)));
            }

        }
    }

    private List<String> searchList(String words, List<String> list) {
        List<String> wordsList = Arrays.asList(words.trim().split(" "));
        return list.stream()
                .filter(s -> wordsList.stream().allMatch(y -> s.toLowerCase().contains(y.toLowerCase())))
                .collect(Collectors.toList());
    }


    /**
     * filters the search through the value chosen by the user in the ChoiceBox
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

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
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
        if (e.getSource() == reserve) {
            if (name.isEmpty()) {
                welcome.setText("please log in to proceed");
            }
            setRestaurant(listView.getSelectionModel().getSelectedItem());
            goToReservation();
        }


    }

    // the map is now resizable
    public void loadPage() {
        File localMapLink = new File("src/client/resources/simple_map.html");
        engine.load(localMapLink.toURI().toString());
    }

    /**
     * returns to login page
     */
    public void exit() throws IOException {
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
        root = loader.load();


        stage = (Stage) exit.getScene().getWindow();

        stage.setHeight(500);
        stage.setWidth(700);

        Scene scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void goToReservation() throws IOException {
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reservation.fxml"));
        root = loader.load();
        ReservationSceneController controller = loader.getController();
        controller.displayInfo(name, restaurant);

        stage = (Stage) exit.getScene().getWindow();
        stage.setWidth(700);
        stage.setHeight(500);
        Scene scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }


    public void displayCalendar(ActionEvent e) {
        if (e.getSource() == calendar) {
            if (name == null || name.isBlank()) {
                return;
            }

            if (name.trim().equals("SomePinguin")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Guests have no Calendars..");
                alert.setContentText("Guests don't really have calendars, do they?" + System.lineSeparator() + "You need to be logged in for this functionality");
                alert.setTitle("No Calendar for you..");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Calendar");
                List<String> allReservations = Parser.getAllReservations();
                alert.setHeaderText("Your Calendar is not organized.. just like me");
                for (String res : allReservations) {
                    if (res.contains("<PID:" + name.trim() + ">")) {
                        String[] temp = res.split("RID:", 2);
                        temp = temp[1].split("><");
                        File restFile = new File("src/server/resources/Restaurants/" + temp[0] + ".dat");
                        String restData = DataHandler.readFile(restFile);
                        String[] temp1 = restData.split("NAME:", 2);
                        temp1 = temp1[1].split("><", 2);

                        String conf = temp[6].substring(2).equals("true") ? " And it is confirmed" : " But it is not confirmed";
                        String resX = "Reservation in " + temp1[0] + " , on " + temp[5].substring(3)
                                + " from " + temp[3].substring(3) + " to " + temp[4].substring(3) + conf;
                        alert.setHeaderText(alert.getHeaderText() + System.lineSeparator() + System.lineSeparator() + resX);
                    }
                }
                alert.showAndWait();
            }
        }
    }
}

