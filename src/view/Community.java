package view;

import com.sun.scenario.effect.impl.prism.ps.PPSBlend_ADDPeer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;


public class Community extends Pane {

    private final Timeline peopleCluster;
    private double flowRate;

    public Community(double x, double y) {
        setLayoutX(x);
        setLayoutY(y);
        setPrefWidth(100);
        setPrefHeight(100);
        setStyle("-fx-border-width: 1; -fx-border-radius: 5; -fx-border-color: #d4d4d4");
        flowRate = 0.6;

        peopleCluster = new Timeline(new KeyFrame(Duration.millis(4000), e -> {
            if (Math.random() < flowRate)
                for (People people : getPeople()) {
                    people.cluster();
                }
        }));
        peopleCluster.setCycleCount(-1);
    }

    public void addPeople(People people) {
        getChildren().add(people);
    }

    public void removePeople(People people) {
        getChildren().remove(people);
    }

    public void play() {
        peopleCluster.play();
        for (People people : getPeople()) {
            people.play();
        }
    }

    public void stop() {
        peopleCluster.stop();
        for (People people : getPeople()) {
            people.stop();
        }
    }

    public ArrayList<People> getPeople() {
        ArrayList<People> people = new ArrayList<>();
        for (Node node : getChildren()) {
            people.add((People) node);
        }
        return people;
    }

    public double getDistanceX(Community community) {
        return community.getLayoutX() - getLayoutX();
    }

    public double getDistanceY(Community community) {
        return community.getLayoutY() - getLayoutY();
    }
}
