package jp.satoyuichiro.cursorkicker.optimizer.model;

import java.awt.Point;

/**
 * Created by Yuichiro on 2016/01/30.
 */
public class UserInput {

    private Point from;
    private Point to;

    private double length;

    public UserInput(Point from, Point to) {
        this.from = from;
        this.to = to;
        length = from.distance(to);
    }

    public Point getFrom() { return from; }

    public Point getTo() { return to; }

    public double getLength() { return length; }
}
