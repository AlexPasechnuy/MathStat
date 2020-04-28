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
import java.util.ResourceBundle;

public class Controller{
    ObservableList<Double> elems = FXCollections.observableArrayList();

    @FXML private ListView elemsListView;

    @FXML private TextField addElem;

    @FXML private BorderPane graphPane;

    @FXML private void addElemClick(javafx.event.ActionEvent event){
        double x = 0;
        try {
            if (!addElem.getText().isEmpty()) {
                x = Double.parseDouble(addElem.getText());
            }
            elems.add(x);
            System.out.println(elems);
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
        Solver slv = new Solver(elems);
        slv.solve();
    }

    private void constructPolygon() {
//        getGraphBounds();
//        double xRange = maxX - minX;
//        double yRange = maxY - minY;
//        try {
//            NumberAxis xAxis = new NumberAxis(minX - xRange / 10, maxX + xRange / 10, xRange / 20);
//            NumberAxis yAxis = new NumberAxis(minY - yRange / 10, maxY + yRange / 10, yRange / 20);
//            xAxis.setLabel("x");
//            yAxis.setLabel("y");
//            LineChart<Number, Number> newChart = new LineChart<>(xAxis, yAxis);
//            newChart.setCreateSymbols(true);
//            graphPane.getChildren().clear();
//            graphPane.getChildren().add(newChart);
//            XYChart.Series<Number, Number> scPlot = new XYChart.Series<>();
//            scPlot.setName("scatterplot");
//            XYChart.Series<Number, Number> regrLine = new XYChart.Series<>();
//            regrLine.setName("regression line");
//            for (Lab1.Controller.TabRow row : tableData) {
//                scPlot.getData().add(new XYChart.Data<>(row.getX(), row.getY()));
//            }
//            for (double x = minX; x <= maxX; x += xRange / 50) {
//                regrLine.getData().add(new XYChart.Data<>(x, solver.getRegrParams()[0] + solver.getRegrParams()[1] * x));
//            }
//            newChart.getData().addAll(scPlot, regrLine);
//            graphPane.getChildren().clear();
//            graphPane.setCenter(newChart);
//        } catch (Exception ex) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText("Error");
//            alert.showAndWait();
//        }
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
