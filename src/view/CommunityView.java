package view;

import javafx.scene.layout.Pane;


public class CommunityView extends Pane {
    public CommunityView() {
        setPrefWidth(100);
        setPrefHeight(100);
        setStyle("-fx-border-width: 1; -fx-border-radius: 5; -fx-border-color: #d4d4d4");
    }

    public void addPeopleView(PeopleView peopleView) {
        peopleView.setLayoutX(Math.random() * 92 + 4);
        peopleView.setLayoutY(Math.random() * 92 + 4);
        getChildren().add(peopleView);
    }
}
