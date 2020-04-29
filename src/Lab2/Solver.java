package Lab2;

import javafx.collections.ObservableList;

import java.util.*;

public class Solver {
    private List<Double> elems = new ArrayList<>();
    private double mean, dev, var, median;
    private List<Double> mode = new ArrayList<>();
    private Map<Double, Integer> freq = new HashMap<Double, Integer>();

    Solver(ObservableList<Double> elems){ this.elems = elems;}

    public  Map<Double, Integer> getFreq(){ return freq;}

    public List<Double> getMode(){return mode;}
    public double getMean(){return mean;}
    public double getDev(){return dev;}
    public double getVar(){return var;}
    public double getMedian(){return median;}

    public void solve(){
        //frequencies
        Set<Double> unique = Collections.EMPTY_SET;
        double sum = 0;
        for(Double i : elems){
            if(freq.containsKey(i)){ freq.replace(i, freq.get(i) + 1);}
            else{freq.put(i,1);}
            sum += i;
        }
        System.out.println(sum);
        //mean
        mean = sum/elems.size();
        //mode
        int maxInp = Collections.max(freq.values());
        for (double i : freq.keySet()){
            if(freq.get(i) == maxInp){
                mode.add(i);
            }
        }
        //median
        Collections.sort(elems);
        median = elems.get(elems.size()/2);
        //variance
        sum = 0;
        for(double i : elems){
            sum += Math.pow(i-mean,2);
        }
        var = sum/(elems.size() - 1);
        //standard deviation
        dev = Math.sqrt(var);
    }
}
