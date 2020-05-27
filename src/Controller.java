import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import view.CityView;
import view.CommunityView;
import view.PeopleView;

import java.util.ArrayList;


public class Controller {
    @FXML
    public CityView cityView;

    @FXML
    public void startSimulate(ActionEvent actionEvent) {
        ArrayList<CommunityView> communityViews = cityView.getCommunityViews();
        int rand = (int) (Math.random() * communityViews.size());
        communityViews.get(rand).getPeopleViews().get(0).setStatus(1);
        communityViews.get(rand).peopleCluster();
    }
}
