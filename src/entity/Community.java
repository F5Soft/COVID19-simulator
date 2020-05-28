package entity;

import common.Parameter;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;


public class Community extends Pane {

    private boolean lockDown;

    public Community(double x, double y) {
        setLayoutX(x);
        setLayoutY(y);
        setPrefWidth(100);
        setPrefHeight(100);
        setStyle("-fx-border-width: 1; -fx-border-radius: 5; -fx-border-color: #d4d4d4");
    }

    public void addPeople(People people) {
        getChildren().add(people);
    }

    public void removePeople(People people) {
        getChildren().remove(people);
    }

    public void play() {
        for (People people : getPeople()) {
            people.play();
        }
    }

    public void stop() {
        for (People people : getPeople()) {
            people.stop();
        }
    }

    public void peopleCluster() {
        if (Math.random() < Parameter.getClusterRate()) {
            for (People people : getPeople()) {
                people.cluster();
            }
        }
    }

    public void toggleLockDown() {
        lockDown = !lockDown;
        if (lockDown) {
            setStyle("-fx-border-width: 2; -fx-border-radius: 5; -fx-border-color: #ce9178");
        } else {
            setStyle("-fx-border-width: 1; -fx-border-radius: 5; -fx-border-color: #d4d4d4");
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

    public boolean isLockDown() {
        return lockDown;
    }
}
