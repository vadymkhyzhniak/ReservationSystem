package javafxapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.Objects;


public class JavaFXApp extends Application{

    private static final String MAIN = "/main.fxml";

    /**
     * Starts the login scene
     *
     * @param primaryStage
     * @throws IOException
     * @author Maha Marhag
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
      Parent rootNode=FXMLLoader.load(Objects.requireNonNull(getClass().getResource(MAIN)));
        Scene scene = new Scene(rootNode, visualBounds.getWidth(), visualBounds.getHeight());
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("ReservationApp");
        primaryStage.setScene(scene);

        primaryStage.setWidth(700);
        primaryStage.setHeight(500);

        primaryStage.show();
        primaryStage.centerOnScreen();
    }




}
