import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import view.City;
import view.Community;
import view.People;

import java.util.ArrayList;

public class Controller {
    @FXML
    public City city;
    @FXML
    public Button startButton;
    @FXML
    public Button optionButton;

    public void startSimulate(ActionEvent actionEvent) {
        ArrayList<People> people = city.getAllPeople();
        ArrayList<Community> communities = city.getCommunities();
        int rand = (int) (Math.random() * people.size());
        people.get(rand).setStatus(1);
        city.play();
    }

    public void openOptionWindow(ActionEvent actionEvent) {

    }
}
