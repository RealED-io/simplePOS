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
        borderPane.setCenter(ViewUtil.getSummaryViewFxml());

    }

    @FXML
    void logout(ActionEvent event) {
        ViewUtil.switchToLoginUI(ViewUtil.getStage(event));
    }


    @FXML
    void modifyProductDetailsView(ActionEvent event) {
        borderPane.setCenter(ViewUtil.getProductEditFxml());
    }

    @FXML
    void summaryView(ActionEvent event) {
        borderPane.setCenter(ViewUtil.getSummaryViewFxml());
    }

    @FXML
    void modifyEmployeeDetailsView(ActionEvent event) {
        borderPane.setCenter(ViewUtil.getEmployeeEditFxml());
    }
}
