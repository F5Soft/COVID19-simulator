package entity;

import java.util.ArrayList;
import java.util.List;

public class Community {
    private final List<People> residents;
    private final List<People> visitors;
    private double flowRate;

    public Community() {
        residents = new ArrayList<>();
        visitors = new ArrayList<>();
        flowRate = 0.5;
    }

    public void communityTransmission() {
        List<People> cluster = new ArrayList<>();
        for (People people : residents) {
            if (Math.random() < flowRate) {
                cluster.add(people);
            }
        }
        for (People people : visitors) {
            if (Math.random() < flowRate) {
                cluster.add(people);
            }
        }
        for (People people : cluster) {
            people.humanToHumanTransmission(cluster);
        }
    }

    public void residentCome(People resident) {
        residents.add(resident);
    }

    public void residentLeave(People resident){
        residents.remove(resident);
    }

    public void visitorCome(People visitor) {
        visitors.add(visitor);
    }

    public void visitorLeave(People visitor) {
        visitors.remove(visitor);
    }

    public double getFlowRate() {
        return flowRate;
    }

    public void setFlowRate(double flowRate) {
        this.flowRate = flowRate;
    }
}
