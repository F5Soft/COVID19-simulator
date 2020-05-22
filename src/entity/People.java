package entity;

import Common.Parameter;

import java.util.List;

public class People {
    /**
     * 感染状态
     * <p>
     * 0: 未感染<br>
     * 1: 潜伏(无症状)<br>
     * 2: 感染<br>
     * 3: 治愈
     * </p>
     */
    public final Community community;
    private int status;
    private boolean wearMask;
    private double reproduction;
    private double immunity;

    public People() {
        community = null;
        setStatus(0);
    }

    public People(int status) {
        community = null;
        setStatus(status);
    }

    public People(Community community) {
        this.community = community;
        this.community.residentCome(this);
        setStatus(0);
    }

    public void humanToHumanTransmission(List<People> receivers) {
        double basicProbability = getReproduction() / receivers.size();
        for (People receiver : receivers) {
            double probability = basicProbability * (1 - receiver.getImmunity());
            if (receiver.isWearMask()) {
                probability *= 0.4;
            }
            if (Math.random() < probability) {
                receiver.setStatus(1);
            }
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
        reproduction = Parameter.getBasicReproduction(status);
        immunity = Parameter.getBasicImmunity(status);
        if (wearMask) {
            reproduction *= Parameter.getChanceReduceWithMask();
            immunity = 1 - (1 - immunity) * Parameter.getChanceReduceWithMask();
        }
    }

    public boolean isWearMask() {
        return wearMask;
    }

    public void setWearMask(boolean wearMask) {
        this.wearMask = wearMask;
        reproduction *= Parameter.getChanceReduceWithMask();
        immunity = 1 - (1 - immunity) * Parameter.getChanceReduceWithMask();
    }

    public double getReproduction() {
        return reproduction;
    }

    public double getImmunity() {
        return immunity;
    }
}
