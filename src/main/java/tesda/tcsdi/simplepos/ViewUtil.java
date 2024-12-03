package tesda.tcsdi.simplepos;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

public class ViewUtil {
    public static void switchToCashierUI(Stage stage) {
        switcher(stage, "cashier-view.fxml", "SimplePOS - Cashier", 1280, 720, true);
    }

    public static void switchToLoginUI(Stage stage) {
        switcher(stage, "login-view.fxml", "SimplePOS - Login", 420, 420, false);
    }

    public static void switchToManagerUI(Stage stage) {
        switcher(stage, "manager-view.fxml", "SimplePOS - Manager", 1280, 720, true);
    }

    public static Pane getProductEditFxml() {
        return getFxml("product-edit-view.fxml");
    }

    public static Pane getEmployeeEditFxml() {
        return getFxml("employee-edit-view.fxml");
    }

    public static Pane getCategoryEditViewFxml() {
        return getFxml("category-edit-view.fxml");
    }

    public static Pane getSummaryViewFxml() {
        return getFxml("summary-view.fxml");
    }

    public static Stage getStage(Event event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    private static Pane getFxml(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void switcher(Stage stage, String fxml, String title, int width, int height, boolean resizable) {
        stage.setScene(new Scene(getFxml(fxml)));
        stage.setTitle(title);
        stage.setWidth(width);
        stage.setMinWidth(width);
        stage.setHeight(height);
        stage.setMinHeight(height);
        stage.centerOnScreen();
        stage.setResizable(resizable);
        if (!stage.isShowing()) stage.show();
    }

}
