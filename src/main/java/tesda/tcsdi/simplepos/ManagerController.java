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
        borderPane.setCenter(ViewSwitcher.getSummaryViewFxml());

    }

    @FXML
    void logout(ActionEvent event) {
        ViewSwitcher.switchToLoginUI(ViewSwitcher.getStage(event));
    }

    @FXML
    void modifyProductDetailsView(ActionEvent event) {
        borderPane.setCenter(ViewSwitcher.getProductEditFxml());
    }

    @FXML
    void summaryView(ActionEvent event) {
        borderPane.setCenter(ViewSwitcher.getSummaryViewFxml());
    }

    @FXML
    void modifyEmployeeDetailsView(ActionEvent event) {
        borderPane.setCenter(ViewSwitcher.getEmployeeEditFxml());
    }

    @FXML
    void modifyCategoryDetailsView(ActionEvent event) {
        borderPane.setCenter(ViewSwitcher.getCategoryEditViewFxml());
    }

    @FXML
    void modifySupplierDetailsView(ActionEvent event) {
        borderPane.setCenter(ViewSwitcher.getSupplierEditViewFxml());
    }
}
