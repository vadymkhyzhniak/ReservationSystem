package javafxapplication;

import javafx.application.Application;
import javafx.stage.Stage;

public class JavaFXApp extends Application {
    private Stage stage;

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        primaryStage.show();
    }

    public Stage getStage() {
        return stage;
    }
}
