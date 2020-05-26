package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class PeopleView extends Circle {

    private final Timeline animation;

    public PeopleView() {
        setRadius(4);
        setStatus(0);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(50), e -> {
            double x = getLayoutX() + Math.random() * 2 - 1;
            double y = getLayoutY() + Math.random() * 2 - 1;
            if (x > 4 && x < 96) {
                setLayoutX(x);
            }
            if (y > 4 && y < 96) {
                setLayoutY(y);
            }
        });
        animation = new Timeline(keyFrame);
        animation.setCycleCount(-1);
        animation.play();
    }

    public void setStatus(int status) {
        String[] colors = {"#4ec9b0", "#dcdcaa", "#ce9178", "#4ec9b0"};
        setFill(Color.web(colors[status]));
    }

    public void move() {
        animation.play();
    }

    public void stop() {
        animation.stop();
    }
}
