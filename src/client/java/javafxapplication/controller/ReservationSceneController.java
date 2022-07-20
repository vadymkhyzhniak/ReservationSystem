package javafxapplication.controller;

import commonapplication.models.Reservation;
import commonapplication.models.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ReservationSceneController  {

private ReservationController controller;
    private final ObservableList<Reservation> reservationList;
    private LocalDate date;
    private boolean datePicked=false;
@FXML
private DatePicker calender;
@FXML
private Button chooseDate;
@FXML
private Button makeReservation;
@FXML
private Button home;

    public ReservationSceneController() {
        this.controller =new ReservationController();
        this.reservationList= FXCollections.observableArrayList();

    }
public void chooseDate(ActionEvent e ){
  date= calender.getValue();
  datePicked= true;

}
public void makeReservation(ActionEvent e ){
    if (datePicked){

//controller.addReservation();
    }

}
public void exit(ActionEvent e){

}

    private void setReservationList(List<Reservation> reservations) {
        Platform.runLater(()-> {
            reservationList.setAll(reservations);

        });
    }
}
