package entity;

import Common.Parameter;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    public final int index;
    public final Community community;

    private Community position;
    private int status;
    private double transmissionProb;
    private double immunity;

    public People(int index, Community community) {
        this.index = index;
        this.community = community;
        this.community.residentCome(this);
        this.position = community;
        setStatus(0);
    }

    public void setPosition(Community position) {
        this.position = position;
    }

    public Community getPosition() {
        return position;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
        transmissionProb = Parameter.getBasicTransmissionProb(status);
        immunity = Parameter.getBasicImmunity(status);
    }

    public double getTransmissionProb() {
        return transmissionProb;
    }

    public double getImmunity() {
        return immunity;
    }
}
