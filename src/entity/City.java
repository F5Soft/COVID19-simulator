package entity;

import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.ArrayList;

public class City extends Pane {

    private final Timeline peopleTravel;
    private boolean travelRestriction;

    public City() {
        peopleTravel = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            ArrayList<Community> communities = getCommunities();
            int rand1 = (int) (Math.random() * communities.size());
            int rand2 = (int) (Math.random() * communities.size());
            Community community1 = communities.get(rand1);
            Community community2 = communities.get(rand2);
            People people = community1.getPeople().get(0);

            if (people != null) {
                people.stop();
                people.setVisible(false);
                community1.removePeople(people);
                community2.addPeople(people);
                PathTransition pathTransition = new PathTransition();
                pathTransition.setNode(people);
                pathTransition.setCycleCount(1);
                pathTransition.setDuration(Duration.millis(1000));
                Line line = new Line(community2.getDistanceX(community1), community2.getDistanceY(community1), 0, 0);
                pathTransition.setPath(line);
                people.setVisible(true);
                pathTransition.play();
                pathTransition.setOnFinished(ee -> people.play());
            }
        }));
        peopleTravel.setCycleCount(-1);
    }

    public void play() {
        peopleTravel.play();
        for (Community community : getCommunities()) {
            community.play();
        }
    }

    public void stop() {
        peopleTravel.stop();
        for (Community community : getCommunities()) {
            community.stop();
        }
    }

    public void addCommunity(Community community) {
        getChildren().add(community);
    }

    public ArrayList<Community> getCommunities() {
        ArrayList<Community> communities = new ArrayList<>();
        for (Node node : getChildren()) {
            communities.add((Community) node);
        }
        return communities;
    }

    public ArrayList<People> getAllPeople() {
        ArrayList<People> people = new ArrayList<>();
        for (Community community : getCommunities()) {
            people.addAll(community.getPeople());
        }
        return people;
    }

    public boolean isTravelRestriction() {
        return travelRestriction;
    }

    public void setTravelRestriction(boolean travelRestriction) {
        this.travelRestriction = travelRestriction;
    }
}
