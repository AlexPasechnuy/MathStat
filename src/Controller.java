import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.DoubleStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public class TabRow{
        private SimpleDoubleProperty x,y;
        public TabRow(double x, double y){
            this.x = new SimpleDoubleProperty(x);
            this.y = new SimpleDoubleProperty(y);
        }

        public double getX(){return x.get();}
        public double getY(){return y.get();}
        public void setX(double x){this.x.set(x);}
        public void setY(double y){this.y.set(y);}
    }

    ObservableList<TabRow> tableData;
    Solver solver;

    @FXML private TableView table;
    @FXML private TableColumn<TabRow, Double> xPts, yPts;
    @FXML private TextField xAdd, yAdd;
    @FXML private BorderPane graphPane;
    @FXML private TextField corCoef;
    @FXML private TextField deterCoef;
    @FXML private TextField regrLine;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        tableData = FXCollections.observableArrayList();
        tableData.addAll(new TabRow(30, 40),new TabRow(27, 35),new TabRow(27, 34),
                new TabRow(32, 45), new TabRow(33, 45), new TabRow(35, 48));
        tableInit();
    }

    private void tableInit(){
        table.setItems(tableData);
        xPts.setCellValueFactory(new PropertyValueFactory<>("x"));
        xPts.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        xPts.setOnEditCommit(t->updateX(t));
        yPts.setCellValueFactory(new PropertyValueFactory<>("y"));
        yPts.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        yPts.setOnEditCommit(t->updateY(t));
    }


    private void updateX(TableColumn.CellEditEvent<TabRow, Double> t) {
        tableData.get(t.getTablePosition().getRow()).setX(t.getNewValue());
    }

    private void updateY(TableColumn.CellEditEvent<TabRow, Double> t) {
        tableData .get(t.getTablePosition().getRow()).setY(t.getNewValue());
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
            tableData.add(new TabRow(x,y));
            tableInit();
        }catch(NumberFormatException ex){
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
        }catch(NullPointerException ex){
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
        double[] xs = new double[tableData.size()];
        double[] ys = new double[tableData.size()];
        for(int i = 0; i < tableData.size(); i++){
            xs[i] = tableData.get(i).getX();
            ys[i] = tableData.get(i).getY();
        }
        solver = new Solver(xs, ys);

        corCoef.setText(solver.getCorelCoef() + "");
        deterCoef.setText(solver.getDeterCoef() + "");
        regrLine.setText(solver.getRegrParams()[0] + " + " + solver.getRegrParams()[1] + " * x");
    }

    private void constructGraphs(){
//        try{
//            NumberAxis xAxis = new NumberAxis(xFrom,xTo,(xTo-xFrom)/20);
//            NumberAxis yAxis = new NumberAxis(fw.getMinY(),fw.getMaxY(),(fw.getMaxY()- fw.getMinY())/20);
//            xAxis.setLabel("x");
//            yAxis.setLabel("y");
//            LineChart<Number,Number> newChart = new LineChart<>(xAxis,yAxis);
//            newChart.setCreateSymbols(false);
//            graphPane.getChildren().clear();
//            graphPane.getChildren().add(newChart);
//            double step = (xTo-xFrom) / 100;
//            XYChart.Series<Number,Number> fSeries = new XYChart.Series<>();
//            fSeries.setName("f(x)");
//            XYChart.Series<Number,Number> gSeries = new XYChart.Series<>();
//            gSeries.setName("g(x)");
//            for(double x = xFrom;x <= xTo;x+=step) {
//                fSeries.getData().add(new XYChart.Data<>(x, f.solve(x)));
//                gSeries.getData().add(new XYChart.Data<>(x, g.solve(x)));
//            }
//            fSeries.getData().add(new XYChart.Data<>(xTo,f.solve(xTo)));
//            gSeries.getData().add(new XYChart.Data<>(xTo,g.solve(xTo)));
//            newChart.getData().addAll(fSeries, gSeries);
//            for(int i = 0; i < resPts.size(); i++){
//                XYChart.Series<Number,Number> newSeries = new XYChart.Series<>();
//                newSeries.getData().add(new XYChart.Data<>(resPts.get(i).getX(), resPts.get(i).getY()));
//                newSeries.setName("Intersection" + (i+1));
//                newChart.getData().add(newSeries);
//            }
//            graphPane.getChildren().clear();
//            graphPane.setCenter(newChart);
//        }catch (Exception ex){
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText("Error");
//            alert.showAndWait();
//        }
    }
}
