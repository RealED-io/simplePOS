package tesda.tcsdi.simplepos;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        ViewSwitcher.switchToLoginUI(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}

// TODO: CODE CLEANUP TO ALL CLASSES