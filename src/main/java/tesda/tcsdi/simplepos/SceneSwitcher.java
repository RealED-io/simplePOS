package tesda.tcsdi.simplepos;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {
    public static void switchToCashierUI(Stage stage) {
        switcher(stage, "cashier-view.fxml", "SimplePOS - Cashier", 1280, 720, true);
    }

    public static void switchToLoginUI(Stage stage) {
        switcher(stage, "login-view.fxml", "SimplePOS - Login", 400, 400, false);
    }

    private static void switcher(Stage stage, String fxml, String title, int width, int height, boolean resizable) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.setTitle(title);
            stage.setMinWidth(width);
            stage.setMinHeight(height);
            stage.centerOnScreen();
            if (!stage.isShowing()) stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
