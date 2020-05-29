import common.Parameter;
import common.Statistics;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import entity.City;
import entity.Community;
import entity.People;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    @FXML
    public Pane statisticsPane;
    @FXML
    public Label daysLabel;
    @FXML
    public TextField param1;
    @FXML
    public TextField param2;
    @FXML
    public TextField param3;

    private Timeline statisticsLive;
    private static int days = 0;

    @FXML
    public void startSimulate(ActionEvent actionEvent) {

        try {
            double basicTransmissionProb = Double.parseDouble(param1.getText());
            double transmissionRadius = Double.parseDouble(param2.getText());
            double deathProb = Double.parseDouble(param3.getText());
            if (basicTransmissionProb < 0 || basicTransmissionProb > 1) {
                throw new Exception("Transmission probability should be 0 ~ 1!");
            }
            if (transmissionRadius < 0 || transmissionRadius > 20) {
                throw new Exception("Transmission radius should be 0 ~ 20");
            }
            if (deathProb < 0 || deathProb > 1) {
                throw new Exception("Death probability should be 0 ~ 1!");
            }
            Parameter.setBasicTransmissionProb(1, basicTransmissionProb * 0.1);
            Parameter.setBasicTransmissionProb(2, basicTransmissionProb);
            Parameter.setTransmissionRadius(transmissionRadius);
            Parameter.setDeathProb(deathProb);
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid params!");
            alert.setHeaderText(null);
            alert.setContentText("Please inout numeric value!");
            alert.showAndWait();
            return;
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid params!");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            return;
        }

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
            double statisticsX = (days % 365) / 2.0 + 20;
            for (int i : new int[]{3, 0, 2, 1, 4}) {
                Line line = new Line(statisticsX, statisticsY, statisticsX, statisticsY + statistics[i] / sum * 140);
                line.setStroke(Color.web(Parameter.getStatusColor(i)));
                statisticsY += statistics[i] / sum * 140;
                statisticsPane.getChildren().add(line);
            }
            days++;
            if (days % 365 == 0) {
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
