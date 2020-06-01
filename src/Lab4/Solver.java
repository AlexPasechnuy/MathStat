package Lab4;

import org.apache.commons.math3.distribution.FDistribution;
import org.apache.commons.math3.util.MathUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solver {
    public List<List<Double>> samples;
    public List<Double> means = new ArrayList<>();
    public List<Double> grpSums = new ArrayList<>();
    public double n, m, sst, sswg, vbg, vwg, mean, ssbg, f, fcrit, alpha;
    public boolean verdict;

    public Solver(List<List<Double>> samples, double alpha) {
        this.samples = samples;
        this.alpha = alpha;
    }

    public void solve() {
        m = samples.size();
        n = 0;
        //calculating means
        double sum = 0;
        for (int i = 0; i < m; i++) {
            double tempSum = 0;
            for (int j = 0; j < samples.get(i).size(); j++) {
                tempSum += samples.get(i).get(j);
                n++;
                sum += samples.get(i).get(j);
            }
            double tempMean = tempSum / samples.get(i).size();
            means.add(tempMean);
        }
        mean = sum / n;
        //calculating commmon squares sum and sum of squares inside groups
        sst = 0;
        sswg = 0;
        for (int i = 0; i < m; i++) {
            double tempSum = 0;
            for (int j = 0; j < samples.get(i).size(); j++) {
                tempSum += Math.pow(samples.get(i).get(j) - means.get(i), 2);
                sst += Math.pow(samples.get(i).get(j) - mean, 2);
            }
            grpSums.add(tempSum);
            sswg += tempSum;
        }
        //calculating intragroup sum of squares
        ssbg = 0;
        for (int i = 0; i < m; i++) {
            ssbg += samples.get(i).size() * Math.pow(means.get(i) - mean, 2);
        }
        //calculating f and fcrit
        vbg = m - 1;
        vwg = n - m;
        double msbg = ssbg / vbg;
        double mswg = sswg / vwg;
        f = msbg / mswg;
        FDistribution fCrit = new FDistribution(vbg, vwg);
        fcrit = fCrit.inverseCumulativeProbability(1 - alpha);
        verdict = f > fcrit;
    }
}
