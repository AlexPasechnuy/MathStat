package Lab3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.DecimalFormat;


public class Controller{
    ObservableList<Double> elems1 = FXCollections.observableArrayList();
    ObservableList<Double> elems2 = FXCollections.observableArrayList();
    Solver slv;

    @FXML Button btn;
    @FXML private ListView sample1ListView, sample2ListView;
    @FXML private Button delBtn1, delBtn2, addBtn1, addBtn2, solveBtn;
    @FXML private TextField addElem, alpha, var1, var2, f, k1, k2, fCrit, verdict;

    @FXML private void addElem1Click(javafx.event.ActionEvent event){
        double x = 0;
        try {
            if (!addElem.getText().isEmpty()) {
                x = Double.parseDouble(addElem.getText());
            }
            elems1.add(x);
            sample1ListView.setItems(elems1);
        } catch (NumberFormatException ex) {
            showError("X or Y are not numbers!");
        }finally {
            addElem.clear();
        }
    }

    @FXML private void addElem2Click(javafx.event.ActionEvent event){
        double x = 0;
        try {
            if (!addElem.getText().isEmpty()) {
                x = Double.parseDouble(addElem.getText());
            }
            elems2.add(x);
            sample2ListView.setItems(elems2);
        } catch (NumberFormatException ex) {
            showError("X or Y are not numbers!");
        }finally{
            addElem.clear();
        }
    }

    @FXML private void delElem1Click(javafx.event.ActionEvent event){
        try {
            int pos = sample1ListView.getSelectionModel().getSelectedIndex();
            if (pos == -1) {
                throw new NullPointerException();
            }
            elems1.remove(pos);
            sample1ListView.setItems(elems1);
        } catch (NullPointerException ex) {
            showError("Please, choose point to delete!");
        }
    }

    @FXML private void delElem2Click(javafx.event.ActionEvent event){
        try {
            int pos = sample2ListView.getSelectionModel().getSelectedIndex();
            if (pos == -1) {
                throw new NullPointerException();
            }
            elems2.remove(pos);
            sample2ListView.setItems(elems2);
        } catch (NullPointerException ex) {
            showError("Please, choose point to delete!");
        }
    }

    @FXML private void solveClick(javafx.event.ActionEvent event){

            if (elems1.isEmpty() || elems2.isEmpty()) {
                showError("There is nothing to solve!!!");
                return;
            }
            DecimalFormat df = new DecimalFormat("###.#####");
            slv = new Solver(elems1, elems2, Double.parseDouble(alpha.getText()));
            slv.solve();
            var1.setText(String.valueOf(df.format(slv.var1)));
            var2.setText(String.valueOf(df.format(slv.var2)));
            f.setText(String.valueOf(df.format(slv.f)));
            k1.setText(String.valueOf(df.format(slv.k1)));
            k2.setText(String.valueOf(df.format(slv.k2)));
            fCrit.setText(String.valueOf(df.format(slv.fcrit)));
            if(slv.verdict == true) {
                verdict.setText("Difference is significant");
            }else{
                verdict.setText("Difference is not significant");
            }
    }

    @FXML private void toLab1(javafx.event.ActionEvent event){
        changeScene("../Lab1/Solver.fxml", "Lab 1");
    }

    @FXML private void toLab2(javafx.event.ActionEvent event){
        changeScene("../Lab2/Solver.fxml", "Lab 2");
    }

    @FXML private void toLab4(javafx.event.ActionEvent event) { changeScene("../Lab4/Solver.fxml", "Lab 4"); }

    private void changeScene(String path, String title){
        try {
            Stage stage = (Stage) solveBtn.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Parent root1 = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setMaximized(true);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
