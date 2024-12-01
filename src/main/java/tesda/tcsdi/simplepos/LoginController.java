package tesda.tcsdi.simplepos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tesda.tcsdi.simplepos.model.Employee;
import tesda.tcsdi.simplepos.model.dal.EmployeeDB;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private ArrayList<Employee> employees = null;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginMessage;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    void onLoginAttempt(MouseEvent event) {
        loginMessage.setText("");
    }

    @FXML
    void onLoginButtonClicked(ActionEvent event) throws IOException {
        for (Employee employee : employees) {
            if (!employee.getUsername().equals(usernameField.getText())) continue;
            if (!employee.getPassword().equals(passwordField.getText())) break;
            loginMessage.setText("login successful");
            Stage stage = ViewUtil.getStage(event);
            if (employee.getRole().equals("cashier")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("cashier-view.fxml"));
                Parent root = loader.load();

                CashierController cashierController = loader.getController();
                cashierController.passEmployeeToController(employee);

                ViewUtil.getStage(event).setScene(new Scene(root));
                stage.setMinWidth(1280);
                stage.setHeight(720);
                stage.show();
            }
            else if (employee.getRole().equals("manager")) ViewUtil.switchToManagerUI(stage);

            return;
        }
        loginMessage.setText("login failed");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EmployeeDB employeeFactory = new EmployeeDB();
        employees = employeeFactory.getAll();
    }
}
