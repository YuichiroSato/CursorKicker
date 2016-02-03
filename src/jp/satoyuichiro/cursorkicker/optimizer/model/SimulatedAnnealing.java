package jp.satoyuichiro.cursorkicker.optimizer.model;

import jp.satoyuichiro.cursorkicker.kicker.KickerThread;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuichiro on 2016/01/25.
 */

public class SimulatedAnnealing {

    // When optimization is done, user inputs are boosted as this ratio
    private static final double BOOSTING_RATIO = 5.0;
    // When optimization is done, user is able to move his cursor within this milli second
    private static final double HUMAN_RESPONSE = 200.0;
    // Iteration for simulated annealing
    private static final int MAX_ITERATE = 1000 * 1000;

    private List<TrainingData> dataList;

    public SimulatedAnnealing() {
        dataList = new ArrayList<>();
    }

    public void putAllData(List<TrainingData> data) { dataList.addAll(data); }

    public void clearData() {
        dataList.clear();
    }

    public Parameter runSimulatedAnnealing(Parameter parameter) {
        return optimization(parameter);
    }

    private Parameter optimization(Parameter p) {
        Parameter previousP = p;
        Parameter neighborP;
        double error0 = evaluateError(p);
        double error1;

        for (int n = 0; n < MAX_ITERATE; n++) {
            neighborP = neighbor(previousP);
            error1 = evaluateError(neighborP);
            if (error1 < error0) {
                previousP = neighborP;
                error0 = error1;
            } else if (forceTransition(n)) {
                previousP = neighborP;
                error0 = error1;
            }
        }
        return previousP;
    }

    private double evaluateError(Parameter p) {
        double error = 0.0;
        for (TrainingData data : dataList) {
            error += distanceError(p, data) + timeError(p, data) + lostError(p, data);
        }
        error += selfDecay(p);
        return error;
    }

    private double distanceError(Parameter p, TrainingData data) {
        double fx = 0.0;
        double d = BOOSTING_RATIO * data.getDistance();
        for (UserInput input : data.getOptimizeList()) {
            fx += p.boost(input.getLength());
        }
        return Math.pow(d - fx, 2.0);
    }

    private double timeError(Parameter p, TrainingData data) {
        double t = 0.0;
        double d = data.getDistance();
        double fx = 0.0;
        for (UserInput input : data.getOptimizeList()) {
            if (fx < d) {
                fx += p.boost(input.getLength());
                t += KickerThread.SLEEPING_TIME;
            }
        }
        return Math.pow(HUMAN_RESPONSE - t, 2.0);
    }

    private double lostError(Parameter p, TrainingData data) {
        double error = 0.0;
        for (UserInput input : data.getLostList()) {
            error += Math.pow(p.boost(input.getLength()), 2.0);
            if (!p.isNoise(input.getLength())) {
                error += KickerThread.SLEEPING_TIME;
            }
        }
        return error;
    }

    private double selfDecay(Parameter p) {
        return p.norm();
    }

    private Parameter neighbor(Parameter base) {
        Parameter p = Parameter.randomParameter().add(base);
        if (p.getThreshold() < 1) {
            p.setThreshold(1.0);
        }
         return p;
    }

    private boolean forceTransition(int n) {
        return (double)(MAX_ITERATE - n) / MAX_ITERATE > Math.random();
    }
}
