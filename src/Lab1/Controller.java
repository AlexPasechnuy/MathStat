package Lab1;

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

public class Controller implements Initializable {
    public class TabRow {
        private SimpleDoubleProperty x, y;

        public TabRow(double x, double y) {
            this.x = new SimpleDoubleProperty(x);
            this.y = new SimpleDoubleProperty(y);
        }

        public double getX() {
            return x.get();
        }

        public double getY() {
            return y.get();
        }

        public void setX(double x) {
            this.x.set(x);
        }

        public void setY(double y) {
            this.y.set(y);
        }
    }

    ObservableList<TabRow> tableData;
    double minX, maxX, minY, maxY;
    Solver solver;

    @FXML
    private TableView table;
    @FXML
    private TableColumn<TabRow, Double> xPts, yPts;
    @FXML
    private TextField xAdd, yAdd;
    @FXML
    private BorderPane graphPane;
    @FXML
    private TextField corCoef;
    @FXML
    private TextField deterCoef;
    @FXML
    private TextField regrLine;
    @FXML
    private Menu changeLab;
    @FXML
    private MenuItem lab2, lab3, lab4, lab5;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableData = FXCollections.observableArrayList();
        tableData.addAll(new TabRow(30, 40), new TabRow(27, 35), new TabRow(27, 34),
                new TabRow(32, 45), new TabRow(33, 45), new TabRow(35, 48));
        tableInit();
    }

    private void tableInit() {
        table.setItems(tableData);
        xPts.setCellValueFactory(new PropertyValueFactory<>("x"));
        xPts.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        xPts.setOnEditCommit(t -> updateX(t));
        yPts.setCellValueFactory(new PropertyValueFactory<>("y"));
        yPts.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        yPts.setOnEditCommit(t -> updateY(t));
    }


    private void updateX(TableColumn.CellEditEvent<TabRow, Double> t) {
        tableData.get(t.getTablePosition().getRow()).setX(t.getNewValue());
    }

    private void updateY(TableColumn.CellEditEvent<TabRow, Double> t) {
        tableData.get(t.getTablePosition().getRow()).setY(t.getNewValue());
    }

    @FXML
    private void addPtClick(javafx.event.ActionEvent event) {
        double x = 0;
        double y = 0;
        try {
            if (!xAdd.getText().isEmpty()) {
                x = Double.parseDouble(xAdd.getText());
            }
            if (!yAdd.getText().isEmpty()) {
                y = Double.parseDouble(yAdd.getText());
            }
            tableData.add(new TabRow(x, y));
            tableInit();
        } catch (NumberFormatException ex) {
            showError("X or Y are not numbers!");
        }
    }

    @FXML
    private void delPtClick(javafx.event.ActionEvent event) {
        try {
            int pos = table.getSelectionModel().getSelectedIndex();
            if (pos == -1) {
                throw new NullPointerException();
            }
            tableData.remove(pos);
            tableInit();
        } catch (NullPointerException ex) {
            showError("Please, choose point to delete!");
        }
    }

    private static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    @FXML
    private void solveClick(javafx.event.ActionEvent event) {
        if (tableData.isEmpty()) {
            showError("There is nothing to solve!!!");
            return;
        }
        double[] xs = new double[tableData.size()];
        double[] ys = new double[tableData.size()];
        for (int i = 0; i < tableData.size(); i++) {
            xs[i] = tableData.get(i).getX();
            ys[i] = tableData.get(i).getY();
        }
        solver = new Solver(xs, ys);

        DecimalFormat df = new DecimalFormat("#.###");
        corCoef.setText(df.format(solver.getCorelCoef()) + "");
        deterCoef.setText(df.format(solver.getDeterCoef()) + "");
        regrLine.setText(df.format(solver.getRegrParams()[0]) + " + "
                + df.format(solver.getRegrParams()[1]) + " * x");
        constructGraphs();
    }


    private void getGraphBounds() {
        minY = maxY = tableData.get(0).getY();
        minX = maxX = tableData.get(0).getX();
        for (int i = 1; i < tableData.size(); i++) {
            double x = tableData.get(i).getX();
            double y = tableData.get(i).getY();
            if (x < minX) {
                minX = x;
            } else if (x > maxX) {
                maxX = x;
            }
            if (y < minY) {
                minY = y;
            } else if (y > maxY) {
                maxY = y;
            }
        }
    }

    private void constructGraphs() {
        getGraphBounds();
        double xRange = maxX - minX;
        double yRange = maxY - minY;
        try {
            NumberAxis xAxis = new NumberAxis(minX - xRange / 10, maxX + xRange / 10, xRange / 20);
            NumberAxis yAxis = new NumberAxis(minY - yRange / 10, maxY + yRange / 10, yRange / 20);
            xAxis.setLabel("x");
            yAxis.setLabel("y");
            LineChart<Number, Number> newChart = new LineChart<>(xAxis, yAxis);
            newChart.setCreateSymbols(true);
            graphPane.getChildren().clear();
            graphPane.getChildren().add(newChart);
            XYChart.Series<Number, Number> scPlot = new XYChart.Series<>();
            scPlot.setName("scatterplot");
            XYChart.Series<Number, Number> regrLine = new XYChart.Series<>();
            regrLine.setName("regression line");
            for (TabRow row : tableData) {
                scPlot.getData().add(new XYChart.Data<>(row.getX(), row.getY()));
            }
            for (double x = minX; x <= maxX; x += xRange / 50) {
                regrLine.getData().add(new XYChart.Data<>(x, solver.getRegrParams()[0] + solver.getRegrParams()[1] * x));
            }
            newChart.getData().addAll(scPlot, regrLine);
            graphPane.getChildren().clear();
            graphPane.setCenter(newChart);
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.showAndWait();
        }
    }

    @FXML private void toLab2(javafx.event.ActionEvent event){
        changeScene("../Lab2/Solver.fxml", "Lab 2");
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

            //Close current
            Stage stage = (Stage) graphPane.getScene().getWindow();
            // do what you have to do
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Parent root1 = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
