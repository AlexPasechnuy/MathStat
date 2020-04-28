import Lab1.*;

public class Test {
    public static void main(String[] args) {
        double[] xs = {30,28,27,32,33,35};
        double[] ys = {40,35,34,45,45,48};
        Solver sv = new Solver(xs, ys);
        System.out.println("Regression parameters: " + sv.getRegrParams()[0] + ", " + sv.getRegrParams()[1]);
        System.out.println("Corellation coefficient: " + sv.getCorelCoef());
        System.out.println("Determination coefficient: " + sv.getDeterCoef());
    }
}
