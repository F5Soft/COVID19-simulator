package view;

import Common.Parameter;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.Random;


public class People extends Circle {

    private int status;
    private double transmissionProb;
    private double immunity;
    private boolean died;

    private final Timeline randomMovement;
    private final Timeline transmission;
    private final Timeline incubationPeriod;
    private final Timeline symptomPeriod;
    private double dTheta;

    public People() {
        setLayoutX(Math.random() * 92 + 4);
        setLayoutY(Math.random() * 92 + 4);
        setRadius(2);

        dTheta = Math.random() * 2 * Math.PI;
        randomMovement = new Timeline(new KeyFrame(Duration.millis(40), e -> { // 40 for 25 FPS
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
        }));
        randomMovement.setCycleCount(-1);

        transmission = new Timeline(new KeyFrame(Duration.millis(500), e -> {
            Community community = getCommunity();
            for (People people : community.getPeople()) {
                if (getSocialDistance(people) < 8) {
                    humanToHumanTransmission(people);
                }
            }
        }));
        transmission.setCycleCount(-1);

        incubationPeriod = new Timeline(new KeyFrame(Duration.millis(3500 + Math.random() * 3500), e -> {
            setStatus(2);
        }));
        incubationPeriod.setCycleCount(1);

        symptomPeriod = new Timeline(new KeyFrame(Duration.millis(14000 + Math.random() * 14000), e -> {
            if (Math.random() < Parameter.getDeathRate()) {
                setStatus(4);
            } else {
                setStatus(3);
            }
        }));
        symptomPeriod.setCycleCount(1);

        setStatus(0);
    }

    public void setStatus(int status) {
        this.status = status;
        transmissionProb = Parameter.getBasicTransmissionProb(status);
        immunity = Parameter.getBasicImmunity(status);
        setFill(Color.web(Parameter.getStatusColor(status)));

        switch (status) {
            case 1:
                transmission.play();
                incubationPeriod.play();
                break;
            case 2:
                symptomPeriod.play();
                break;
            case 4:
                died = true;
                stop();
                break;
            default:
                transmission.stop();
        }
    }

    public void play() {
        randomMovement.play();
        setStatus(status);
    }

    public void stop() {
        randomMovement.stop();
        transmission.stop();
        incubationPeriod.stop();
        symptomPeriod.stop();
    }

    public void cluster() {
        PathTransition pathTransition = new PathTransition();
        pathTransition.setNode(this);
        pathTransition.setAutoReverse(true);
        pathTransition.setCycleCount(2);
        pathTransition.setDuration(Duration.seconds(1));

        Line line = new Line(0, 0, 50 - getLayoutX(), 50 - getLayoutY());
        pathTransition.setPath(line);
        pathTransition.play();
    }

    public void humanToHumanTransmission(People people) {
        double rate = transmissionProb * (1 - people.immunity);
        if (Math.random() < rate) {
            people.setStatus(1);
        }
    }

    public double getSocialDistance(People people) {
        double dx = getLayoutX() - people.getLayoutX();
        double dy = getLayoutY() - people.getLayoutY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public Community getCommunity() {
        return (Community) getParent();
    }
}
