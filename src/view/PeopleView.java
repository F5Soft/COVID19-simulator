package view;

import Common.Parameter;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.Random;

public class PeopleView extends Circle {

    private final Timeline animation;

    public PeopleView() {
        setRadius(4);
        setStatus(0);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(40), e -> { // 40 for 25 FPS
            double x = getLayoutX() + Math.random() * 2 - 1;
            double y = getLayoutY() + Math.random() * 2 - 1;
            if (x > 4 && x < 96) {
                setLayoutX(x);
            } else if (x <= 4) {
                setLayoutX(5);
            } else if (x >= 96) {
                setLayoutX(95);
            }
            if (y > 4 && y < 96) {
                setLayoutY(y);
            } else if (y <= 4) {
                setLayoutY(5);
            } else if (y >= 96) {
                setLayoutY(95);
            }
        });
        animation = new Timeline(keyFrame);
        animation.setCycleCount(-1);
        animation.play();
    }

    public void setStatus(int status) {
        setFill(Color.web(Parameter.getStatusColor(status)));
    }

    public void move() {
        animation.play();
    }

    public void stop() {
        animation.stop();
    }

    public void cluster() {
        PathTransition animation = new PathTransition();
        animation.setNode(this);
        animation.setAutoReverse(true);
        animation.setCycleCount(2);
        animation.setDuration(Duration.seconds(2));

        Line line = new Line(0, 0, 50 - getLayoutX(), 50 - getLayoutY());
        animation.setPath(line);
        animation.play();

    }
}
