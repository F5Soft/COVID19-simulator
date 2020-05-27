package view;

import Common.Parameter;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;


public class PeopleView extends Circle {

    public final int index;

    private final Timeline randomMovement;
    private double dTheta;

    public PeopleView(int index) {
        this.index = index;
        setLayoutX(Math.random() * 92 + 4);
        setLayoutY(Math.random() * 92 + 4);
        setRadius(2);
        setStatus(0);
        dTheta = Math.random() * 2 * Math.PI;

        KeyFrame keyFrame = new KeyFrame(Duration.millis(40), e -> { // 40 for 25 FPS
            dTheta += Math.random() - 0.5;
            double dx = Math.cos(dTheta) / 2;
            double dy = Math.sin(dTheta) / 2;

            double x = getLayoutX();
            double y = getLayoutY();
            if (x + dx > 4 && x + dx < 96) {
                setLayoutX(x + dx);
            } else {
                setLayoutX(x - dx);
                dTheta = Math.PI - dTheta;
            }
            if (y + dy > 4 && y + dy < 96) {
                setLayoutY(y + dy);
            } else {
                setLayoutY(y - dy);
                dTheta = 2 * Math.PI - dTheta;
            }
        });
        randomMovement = new Timeline(keyFrame);
        randomMovement.setCycleCount(-1);
        randomMovement.play();
    }

    public void setStatus(int status) {
        setFill(Color.web(Parameter.getStatusColor(status)));
    }

    public void cluster() {
        randomMovement.stop();
        PathTransition pathTransition = new PathTransition();
        pathTransition.setNode(this);
        pathTransition.setAutoReverse(true);
        pathTransition.setCycleCount(2);
        pathTransition.setDuration(Duration.seconds(1));

        Line line = new Line(0, 0, 50 - getLayoutX(), 50 - getLayoutY());
        pathTransition.setPath(line);
        pathTransition.play();
        randomMovement.play();
    }

    public void die() {
        randomMovement.stop();
    }
}
