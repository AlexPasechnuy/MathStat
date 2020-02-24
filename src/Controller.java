import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class Controller{
    public class PtsRow{
        private SimpleDoubleProperty x,y;
        public PtsRow(double x, double y){
            this.x = new SimpleDoubleProperty(x);
            this.y = new SimpleDoubleProperty(y);
        }

        public double getX(){return x.get();}
        public double getY(){return y.get();}
        public void setX(double x){this.x.set(x);}
        public void setY(double y){this.y.set(y);}
    }

    @FXML private TableView table;
    @FXML private TableColumn<PtsRow, Double> xPts, yPts;
    @FXML private TextField regression;
    @FXML private BorderPane graphPane;
}
