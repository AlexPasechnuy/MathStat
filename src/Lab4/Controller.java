package Lab4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Controller {
    ObservableList<Double> elems = FXCollections.observableArrayList();
    List<List<Double>> samplesList = new ArrayList<>();

    @FXML
    ListView sampleTable, groupMeans;
    @FXML
    TextField addElem, alpha, mean, f, fcrit, verdict,n,m,sst,sswg,ssbg, commonMean;
    @FXML
    TextArea samples;

    @FXML
    private void addElemClick(javafx.event.ActionEvent event) {
        double x = 0;
        try {
            if (!addElem.getText().isEmpty()) {
                x = Double.parseDouble(addElem.getText());
            }
            elems.add(x);
            sampleTable.setItems(elems);
        } catch (NumberFormatException ex) {
            showError("X or Y are not numbers!");
        }
    }

    @FXML
    private void delElemClick(javafx.event.ActionEvent event) {
        try {
            int pos = sampleTable.getSelectionModel().getSelectedIndex();
            if (pos == -1) {
                throw new NullPointerException();
            }
            elems.remove(pos);
            sampleTable.setItems(elems);
        } catch (NullPointerException ex) {
            showError("Please, choose point to delete!");
        }
    }

    @FXML
    private void solveClick(javafx.event.ActionEvent event) {
        if (samplesList.isEmpty() || alpha.getText().isEmpty()) {
            showError("There is nothing to solve!!!");
            return;
        }
        Solver slv = new Solver(samplesList, Double.parseDouble(alpha.getText()));
        slv.solve();
        String smpls = "";
        for(int i = 0; i < slv.samples.size(); i++){
            smpls += "Sample " + i + ": ";
            for(int j = 0; j < slv.samples.get(i).size(); j++){
                smpls += slv.samples.get(i).get(j) + " ";
            }
            smpls += "\n";
        }
        groupMeans.setItems(FXCollections.observableArrayList(slv.means));
        samples.setText(smpls);
        n.setText(String.valueOf(slv.n));
        m.setText(String.valueOf(slv.m));
        sst.setText(String.valueOf(slv.sst));
        commonMean.setText(String.valueOf(slv.mean));
        sswg.setText(String.valueOf(slv.sswg));
        ssbg.setText(String.valueOf(slv.ssbg));
        f.setText(String.valueOf(slv.f));
        fcrit.setText(String.valueOf(slv.fcrit));
        if(slv.verdict == true) {
            verdict.setText("Difference is significant");
        }else{
            verdict.setText("Difference is not significant");
        }

    }

    @FXML
    private void nextSampleClick(javafx.event.ActionEvent event) {
        if (elems.isEmpty()) {
            showError("Sample is empty!!!");
            return;
        }
        samplesList.add(new ArrayList<>(elems));
        elems.clear();
        sampleTable.setItems(elems);
    }

    private static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
