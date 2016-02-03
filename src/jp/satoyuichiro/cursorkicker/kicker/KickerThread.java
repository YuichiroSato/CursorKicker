package jp.satoyuichiro.cursorkicker.kicker;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.MouseInfo;
import java.awt.Robot;

/**
 * Created by Yuichiro on 2016/01/22.
 */

public class KickerThread extends Thread {

    public static int SLEEPING_TIME = 10;
    private Robot robot;
    private Booster booster;

    // mouse location
    private Point previous;
    private Point current;

    public KickerThread(Booster booster) throws AWTException{
        this.booster = booster;
        robot = new Robot();
        previous = getLocation();
    }

    @Override
    public void run() {
        while(true) {
            current = getLocation();
            if (!booster.isNoise(previous, current)) {
                kickCursor();
            }
            previous = getLocation();
            try {
                Thread.sleep(SLEEPING_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Point getLocation() {
        return MouseInfo.getPointerInfo().getLocation();
    }

    private void kickCursor() {
        Point kicked = booster.kickedCursor(previous, current);
        robot.mouseMove(kicked.x, kicked.y);
    }
}
