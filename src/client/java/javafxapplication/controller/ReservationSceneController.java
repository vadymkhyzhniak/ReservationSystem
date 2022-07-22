package javafxapplication.controller;

import commonapplication.models.Generator;
import commonapplication.models.Reservation;
import commonapplication.models.Restaurant;
import commonapplication.models.Table;
import commonapplication.persistancemanagement.Parser;
import commonapplication.persistancemanagement.Saver;
import javafx.application.Platform;
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
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class ReservationSceneController implements Initializable {

    private ReservationController controller;
    private final ObservableList<Reservation> reservationList;
    private LocalDate date;
    private LocalTime reservationStart, reservationEnd;
    private boolean verify = false;
    private RestaurantController restaurantController;
    @FXML
    private Spinner<Integer> guests;
    private int guestValue;
    @FXML
    private DatePicker calender;

    @FXML
    private Button confirmReservation;
    @FXML
    private Button cancelReservation;
    @FXML
    private Button makeReservation;
    @FXML
    private Button home;
    @FXML
    private Button confirmDate;
    @FXML
    private ComboBox<String> start;
    @FXML
    private ComboBox<String> end;
    @FXML
    private Label prompt;
    @FXML
    private Button availableTables;
    @FXML
    private Button t0;
    @FXML
    private Button t1;
    @FXML
    private Button t2;
    @FXML
    private Button t3;
    @FXML
    private Button t4;
    @FXML
    private Button t5;
    @FXML
    private Button t6;
    @FXML
    private Button t7;
    @FXML
    private Button t8;
    @FXML
    private Button t9;
    @FXML
    private Button t10;
    @FXML
    private Button t11;
    @FXML
    private Button t12;
    @FXML
    private Button t13;
    @FXML
    private Button t14;
    @FXML
    private Button t15;
    private Button[] buttons;
    private final List<String> times = new ArrayList<String>();
    private Table table;
    private Restaurant restaurant;
    private String username;
    @FXML
    private Label info;
    private boolean confirm = false;


    public ReservationSceneController() {
        this.controller = new ReservationController();
        this.restaurantController = new RestaurantController();
        this.reservationList = FXCollections.observableArrayList();
        buttons = new Button[]{t0, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15};
        for (int i = 8; i < 24; i++) {
            String time = i + ":00";
            if (i < 10) {
                time = "0" + time;
            }
            times.add(time);


        }

    }

    public void displayInfo(String name, String restaurant) {
        String s = "Welcome " + name + "\n Restaurant: " + restaurant;
        info.setText(s);
        restaurantController.getRestaurant(info.getText().substring(22 + name.length()), this::setRestaurant);
        setUsername(name.trim());
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void chooseDate(ActionEvent e) {

        date = calender.getValue();
        if (date.isBefore(LocalDate.now())) {
            prompt.setText("Do you have a time machine?");
            confirm = false;
            return;
        }
        confirm = !date.toString().isBlank();

    }

    public void pickTime(ActionEvent e) {
        if (e.getSource().equals(confirmDate)) {
            if (start == null || end == null) {
                prompt.setText("Please chose the period of your reservation");
                this.verify = false;
            }
            if (start.getValue().isBlank() || end.getValue().isBlank()) {
                prompt.setText("please provide reservation start and end");
                this.verify = false;
            }
            this.reservationStart = LocalTime.parse(start.getValue());
            this.reservationEnd = LocalTime.parse(end.getValue());

            if (reservationStart.isBefore(reservationEnd)) {
                this.verify = true;
            } else {
                prompt.setText("Starting time seems to be equal to," + System.lineSeparator() + "or after ending time");
                this.verify = false;
            }
        }
    }


    private Reservation reserve() {
        Random rand = new Random();
        if (restaurant.getTables().length > 0) {
            while (this.table == null) {
                this.table = restaurant.getTables()[rand.nextInt(0, restaurant.getTables().length)];
            }
        } else {
            this.table = new Table();
        }
        Reservation reservation = new Reservation(reservationStart, reservationEnd, username, restaurant, table, date);
        return reservation;
    }


    public void makeReservation(ActionEvent e) {
        if (e.getSource().equals(makeReservation)) {
            if (verify && confirm) {
                Random rand = new Random();
                if (restaurant.getTables().length > 0) {
                    while (this.table == null) {
                        this.table = restaurant.getTables()[rand.nextInt(0, restaurant.getTables().length)];
                    }
                } else {
                    this.table = new Table();
                }
                Reservation reservation = new Reservation(reservationStart, reservationEnd, username, restaurant, table, date);
                info.setText("\nTable has been successfully booked on " + date.toString() + "\n from: " + reservationStart.toString() + " to " + reservationEnd.toString());
                controller.addReservation(reservation, this::setReservationList);
            } else {
                prompt.setText("failed to make a reservation," + System.lineSeparator() + "please choose a table before proceeding");
            }
        }

    }

    public void confirmReservation(ActionEvent e) {
        if (e.getSource().equals(confirmReservation)) {
            if (verify && confirm) {
                Random rand = new Random();
                if (restaurant.getTables().length > 0) {
                    while (this.table == null) {
                        this.table = restaurant.getTables()[rand.nextInt(0, restaurant.getTables().length)];
                    }
                } else {
                    this.table = new Table();
                }
                Reservation reservation;
                if (Parser.reservationExists(Generator.generateUniqueId(reservationStart.toString(),
                        reservationEnd.toString(), username, date.toString()))) {
                    reservation = new Reservation(reservationStart, reservationEnd, username, restaurant, table, date);
                    Saver.confirmReservation(reservation);
                    info.setText("Your reservation has been successfully confirmed");
                } else {
                    info.setText("The reservation you are trying" + System.lineSeparator() + "to confirm doesn't even exist.." + System.lineSeparator() + System.lineSeparator() + "Maybe make it first?");
                }
            } else {
                prompt.setText("failed to confirm the reservation," + System.lineSeparator() + "please choose a table before proceeding");
            }
        }

    }


    public void cancelReservation(ActionEvent e) {
        if (e.getSource().equals(cancelReservation)) {
            if (verify && confirm) {
                Random rand = new Random();
                if (restaurant.getTables().length > 0) {
                    while (this.table == null) {
                        this.table = restaurant.getTables()[rand.nextInt(0, restaurant.getTables().length)];
                    }
                } else {
                    this.table = new Table();
                }
                Reservation reservation;
                if (Parser.reservationExists(Generator.generateUniqueId(reservationStart.toString(),
                        reservationEnd.toString(), username, date.toString()))) {

                    reservation = new Reservation(reservationStart, reservationEnd, username, restaurant, table, date);

                    if (Parser.isReservationConfirmed(reservation.getId())) {
                        info.setText("You have already confirmed your reservation.." + System.lineSeparator() + "it is INEVITABLE!");
                    } else {
                        info.setText("Your reservation have been canceled successfully");
                        Saver.removeReservation(reservation);
                    }
                } else {
                    info.setText("The reservation you are trying to cancel" + System.lineSeparator() + "never existed in the first place.." + System.lineSeparator() + System.lineSeparator() +
                            "Maybe you could try making it first?");
                }


            } else {
                prompt.setText("Please first chose the details" + System.lineSeparator() + "of the reservation you want to cancel.." +
                        System.lineSeparator() + "or should i guess?");
            }
        }

    }


    @FXML
    public void generateSchema(ActionEvent e) {
        if (e.getSource().equals(availableTables)) {
            String schema = restaurant.getTableSchema();
            int j = schema.length();
            if (schema.length() > 0) {
                int i = 0;

                while (i < j && i < buttons.length) {

                    if (schema.charAt(i) == '0') {

                        buttons[i].setStyle("-fx-background-color: #3eb516;");
                    } else if (schema.charAt(i) == '1') {

                        buttons[i].setStyle("-fx-background-color: #c90b04;");
                        buttons[i].setDisable(true);
                    }
                    i++;

                }
            }


            while (j < buttons.length) {
                buttons[j].setStyle("-fx-background-color: #c90b04;");
                buttons[j].setDisable(true);
                j++;
            }
        }


    }

    public void exit(ActionEvent e) throws IOException {
        Stage stage;
        Rectangle2D visualBounds;
        visualBounds = Screen.getPrimary().getVisualBounds();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Homepage.fxml"));
        Parent root = loader.load();
        ((HomeController) loader.getController()).showName(username);

        stage = (Stage) home.getScene().getWindow();

        Scene scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());
        stage.setWidth(700);
        stage.setHeight(500);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    private void setReservationList(Boolean success, Reservation reservations) {
        Platform.runLater(() -> {
            if (success) {
                reservationList.add(reservations);
            }
        });
    }

    public void setPrompt(String text) {
        prompt.setText("table n°" + text + " is reserved, please confirm reservation");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttons = new Button[]{t0, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15};
        for (Button button : buttons) {
            button = new Button();
            String text = button.getText();
            button.setOnAction(e -> setPrompt(text));
        }


        SpinnerValueFactory<Integer> valueFactory = new
                SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20);
        valueFactory.setValue(1);
        guests.setValueFactory(valueFactory);

        start.getItems().addAll(times);
        end.getItems().addAll(times);

    }


}
