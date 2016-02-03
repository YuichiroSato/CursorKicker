package jp.satoyuichiro.cursorkicker.kicker;

import java.awt.AWTException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Created by Yuichiro on 2015/12/21.
 */

public class Kicker {

    public static void main(String[] args) {
        try {
            (new KickerThread(loadKickerParameter())).start();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static Booster loadKickerParameter() {
        double a;
        double b;
        double c;
        double threshold;

        Properties properties = new Properties();
        try {
            properties.load(Files.newInputStream(Paths.get("parameter.properties")));
            a = Double.valueOf(properties.getProperty("a"));
            b = Double.valueOf(properties.getProperty("b"));
            c = Double.valueOf(properties.getProperty("c"));
            threshold = Double.valueOf(properties.getProperty("threshold"));
        } catch (IOException | NullPointerException | NumberFormatException e) {
            // default value
            a = 0.4;
            b = 0.1;
            c = 0.0;
            threshold = 10.0;
        }

        return new Booster(a, b, c, threshold);
    }
}
