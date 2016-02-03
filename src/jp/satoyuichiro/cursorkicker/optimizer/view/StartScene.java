package jp.satoyuichiro.cursorkicker.optimizer.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Yuichiro on 2016/02/03.
 */
public class StartScene {

    public static Scene startScene(Stage stage) {
        Label label = new Label("Kicker Parameter Optimizer");

        Button startButton = new Button("Start optimize");
        startButton.setOnAction(event -> stage.setScene((new OptimizeScene(stage)).optimizeScene()));

        Button tutorialButton = new Button("Tutorial");
        tutorialButton.setOnAction(event -> stage.setScene(TutorialScene.tutorialScene(stage)));

        VBox pane = new VBox();
        pane.getChildren().addAll(label, startButton, tutorialButton);
        pane.setSpacing(20);
        pane.setAlignment(Pos.CENTER);

        return new Scene(pane, 300, 240);
    }
}
