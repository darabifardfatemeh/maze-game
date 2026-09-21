package com.maze;

import com.maze.controller.GameController;
import com.maze.exceptions.LethalCollisionException;
import com.maze.placer.BranchAwarePlacer;
import com.maze.generation.AldousBroderMazeGenerator;
import com.maze.generation.DFSMazeGenerator;
import com.maze.generation.PrimMazeGenerator;
import com.maze.score.ScoreCalculator;
import com.maze.view.MazeView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.List;

public class MainApp extends Application {

    private GameController controller;
    private MazeView mazeView;
    private Label scoreLabel;
    private Label levelLabel;
    private Label timerLabel;
    private Label totalLabel;
    private Label bombsLabel;
    private Label starsLabel;
    private Timeline timer;

    @Override
    public void start(Stage stage) {
        controller = new GameController(
                List.of(
                        new DFSMazeGenerator(),
                        new PrimMazeGenerator(),
                        new AldousBroderMazeGenerator()
                ),
                new ScoreCalculator(),
                new BranchAwarePlacer()
        );
        controller.startGame();

        mazeView = new MazeView(800, 600);
        mazeView.bind(controller.getMaze(), controller.getPlayer());
        mazeView.setEntities(controller.getEntities());

        scoreLabel = new Label("Score: " + controller.getScore());
        levelLabel = new Label("Level: " + (controller.getCurrentLevelIndex() + 1));
        timerLabel = new Label("Time: " + controller.getRemainingSeconds() + "s");
        totalLabel = new Label("Total: " + controller.getTotalScore());
        bombsLabel = new Label("Bombs: " + controller.getPlayer().getBombs());
        starsLabel = new Label("Stars: " + controller.getPlayer().getStars());

        Button restartBtn = new Button("Restart Level");
        restartBtn.setOnAction(e -> restartLevel());

        HBox topBar = new HBox(10, restartBtn, levelLabel, scoreLabel,
                totalLabel, timerLabel, bombsLabel, starsLabel);
        topBar.setPadding(new Insets(8));

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(mazeView);

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(ke -> {
            if (ke.getCode() == KeyCode.X) {
                controller.consumeStarForBonusTime();
                refreshUI();
                return;
            }
            try {
                if (ke.getCode() == KeyCode.W || ke.getCode() == KeyCode.UP) controller.moveUp();
                else if (ke.getCode() == KeyCode.S || ke.getCode() == KeyCode.DOWN) controller.moveDown();
                else if (ke.getCode() == KeyCode.A || ke.getCode() == KeyCode.LEFT) controller.moveLeft();
                else if (ke.getCode() == KeyCode.D || ke.getCode() == KeyCode.RIGHT) controller.moveRight();
            } catch (LethalCollisionException ex) {
                handleLethalCollision(ex.getMessage());
                return;
            } finally {
                refreshUI();
            }
            if (controller.isWon()) {
                handleWin();
            }
        });

        initTimer();

        stage.setTitle("Maze Game");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    private void restartLevel() {
        controller.startLevel();
        mazeView.bind(controller.getMaze(), controller.getPlayer());
        mazeView.setEntities(controller.getEntities());
        refreshUI();
        initTimer();
    }

    private void refreshUI() {
        mazeView.setEntities(controller.getEntities());
        mazeView.draw();
        scoreLabel.setText("Score: " + controller.getScore());
        levelLabel.setText("Level: " + (controller.getCurrentLevelIndex() + 1));
        timerLabel.setText("Time: " + controller.getRemainingSeconds() + "s");
        totalLabel.setText("Total: " + controller.getTotalScore());
        bombsLabel.setText("Bombs: " + controller.getPlayer().getBombs());
        starsLabel.setText("Stars: " + controller.getPlayer().getStars());
    }

    private void showWinDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You Win!");
        alert.setHeaderText("Congratulations!");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void handleLethalCollision(String reason) {
        if (timer != null) timer.stop();
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Caught!");
            alert.setHeaderText("Level failed");
            alert.setContentText(reason + "\nTry again!");
            alert.showAndWait();
            controller.startLevel();
            mazeView.bind(controller.getMaze(), controller.getPlayer());
            mazeView.setEntities(controller.getEntities());
            refreshUI();
            initTimer();
        });
    }

    private void initTimer() {
        if (timer != null) timer.stop();
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            try {
                controller.tickOneSecond();
            } catch (LethalCollisionException ex) {
                handleLethalCollision(ex.getMessage());
                return;
            }
            timerLabel.setText("Time: " + controller.getRemainingSeconds() + "s");
            mazeView.draw();
            if (controller.isTimeUp()) {
                timer.stop();
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Time's up");
                    alert.setHeaderText("Level failed");
                    alert.setContentText("Try again!");
                    alert.showAndWait();
                    controller.startLevel();
                    mazeView.bind(controller.getMaze(), controller.getPlayer());
                    mazeView.setEntities(controller.getEntities());
                    refreshUI();
                    initTimer();
                });
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void handleWin() {
        if (timer != null) timer.stop();
        if (controller.isLastLevel()) {
            showWinDialog(
                    "You finished all levels! Total score: " + controller.getTotalScore()
            );
            controller.startGame();
        } else {
            showWinDialog("Level complete! jump into the next level ;) ");
            controller.nextLevel();
        }
        mazeView.bind(controller.getMaze(), controller.getPlayer());
        mazeView.setEntities(controller.getEntities());
        refreshUI();
        initTimer();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
