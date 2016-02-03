package jp.satoyuichiro.cursorkicker.optimizer.view;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import jp.satoyuichiro.cursorkicker.optimizer.model.Trajectory;
import jp.satoyuichiro.cursorkicker.optimizer.model.UserInput;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuichiro on 2016/02/03.
 */
public class TutorialScene {

    public static Scene tutorialScene(Stage stage) {
        Label label = new Label("Comming soon");

        Button backButton = new Button("Back to Start");
        backButton.setOnAction(event -> stage.setScene(StartScene.startScene(stage)));

        VBox pane = new VBox();
        pane.getChildren().addAll(label, backButton);
        pane.setSpacing(20);
        pane.setAlignment(Pos.CENTER);

        return new Scene(pane, 300, 240);
    }

    private Group trajectoryListToLines(List<Trajectory> trajectoryList, double x, double y) {
        Group g = new Group();
        List<Line> lineList = new ArrayList<>();
        for (Trajectory trajectory : trajectoryList) {
            lineList.addAll(trajectoryToLine(trajectory, x, y));
        }
        g.getChildren().addAll(lineList);
        return g;
    }

    private List<Line> trajectoryToLine(Trajectory trajectory, double x, double y) {
        List<Line> lineList = new ArrayList<>();
        List<UserInput> inputList = trajectory.getTrajectory();
        // if trajectory do not have enough points, return empty list
        if (inputList.size() < 2) {
            return lineList;
        }

        for (UserInput input : inputList) {
            Point start = input.getFrom();
            Point end = input.getTo();
            // start.x is x value on screen. start.x - x is relative x value on application
            Line line = new Line(start.x - x, start.y - y, end.x - x, end.y - y);
            lineList.add(line);
        }
        return lineList;
    }

}
