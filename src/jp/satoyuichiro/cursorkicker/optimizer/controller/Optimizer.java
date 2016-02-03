package jp.satoyuichiro.cursorkicker.optimizer.controller;

import javafx.application.Application;
import javafx.stage.Stage;
import jp.satoyuichiro.cursorkicker.optimizer.model.TrajectoryLogger;
import jp.satoyuichiro.cursorkicker.optimizer.view.StartScene;

/**
 * Created by Yuichiro on 2015/12/21.
 */

public class Optimizer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Kicker Parameter Optimizer");
        stage.setResizable(false);
        stage.setScene(StartScene.startScene(stage));
        stage.setOnCloseRequest(event -> TrajectoryLogger.getLogger().killLogger());
        stage.show();
    }
}