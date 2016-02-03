package jp.satoyuichiro.cursorkicker.optimizer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuichiro on 2016/01/27.
 */

public class Trajectory {

    private List<UserInput> trajectory;

    public Trajectory() {
        trajectory = new ArrayList<>();
    }

    public Trajectory(List<UserInput> pointList) {
        trajectory = pointList;
    }

    public void add(UserInput input) {
        trajectory.add(input);
    }

    public List<UserInput> getTrajectory() {
        return trajectory;
    }

    public TrainingData toTrainingData() {
        return new TrainingData(trajectory);
    }
}
