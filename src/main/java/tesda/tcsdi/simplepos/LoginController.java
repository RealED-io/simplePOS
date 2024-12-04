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
import tesda.tcsdi.simplepos.model.dal.EmployeeFactory;

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
            Stage stage = ViewSwitcher.getStage(event);
            if (employee.getRole().equals("cashier")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("cashier-view.fxml"));
                Parent root = loader.load();

                CashierController cashierController = loader.getController();
                cashierController.passEmployeeToController(employee);

                ViewSwitcher.getStage(event).setScene(new Scene(root));
                stage.setWidth(1280);
                stage.setHeight(720);
                stage.centerOnScreen();
                stage.setTitle("SimplePOS - Cashier : " + employee.getName());
                stage.show();
            }
            else if (employee.getRole().equals("manager")) ViewSwitcher.switchToManagerUI(stage);

            return;
        }
        loginMessage.setText("login failed");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EmployeeFactory employeeFactory = new EmployeeFactory();
        employees = employeeFactory.getAll();
    }
}
