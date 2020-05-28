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
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
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
    public Label daysLabel;

    private Timeline statisticsLive;
    private static int days = 0;

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
            double statisticsY = 0;
            double statisticsX = days / 2.0 + 20;
            for (int i : new int[]{3, 0, 2, 1, 4}) {
                Line line = new Line(statisticsX, statisticsY, statisticsX, statisticsY + statistics[i] / sum * 140);
                line.setStroke(Color.web(Parameter.getStatusColor(i)));
                statisticsY += statistics[i] / sum * 140;
                statisticsPane.getChildren().add(line);
            }
            System.out.println(sum);
            days++;
            if (days == 390) {
                days = 0;
                statisticsPane.getChildren().clear();
            }
            daysLabel.setText(days + " Days");
        }));
        statisticsLive.setCycleCount(-1);
        statisticsLive.play();
        city.play();
    }

    public void openOptionWindow(ActionEvent actionEvent) {

    }
}
