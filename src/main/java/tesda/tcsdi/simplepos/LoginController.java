package tesda.tcsdi.simplepos;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tesda.tcsdi.simplepos.model.Employee;
import tesda.tcsdi.simplepos.model.dal.EmployeeDB;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
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
    void onLoginButtonClicked(MouseEvent event) throws IOException {
        for (Employee employee : employees) {
            if (!employee.getUsername().equals(usernameField.getText())) continue;
            if (!employee.getPassword().equals(passwordField.getText())) break;
            loginMessage.setText("login successful");

            Parent root = FXMLLoader.load(getClass().getResource("cashier-view.fxml"));
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setTitle("wow");
            stage.setScene(new Scene(root));
//            stage.setScene(FXMLLoader.load(getClass().getResource("cashier-view.fxml")));
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
