package tesda.tcsdi.simplepos;
import tesda.tcsdi.simplepos.model.Cart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("cashier-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setMinWidth(1280);
        stage.setMinHeight(720);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}