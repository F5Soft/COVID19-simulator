import common.Parameter;
import common.Statistics;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import entity.City;
import entity.Community;
import entity.People;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Controller {
    @FXML
    public City city;
    @FXML
    public Button startButton;
    @FXML
    public Button optionButton;
    public Pane statisticsPane;

    private Timeline statisticsLive;
    private static double statisticsX = 20;

    public void startSimulate(ActionEvent actionEvent) {
        ArrayList<People> people = city.getAllPeople();
        int rand = (int) (Math.random() * people.size());
        people.get(rand).setStatus(1);

        statisticsLive = new Timeline(new KeyFrame(Duration.millis(500), e -> {
            Statistics.updateStatistics(people);
            int[] statistics = Statistics.getStatistics();
            double sum = 0;
            for (int i = 0; i < 5; i++) {
                sum += statistics[i];
            }
            double height = 0;
            for (int i: new int[] {3, 0, 2, 1, 4}) {
                Line line = new Line(statisticsX, height, statisticsX, height + statistics[i] / sum * 140);
                line.setStroke(Color.web(Parameter.getStatusColor(i)));
                height += statistics[i] / sum * 140;
                statisticsPane.getChildren().add(line);
            }
            statisticsX += 0.5;
        }));
        statisticsLive.setCycleCount(-1);
        statisticsLive.play();
        city.play();
    }

    public void openOptionWindow(ActionEvent actionEvent) {

    }
}
