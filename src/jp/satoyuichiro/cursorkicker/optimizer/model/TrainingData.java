package jp.satoyuichiro.cursorkicker.optimizer.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuichiro on 2016/01/25.
 */

public class TrainingData {

    private List<UserInput> optimizeList;
    private List<UserInput> lostList;
    private double totalDistance;

    public TrainingData(List<UserInput> trajectory) {
        List<UserInput> dataList = new ArrayList<>();
        for (UserInput input : trajectory) {
            if (input.getLength() > 0) {
                dataList.add(input);
            }
        }

        totalDistance = distance(dataList);

        optimizeList = new ArrayList<>();
        lostList = new ArrayList<>();
        if (dataList.size() > 0) {
            Point start = dataList.get(0).getFrom();
            for (UserInput input : dataList) {
                if (start.distance(input.getFrom()) < totalDistance) {
                    optimizeList.add(input);
                } else {
                    lostList.add(input);
                }
            }
        }
    }

    public double distance(List<UserInput> dataList) {
        if (dataList.size() < 2) {
            return 0.0;
        }
        Point start = dataList.get(0).getFrom();
        Point end = dataList.get(dataList.size() - 1).getTo();
        return start.distance(end);
    }

    public double getDistance() { return totalDistance; }
    public List<UserInput> getOptimizeList() { return optimizeList; }
    public List<UserInput> getLostList() { return lostList; }
}
