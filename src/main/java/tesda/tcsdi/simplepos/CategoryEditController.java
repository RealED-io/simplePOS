package tesda.tcsdi.simplepos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import tesda.tcsdi.simplepos.model.Category;
import tesda.tcsdi.simplepos.model.dal.CategoryFactory;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CategoryEditController implements Initializable {

    @FXML
    private TableView<Category> categoryTable;

    @FXML
    private TableColumn<Category, Integer> idCol;

    @FXML
    private TableColumn<Category, String> nameCol;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField searchField;

    private Category selectedCategory;
    private final CategoryFactory categoryFactory = new CategoryFactory();
    private ObservableList<Category> categoryList;
    private FilteredList<Category> filteredData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categoryTableInit();
        nameField.setOnKeyTyped(event -> isFieldValid(nameField));
    }

    private boolean isFieldValid(TextField textField) {
        boolean isNotBlank = true;
        String name = textField.getText();
        if(name == null || name.isBlank()) {
            isNotBlank = false;
        }
        String warningCSS = "-fx-border-color: red; -fx-background-color: #ffe6e6;";
        if(isNotBlank) textField.setStyle("");
        else textField.setStyle(warningCSS);
        return isNotBlank;
    }

    private void categoryTableInit() {
        categoryList = FXCollections.observableArrayList(categoryFactory.getAll());

        idCol.setCellValueFactory(new PropertyValueFactory<Category, Integer>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Category, String>("name"));

        filteredData = new FilteredList<>(categoryList, b -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(category -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if (category.getName().toLowerCase().contains(lowerCaseFilter)) return true;
                else return false;
            });
        });
        SortedList<Category> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(categoryTable.comparatorProperty());
        categoryTable.setItems(sortedData);
    }

    @FXML
    void clearTextFields(ActionEvent event) {
        idField.clear();
        nameField.clear();
    }

    @FXML
    void deleteCategory(ActionEvent event) {
        if(!isFieldValid(nameField)) return;
        Category category = textFieldToCategory();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete category confirmation");
        alert.setContentText("Are you sure you want to delete \"" + category.getName() + "\"?");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
            categoryFactory.delete(category);
            categoryTableInit();
        }
    }

    private Category textFieldToCategory() {
        Category category = new Category();
        int id = idField.getText() == null || idField.getText().isBlank() ? 0 : Integer.parseInt(idField.getText());
        String name = nameField.getText();
        category.setId(id);
        category.setName(name);
        return category;
    }

    @FXML
    void newCategory(ActionEvent event) {
        if(!isFieldValid(nameField)) return;
        Category category = textFieldToCategory();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("New category confirmation");
        alert.setContentText("Are you sure you want to add \"" + category.getName() + "\" as new category?");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
            categoryFactory.create(category);
            categoryTableInit();
        }
    }

    @FXML
    void selectCategoryFromTable(MouseEvent event) {
        selectedCategory = categoryTable.getSelectionModel().getSelectedItem();
        if(selectedCategory == null) return;
        idField.setText(String.valueOf(selectedCategory.getId()));
        nameField.setText(selectedCategory.getName());
    }

    @FXML
    void updateCategory(ActionEvent event) {
        if(!isFieldValid(nameField)) return;
        Category category = textFieldToCategory();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update category confirmation");
        alert.setContentText("Are you sure you want to update \"" + category.getName() + "\"?");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
            categoryFactory.update(category);
            categoryTableInit();
        }
    }
}
