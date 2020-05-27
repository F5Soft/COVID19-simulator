package view;

import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;

public class CityView extends FlowPane {

    public CityView() {
    }

    public ArrayList<CommunityView> getCommunityViews() {
        ArrayList<CommunityView> CommunityViews = new ArrayList<>();
        for (Node node : getChildren()) {
            CommunityViews.add((CommunityView) node);
        }
        return CommunityViews;
    }
}
