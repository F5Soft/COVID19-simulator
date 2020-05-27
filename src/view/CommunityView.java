package view;

import javafx.animation.PathTransition;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.ArrayList;


public class CommunityView extends Pane {


    public CommunityView() {
        setPrefWidth(100);
        setPrefHeight(100);
        setStyle("-fx-border-width: 1; -fx-border-radius: 5; -fx-border-color: #d4d4d4");
    }

    public void addPeopleView(PeopleView peopleView) {
        getChildren().add(peopleView);
    }

    public ArrayList<PeopleView> getPeopleViews() {
        ArrayList<PeopleView> peopleViews = new ArrayList<>();
        for (Node node : getChildren()) {
            peopleViews.add((PeopleView) node);
        }
        return peopleViews;
    }

    public void peopleCluster() {
        for (PeopleView peopleView : getPeopleViews()) {
            peopleView.cluster();
        }
    }
}
