package controller;

import Common.Parameter;
import entity.People;
import view.PeopleView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class PeopleController {
    private ArrayList<People> people;
    private ArrayList<PeopleView> peopleViews;
    private int size;

    public PeopleController(ArrayList<People> people, ArrayList<PeopleView> peopleViews) {
        this.people = people;
        this.peopleViews = peopleViews;
        this.size = people.size();
    }

    public void setOneInfected() {
        int rand = (int) (Math.random() * size);
        setPeopleStatus(rand, 1);
    }

    public double getPeopleDistance(int index1, int index2) {
        PeopleView peopleView1 = peopleViews.get(index1);
        PeopleView peopleView2 = peopleViews.get(index2);
        if (peopleView1.getParent() != peopleView2.getParent())
            return Double.POSITIVE_INFINITY;
        return Math.sqrt(Math.pow(peopleView1.getLayoutX() - peopleView2.getLayoutX(), 2) +
                Math.pow(peopleView1.getLayoutY() - peopleView2.getLayoutY(), 2));
    }

    public void setPeopleStatus(int index, int status) {
        People people = this.people.get(index);
        PeopleView peopleView = this.peopleViews.get(index);
        people.setStatus(status);
        peopleView.setStatus(status);

        if (status == 1) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    setPeopleStatus(index, 2);
                }
            }, (int) (Math.random() * 7000 + 7000));
        } else if (status == 2) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    humanToHumanTransmission(index);
                }
            }, 0, 1000);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (Math.random() < Parameter.getDeathRate()) {
                        setPeopleStatus(index, 4);
                    } else {
                        setPeopleStatus(index, 3);
                    }
                }
            }, 28000);
        } else if (status == 4) {
            peopleView.die();
        }
    }

    public void humanToHumanTransmission(int index1) {
        People people1 = people.get(index1);
        for (int index2 = 0; index2 < size; index2++) {
            People people2 = people.get(index2);
            if (index1 != index2 && getPeopleDistance(index1, index2) < 8) {
                double rate = people1.getTransmissionProb() * (1 - people2.getImmunity());
                if (Math.random() < rate) {
                    setPeopleStatus(index2, 1);
                }
            }
        }
    }
}
