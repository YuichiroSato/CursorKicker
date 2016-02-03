package jp.satoyuichiro.cursorkicker.optimizer.model;

import jp.satoyuichiro.cursorkicker.kicker.Booster;
import jp.satoyuichiro.cursorkicker.kicker.KickerThread;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuichiro on 2016/01/25.
 */
public class TrajectoryLogger extends Thread {

    private static TrajectoryLogger logger;

    private Robot robot;
    private Parameter parameter;
    private Booster booster;
    private boolean running;

    private boolean logging;
    private Trajectory trajectory;
    private List<Trajectory> trajectoryList;

    // mouse location
    private Point previous;
    private Point current;

    public static TrajectoryLogger getLogger() {
        if (logger == null) {
            try {
                logger = new TrajectoryLogger(Parameter.unit());
                logger.start();
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
        return logger;
    }

    private TrajectoryLogger(Parameter parameter) throws AWTException {
        this.parameter = parameter;
        this.booster = parameter.toBooster();
        robot = new Robot();
        running = true;
        logging = false;
        previous = getLocation();
        trajectory = new Trajectory();
        trajectoryList = new ArrayList<>();
    }

    @Override
    public void run() {
        while(running) {
            current = getLocation();
            if (logging) {
                trajectory.add(new UserInput(previous, current));
            }
            if (!booster.isNoise(previous, current)) {
                kickCursor();
            }
            previous = getLocation();
            try {
                Thread.sleep(KickerThread.SLEEPING_TIME);
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

    public void setBooster(Parameter parameter) {
        this.parameter = parameter;
        this.booster = parameter.toBooster();
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void startLogging() {
        logging = true;
    }

    public void stopLogging() {
        logging = false;
    }

    public void cutTrajectory() {
        trajectoryList.add(new Trajectory(trajectory.getTrajectory()));
        trajectory = new Trajectory();
    }

    public boolean isLogging() {
        return logging;
    }

    public void clearLog() {
        trajectoryList.clear();
    }

    public void killLogger() {
        running = false;
    }

    public List<TrainingData> prepareTrainingData() {
        List<TrainingData> dataList = new ArrayList<>();
        for (Trajectory trajectory : trajectoryList) {
            dataList.add(trajectory.toTrainingData());
        }
        return dataList;
    }
}
