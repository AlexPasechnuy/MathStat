package Lab1;

import javafx.fxml.FXML;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Solver {
    private double[] xi = new double[0];
    private double[] yi = new double[0];
    private double xm, ym;
    private double[] x = new double[0];
    private double[] y = new double[0];
    private double xSqSum;
    private double ySqSum;
    private double xySum;
    private double sx, sy;
    private double r;
    private double squaredR;
    private double[] b = new double[2];

    public double[] getRegrParams(){
        return b;
    }

    public double getCorelCoef(){
        return r;
    }

    public double getDeterCoef(){
        return squaredR;
    }

    public void add(double x, double y) {
        Arrays.copyOf(this.xi, this.xi.length + 1);
        Arrays.copyOf(this.yi, this.yi.length + 1);
        this.xi[this.xi.length - 1] = x;
        this.yi[this.yi.length - 1] = y;
        solve();
    }

    public Solver(double[] xi, double[] yi) {
        this.xi = xi;
        this.yi = yi;
        solve();
    }

    private void solve() {
        xyInit();
        correlCoefInit();
        regrParamInit();
        deterCoefInit();
    }

    private void xyInit(){
        double xsum = 0;
        double ysum = 0;
        for (int i = 0; i < xi.length; i++) {
            xsum += xi[i];
            ysum += yi[i];
        }
        xm = xsum / xi.length;
        ym = ysum / yi.length;
        x = new double[xi.length];
        y = new double[yi.length];
        for (int i = 0; i < xi.length; i++) {
            x[i] = xi[i] - xm;
            y[i] = yi[i] - ym;
            xySum += x[i] * y[i];
            xSqSum += Math.pow(x[i], 2);
            ySqSum += Math.pow(y[i], 2);
        }
    }

    private void correlCoefInit() {
        r = (xySum)/(Math.sqrt(xSqSum * ySqSum));
    }

    private void regrParamInit(){
        sx = Math.sqrt(xSqSum/(xi.length-1));
        sy = Math.sqrt(ySqSum/(yi.length-1));
        b[1] = r * (sy/sx);
        b[0] = ym - b[1] * xm;
    }

    private void deterCoefInit(){
    squaredR = Math.pow((xySum)/(xi.length * sx * sy),2);
    }
}
