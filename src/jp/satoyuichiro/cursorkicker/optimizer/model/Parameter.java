package jp.satoyuichiro.cursorkicker.optimizer.model;

import jp.satoyuichiro.cursorkicker.kicker.Booster;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Properties;

/**
 * Created by Yuichiro on 2016/02/01.
 */
public class Parameter {

    private double a;
    private double b;
    private double c;
    private double threshold;

    public Parameter(double a, double b, double c, double threshold) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.threshold = threshold;
    }

    public Parameter add(Parameter p) {
        return new Parameter(a + p.getA(), b + p.getB(), c + p.getC(), threshold + p.getThreshold());
    }

    public double boost(double x) {
        if (x < threshold) {
            return x;
        } else {
            return a * x * x + b * x + c;
        }
    }

    public static Parameter unit() {
        return new Parameter(0.0, 1.0, 0.0, 10.0);
    }

    public static Parameter randomParameter() {
        double newA = 0.01 - Math.random() * 0.02;
        double newB = 0.01 - Math.random() * 0.02;
        double newC = 0.01 - Math.random() * 0.02;
        double newThreshold = 0.01 - Math.random() * 0.02;
        return new Parameter(newA, newB, newC, newThreshold);
    }
    public boolean isNoise(double x) {
        return x < threshold;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) { this.threshold = threshold; }

    public double norm() {
        return a * a + b * b + c * c + threshold * threshold;
    }

    public Booster toBooster() {
        return new Booster(a, b, c, threshold);
    }

    public void toProperty() {
        Properties p = new Properties();
        p.setProperty("a", String.valueOf(a));
        p.setProperty("b", String.valueOf(b));
        p.setProperty("c", String.valueOf(c));
        p.setProperty("threshold", String.valueOf(threshold));
        try (Writer wt = new PrintWriter(new File("parameter.properties"))){
            p.store(wt, "parameter");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}