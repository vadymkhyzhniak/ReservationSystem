package javafxapplication.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
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

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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

    private final List<String> list= Arrays.asList("McDonald's","Lost Weekend","L'Osteria");
    private final String[] filters= {"price range","cuisine","stars","open now"};
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        engine = webView.getEngine();
        listView.getItems().addAll(list);
filterBox.getItems().addAll(filters);
        loadPage();
    }
public void search(ActionEvent e){
        if (e.getSource()==search){
            listView.getItems().clear();
            listView.getItems().addAll(searchList(searchBar.getText(),list));
        }

}
private List<String> searchList(String words,List<String> list){
        List<String> wordsList =Arrays.asList(words.trim().split(" "));
        return list.stream()
                .filter(x->wordsList.stream().allMatch(y->x.toLowerCase().contains(y.toLowerCase())))
                .collect(Collectors.toList());
}
public void filter(ActionEvent e){
        //TODO:filter restaurants



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

