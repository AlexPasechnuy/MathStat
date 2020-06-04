import Lab1.*;

import java.text.DecimalFormat;

public class Test {
    public static void main(String[] args) {
    double kek = 10000*100000;
        DecimalFormat df = new DecimalFormat("###.###");
        System.out.println(kek);
    System.out.println(df.format(kek));
    }
}
