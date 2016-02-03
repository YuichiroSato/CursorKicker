package jp.satoyuichiro.cursorkicker.kicker;

import java.awt.Point;

/**
 * Created by Yuichiro on 2016/01/25.
 */

public class Booster {

    private double a;
    private double b;
    private double c;
    private double threshold;

    public Booster(double a, double b, double c, double threshold) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.threshold = threshold;
    }

    public boolean isNoise(Point previous, Point current) {
        if (previous == null || current == null) return true;
        return previous.distance(current) < threshold;
    }

    public Point kickedCursor(Point previous, Point current) {
        Point kicked = (Point)previous.clone();
        int dx = current.x - previous.x;
        int dy = current.y - previous.y;
        double r = ratio(previous, current);
        kicked.translate((int)(dx * r), (int)(dy * r));
        return kicked;
    }

    private double ratio(Point previous, Point current) {
        double d = previous.distance(current);
        if (d == 0) return 0;
        return boost(d) / d;
    }

    private double boost(double x) {
        return a * x * x + b * x + c;
    }
}
