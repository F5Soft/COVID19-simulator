import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import view.CommunityView;
import view.PeopleView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

        FlowPane cityView = (FlowPane) root.getChildrenUnmodifiable().get(0);
        for (int i = 0; i < 15; i++) {
            CommunityView communityView = new CommunityView();

            for (int j = 0; j < 30; j++) {
                PeopleView peopleView = new PeopleView(i * 30 + j);
                communityView.addPeopleView(peopleView);
            }
            cityView.getChildren().add(communityView);
        }

        primaryStage.setTitle("COVID-19 Simulator");
        primaryStage.setScene(new Scene(root, 850, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
