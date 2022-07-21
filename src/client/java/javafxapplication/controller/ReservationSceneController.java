package javafxapplication.controller;

import commonapplication.models.*;
import commonapplication.persistancemanagement.Parser;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class ReservationSceneController implements Initializable {

private ReservationController controller;
    private final ObservableList<Reservation> reservationList;
    private LocalDate date;
    private LocalTime reservationStart,reservationEnd;
    private boolean verif=false;
    private RestaurantController restaurantController;
@FXML
private Spinner<Integer> guests;
private int guestValue;
@FXML
private DatePicker calender;

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
    private  Button[] buttons;
private final List<String> times= new ArrayList<String>();
private Table table;
private Restaurant restaurant;
private String username;
@FXML
private Label info;



    public ReservationSceneController()  {
        this.controller =new ReservationController();
        this.restaurantController= new RestaurantController();
        this.reservationList= FXCollections.observableArrayList();
     buttons= new Button[]{t0, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15};
        for (int i = 0; i < 24; i++) {
            for(int j = 0; j < 4; j++) {
                String[] minutes = {"00", "15", "30", "45"};
                String time = i + ":" + minutes[j];
                if(i < 10) {
                    time = "0" + time;
                }
                times.add(time);
            }

        }

    }
    public void displayInfo(String name,String restaurant){
        String s="Welcome "+name+"\n Restaurant: "+restaurant;
        info.setText(s);
      restaurantController.getRestaurant(info.getText().substring(22+name.length()),this::setRestaurant);
        setUsername(info.getText().substring(7));
    }
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setUsername(String username) {
        this.username = username;
    }
public void chooseDate(ActionEvent e ){
  date= calender.getValue();
 verif= true;

}
public void pickTime(ActionEvent e){
        if (e.getSource().equals(confirmDate)){
            if (start.getValue().isBlank()||end.getValue().isBlank()){
                prompt.setText("please provide reservation start and end");
                this.verif= false;
            }
            this.reservationStart= LocalTime.parse(start.getValue());
            this.reservationEnd= LocalTime.parse(end.getValue());

    if (reservationStart.isBefore(reservationEnd)){
        this.verif=true;

    }
    else {
prompt.setText("please provide another time period");
this.verif= false;
    }
}
}
public void makeReservation(ActionEvent e){
    if (verif && e.getSource().equals(makeReservation)){
        Random rand= new Random();
this.table= restaurant.getTables()[rand.nextInt(0,restaurant.getTables().length)];
        Reservation   reservation = new Reservation(reservationStart,reservationEnd,username,restaurant,table,date);
        info.setText(info.getText() +"\nTable is booked on "+date.toString()+"\n from: "+reservationStart.toString()+" to "+reservationEnd.toString());
controller.addReservation(reservation,this::setReservationList);
    }

}
@FXML
public void generateSchema(ActionEvent e){

    String schema= restaurant.getTableSchema();


        int i=0;
        int j= restaurant.getTableSchema().length();
        while (i<= j && i< buttons.length) {

            if (schema.charAt(i)=='0'){

                buttons[i].setStyle("-fx-background-color: #3eb516;");
            }
            else if(schema.charAt(i)=='1') {

                buttons[i].setStyle("-fx-background-color: #c90b04;");
                buttons[i].setDisable(true);
            }
            i++;

        }

    while (j<buttons.length) {
        buttons[j].setStyle("-fx-background-color: #c90b04;");
        buttons[j].setDisable(true);

    }


}
public void exit(ActionEvent e) throws IOException {
        Stage stage;
    Rectangle2D visualBounds ;
    visualBounds= Screen.getPrimary().getVisualBounds();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
    Parent root = loader.load();

    stage= (Stage) home.getScene().getWindow();
    stage.setWidth(600);
    stage.setHeight(400);
    Scene scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());
    stage.setScene(scene);
    stage.centerOnScreen();
    stage.show();
}

    private void setReservationList(List<Reservation> reservations) {
        Platform.runLater(()-> {
            reservationList.setAll(reservations);


        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttons= new Button[]{t0, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15};
        for (Button button: buttons){
            button= new Button();
            Button finalButton = button;
            button.setOnAction(e->{
                finalButton.setDisable(true);
            this.table= restaurant.getTables()[Integer.parseInt(finalButton.getText())];
            } );
        }

        availableTables.setOnAction(this::generateSchema);
SpinnerValueFactory<Integer> valueFactory= new
        SpinnerValueFactory.IntegerSpinnerValueFactory(0,20);
valueFactory.setValue(0);
guests.setValueFactory(valueFactory);

start.getItems().addAll(times);
end.getItems().addAll(times);

    }
}
