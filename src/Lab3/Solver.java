package Lab3;

import java.util.*;

import javafx.collections.ObservableList;
import org.apache.commons.math3.distribution.FDistribution;

public class Solver {
    public List<Double> sample1 = new ArrayList<>();
    public List<Double> sample2 = new ArrayList<>();
    public double var1, var2, k1, k2, f, alpha, fcrit;
    public boolean verdict;

    public Solver(List<Double> sample1, List<Double> sample2, double alpha){
        this.sample1 = sample1;
        this.sample2 = sample2;
        this.alpha = alpha;
    }

//    public Solver(ObservableList<Double> sample1, ObservableList<Double> sample2){
//        this.sample1 = sample1;
//        this.sample2 = sample2;
//    }

    public double variance(List<Double> sample){
        double sum = 0;

        for(double i : sample){
            sum += i;
        }

        double mean = sum/sample.size();
        sum = 0;
        for(double i : sample){
            sum += Math.pow(i-mean,2);
        }
        return sum/(sample.size() - 1);
    }

    public void solve(){
        var1 = variance(sample1);
        var2 = variance(sample2);
        FDistribution fCrit;
        if(var1 > var2) {
            f = var1/var2;
            k1 = sample1.size()-1;
            k2 = sample2.size()-1;

        }
        else {
            f = var2/var1;
            k1 = sample2.size()-1;
            k2 = sample1.size()-1;
        }
        fCrit = new FDistribution(k1,k2);
        fcrit = fCrit.inverseCumulativeProbability(1-alpha);
        verdict = f>fcrit;
    }
}