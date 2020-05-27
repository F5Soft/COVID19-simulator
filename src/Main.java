import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import entity.City;
import entity.Community;
import entity.People;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

        City city = (City) root.getChildrenUnmodifiable().get(0);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                Community community = new Community(110 * j + 10, 110 * i + 10);
                for (int k = 0; k < 30; k++) {
                    People people = new People();
                    community.addPeople(people);
                }
                city.addCommunity(community);
            }
        }

        primaryStage.setTitle("COVID-19 Simulator");
        primaryStage.setScene(new Scene(root, 850, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
