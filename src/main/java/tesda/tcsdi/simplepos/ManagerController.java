package tesda.tcsdi.simplepos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;


public class ManagerController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        borderPane.setCenter(ViewUtil.getProductEditFxml());

    }

    @FXML
    void logout(ActionEvent event) {
        ViewUtil.switchToLoginUI(ViewUtil.getStage(event));
    }


    @FXML
    void modifyProductDetailsView(ActionEvent event) {

    }

    @FXML
    void summaryView(ActionEvent event) {

    }
}
