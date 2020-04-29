package Lab2;

import javafx.collections.ObservableList;

import java.util.*;

public class Solver {
    private List<Double> elems = new ArrayList<>();
    private Map<Double, Integer> freq = new HashMap<Double, Integer>();

    Solver(ObservableList<Double> elems){ this.elems = elems;}

    public  Map<Double, Integer> getFreq(){ return freq;}
    

    public void solve(){
        //frequencies
        Set<Double> unique = Collections.EMPTY_SET;
        for(Double i : elems){
            if(freq.containsKey(i)){ freq.replace(i, freq.get(i) + 1);}
            else{freq.put(i,1);}
        }
        //median

        //mode

        //dispersion

        //standard deviation

    }
}
