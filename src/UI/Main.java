package UI;

import entity.Community;
import entity.People;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        Community community = new Community();
        People[] people = new People[30];
        for (int i = 0; i < people.length; i++) {
            people[i] = new People(community);
        }
        people[0].setStatus(2);
        community.communityTransmission();
        for (People p : people) {
            System.out.println(p.getStatus());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
