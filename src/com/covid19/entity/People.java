package com.covid19.entity;

import com.covid19.common.Parameter;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

/**
 * 人实体
 *
 * @author F5 (bsy)
 */
public class People extends Circle {
    /**
     * 人随机游走的动画
     */
    private final Timeline randomMovement;
    /**
     * 人每隔一段时间传播病毒的模拟
     */
    private final Timeline transmission;
    /**
     * 潜伏期动画
     */
    private final Timeline incubationPeriod;
    /**
     * 发病期动画
     */
    private final Timeline symptomPeriod;
    /**
     * 感染状态
     * <p>0: 未曾感染</p>
     * <p>1: 感染，潜伏期，无症状，传播能力极低</p>
     * <p>2: 感染，开始发病，传播能力强</p>
     * <p>3: 感染后康复，已免疫</p>
     * <p>4: 感染后死亡</p>
     */
    private int status;
    /**
     * 传播给他人病毒的概率
     * <p>只有1和2状态该值不为0。其中1状态的值为2状态的1/10</p>
     */
    private double transmissionProb;
    /**
     * 自身免疫能力
     * <p>只有0状态的值为0，其余全为1。将被感染者免疫力设为1是为了防止程序出现同时重复感染的情况</p>
     */
    private double immunity;
    /**
     * 是否处于社区间旅行中
     */
    private boolean onTravel;
    /**
     * 随机游走时，上一次的方向角的变化量，用于平滑轨迹
     */
    private double dTheta;

    /**
     * 人实体的构造函数
     */
    public People() {
        // 随机设置位置
        setLayoutX(Math.random() * 92 + 4);
        setLayoutY(Math.random() * 92 + 4);
        setRadius(2);
        // 设置随机运动的动画
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
        // 设置传播的时间戳，每0.5秒（1天）进行一次传播
        transmission = new Timeline(new KeyFrame(Duration.millis(500), e -> {
            for (People people : getCommunity().getPeople()) {
                if (getSocialDistance(people) < Parameter.getTransmissionRadius()) {
                    humanToHumanTransmission(people);
                }
            }
        }));
        transmission.setCycleCount(-1);
        // 设置潜伏期时间，为3.5-7秒（7-14天）
        incubationPeriod = new Timeline(new KeyFrame(Duration.millis(3500 + Math.random() * 3500), e -> setStatus(2)));
        incubationPeriod.setCycleCount(1);
        // 设置发病期的时间，为14-28秒（28-56天）
        symptomPeriod = new Timeline(new KeyFrame(Duration.millis(14000 + Math.random() * 14000), e -> {
            if (Math.random() < Parameter.getDeathProb()) {
                setStatus(4);
            } else {
                setStatus(3);
            }
        }));
        symptomPeriod.setCycleCount(1);
        setStatus(0);
    }

    /**
     * 获取当前感染状态
     *
     * @return 当前感染状态
     */
    public int getStatus() {
        return status;
    }

    /**
     * 设置当前感染状态
     *
     * @param status 感染状态
     */
    public void setStatus(int status) {
        this.status = status;
        transmissionProb = Parameter.getBasicTransmissionProb(status);
        immunity = Parameter.getBasicImmunity(status);
        setFill(Color.web(Parameter.getStatusColor(status)));
        // 根据状态进入不同时期的动画
        switch (status) {
            case 1:
                transmission.play();
                incubationPeriod.play();
                break;
            case 2:
                symptomPeriod.play();
                break;
            case 4:
                stop();
                break;
            default:
                transmission.stop();
        }
    }

    /**
     * 开始模拟该人
     */
    public void play() {
        randomMovement.play();
        setStatus(status);
    }

    /**
     * 停止模拟该人
     */
    public void stop() {
        randomMovement.pause();
        transmission.pause();
        incubationPeriod.pause();
        symptomPeriod.pause();
    }

    /**
     * 模拟该人跑去和别人群聚的动画
     */
    public void cluster() {
        PathTransition pathTransition = new PathTransition();
        pathTransition.setNode(this);
        pathTransition.setAutoReverse(true);
        pathTransition.setCycleCount(2);
        pathTransition.setDuration(Duration.seconds(1));
        Line line = new Line(0, 0, 50 - getLayoutX(), 50 - getLayoutY());
        pathTransition.setPath(line);
        pathTransition.play();
        pathTransition.setOnFinished(e -> {
            for (People people : getCommunity().getPeople()) {
                humanToHumanTransmission(people);
            }
        });
    }

    /**
     * 模拟人传人
     * <p>传播成功概率在这里设置为：该人的传播概率*(1-被传染者的免疫力)</p>
     *
     * @param people 被传染者
     */
    public void humanToHumanTransmission(People people) {
        double rate = transmissionProb * (1 - people.immunity);
        if (Math.random() < rate) {
            people.setStatus(1);
        }
    }

    /**
     * 获取该人和另一个人之间的社交距离
     *
     * @param people 另一个人
     * @return 两人间社交距离
     */
    public double getSocialDistance(People people) {
        double dx = getLayoutX() - people.getLayoutX();
        double dy = getLayoutY() - people.getLayoutY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * 获取该人所在社区
     *
     * @return 该人所在社区
     */
    public Community getCommunity() {
        return (Community) getParent();
    }

    /**
     * 获取该人是否在旅行中
     *
     * @return 该人是否在旅行中
     */
    public boolean isOnTravel() {
        return onTravel;
    }

    /**
     * 设置该人是否在旅行中
     *
     * @param onTravel 该人是否在旅行中
     */
    public void setOnTravel(boolean onTravel) {
        this.onTravel = onTravel;
    }
}
