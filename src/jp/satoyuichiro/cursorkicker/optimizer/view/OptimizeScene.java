package jp.satoyuichiro.cursorkicker.optimizer.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import jp.satoyuichiro.cursorkicker.optimizer.model.*;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Created by Yuichiro on 2016/02/03.
 */
public class OptimizeScene {

    // Number of UserInputs used for optimization
    private static final int MAX_COUNT = 30;

    Stage stage;
    Circle circle;
    int count;

    int screenWidth;
    int screenHeight;


    public OptimizeScene(Stage stage) {
        circle = new Circle();
        count = 0;
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int)d.getWidth();
        screenHeight = (int)d.getHeight();
        stage.setX(0);
        stage.setY(0);
        this.stage = stage;
        TrajectoryLogger.getLogger().startLogging();
    }

    public Scene optimizeScene() {
        Group g = new Group();
        circle.setCenterX(screenWidth / 2);
        circle.setCenterY(screenHeight / 2);
        circle.setRadius(20);
        circle.setVisible(true);
        g.getChildren().add(circle);
        g.setOnMouseClicked(event -> {
            if (TrajectoryLogger.getLogger().isLogging()) {
                TrajectoryLogger.getLogger().cutTrajectory();
            } else {
                TrajectoryLogger.getLogger().startLogging();
            }

            updateCircle();
            count++;
            if (MAX_COUNT < count) {
                optimize();
                goToNextScene();
            }
        });
        return new Scene(g, screenWidth, screenHeight);
    }

    private void updateCircle() {
        double newX = 40 + Math.random() * (screenWidth - 80);
        double newY = 40 + Math.random() * (screenHeight - 80);
        circle.setCenterX(newX);
        circle.setCenterY(newY);
    }

    private void optimize() {
        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing();
        simulatedAnnealing.clearData();
        simulatedAnnealing.putAllData(TrajectoryLogger.getLogger().prepareTrainingData());
        Parameter newParameter = simulatedAnnealing.runSimulatedAnnealing(TrajectoryLogger.getLogger().getParameter());
        TrajectoryLogger.getLogger().setBooster(newParameter);
        TrajectoryLogger.getLogger().clearLog();
        TrajectoryLogger.getLogger().stopLogging();
        newParameter.toProperty();
    }

    private void goToNextScene() {
        stage.setScene(StartScene.startScene(stage));
    }
}
