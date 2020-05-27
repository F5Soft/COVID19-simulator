package controller;

import controller.CommunityController;
import controller.PeopleController;
import entity.Community;
import entity.People;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import view.CityView;
import view.CommunityView;
import view.PeopleView;

import java.util.ArrayList;


public class Controller {

    public static CommunityController communityController;
    public static PeopleController peopleController;

    @FXML
    public CityView cityView;
    @FXML
    public Button startButton;
    @FXML
    public Button stopButton;

    @FXML
    public void startSimulate(ActionEvent actionEvent) {

        ArrayList<CommunityView> communityViews = cityView.getCommunityViews();
        ArrayList<Community> communities = new ArrayList<>();
        for (int i = 0; i < communityViews.size(); i++) {
            communities.add(new Community());
        }
        communityController = new CommunityController(communities, communityViews);

        ArrayList<PeopleView> peopleViews = new ArrayList<>();
        ArrayList<People> people = new ArrayList<>();
        for (int i = 0; i < communityViews.size(); i++) {
            CommunityView communityView = communityViews.get(i);
            peopleViews.addAll(communityView.getPeopleViews());
            for (int j = 0; j < communityView.getChildren().size(); j++) {
                people.add(new People(people.size(), communities.get(i)));
            }
        }
        peopleController = new PeopleController(people, peopleViews);

        peopleController.setOneInfected();

        startButton.setDisable(true);
        stopButton.setDisable(false);
    }

    @FXML
    public void stopSimulate(ActionEvent actionEvent) {
        startButton.setDisable(false);
        stopButton.setDisable(true);
    }
}
