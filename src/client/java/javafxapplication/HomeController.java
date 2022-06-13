package javafxapplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


import java.net.URL;
import java.util.ResourceBundle;


public class HomeController implements Initializable {
    @FXML
    private WebView webView;
    @FXML
    private WebEngine engine;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        engine= webView.getEngine();
        loadPage();
    }
    public void loadPage(){
        engine.load("https://www.google.com/maps/@48.2011967,11.6145736,15z");
    }
}

