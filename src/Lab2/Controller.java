package Lab2;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller{
    ObservableList<Double> elems = FXCollections.observableArrayList();
    Solver slv;

    @FXML private ListView elemsListView;

    @FXML private TextField addElem;

    @FXML private BorderPane graphPane;

    @FXML private TextField min, max, range, mode, median, mean, variance, dev;

    @FXML private void addElemClick(javafx.event.ActionEvent event){
        double x = 0;
        try {
            if (!addElem.getText().isEmpty()) {
                x = Double.parseDouble(addElem.getText());
            }
            elems.add(x);
            elemsListView.setItems(elems);
        } catch (NumberFormatException ex) {
            showError("X or Y are not numbers!");
        }
    }

    @FXML private void delElemClick(javafx.event.ActionEvent event){
        try {
            int pos = elemsListView.getSelectionModel().getSelectedIndex();
            if (pos == -1) {
                throw new NullPointerException();
            }
            elems.remove(pos);
            elemsListView.setItems(elems);
        } catch (NullPointerException ex) {
            showError("Please, choose point to delete!");
        }
    }

    @FXML private void solveClick(javafx.event.ActionEvent event){
        if (elems.isEmpty()) {
            showError("There is nothing to solve!!!");
            return;
        }
        slv = new Solver(elems);
        slv.solve();
        min.setText(Collections.min(slv.getFreq().keySet()).toString());
        max.setText(Collections.max(slv.getFreq().keySet()).toString());
        range.setText(String.valueOf(Collections.max(slv.getFreq().keySet()) - Collections.min(slv.getFreq().keySet())));
        mode.setText(slv.getMode().toString());
        median.setText(String.valueOf(slv.getMedian()));
        mean.setText(String.valueOf(slv.getMean()));
        variance.setText(String.valueOf(slv.getVar()));
        dev.setText(String.valueOf(slv.getDev()));
        constructPolygon();
    }

    private void constructPolygon() {
        Map<Double, Integer> freqs = slv.getFreq();
        double minX = Collections.min(freqs.keySet());
        double maxX = Collections.max(freqs.keySet());
        double minY = Collections.min(freqs.values());
        double maxY = Collections.max(freqs.values());
        double xRange = maxX - minX;
        double yRange = maxY - minY;
        try {
            NumberAxis xAxis = new NumberAxis(minX - xRange / 10, maxX + xRange / 10, 1);
            NumberAxis yAxis = new NumberAxis(minY - yRange / 10, maxY + yRange / 10, 1);
            xAxis.setLabel("x");
            yAxis.setLabel("y");
            LineChart<Number, Number> newChart = new LineChart<>(xAxis, yAxis);
            newChart.setCreateSymbols(true);
            graphPane.getChildren().clear();
            graphPane.getChildren().add(newChart);
            XYChart.Series<Number, Number> polygon = new XYChart.Series<>();
            polygon.setName("Frequency polygon");
            for (double i : freqs.keySet()) {
                polygon.getData().add(new XYChart.Data<>(i, freqs.get(i)));
            }
            newChart.getData().add(polygon);
            graphPane.getChildren().clear();
            graphPane.setCenter(newChart);
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.showAndWait();
        }
    }

    @FXML private void toLab1(javafx.event.ActionEvent event){
        changeScene("../Lab1/Solver.fxml", "Lab 1");
    }

    @FXML private void toLab3(javafx.event.ActionEvent event){
        changeScene("../Lab3/Solver.fxml", "Lab 3");
    }

    @FXML private void toLab4(javafx.event.ActionEvent event){
        changeScene("../Lab4/Solver.fxml", "Lab 4");
    }

    @FXML private void toLab5(javafx.event.ActionEvent event){
        changeScene("../Lab5/Solver.fxml", "Lab 5");
    }

    private void changeScene(String path, String title){
        try {
            Stage stage = (Stage) graphPane.getScene().getWindow();
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
