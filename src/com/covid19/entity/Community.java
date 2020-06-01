package com.covid19.entity;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * 社区实体
 * <p>既是社区模型，也是窗口中的社区视图（圆角矩形框）</p>
 *
 * @author F5 (bsy)
 */
public class Community extends Pane {
    /**
     * 社区是否被封锁
     */
    private boolean lockDown;
    /**
     * 社区是否正在群聚
     */
    private boolean clustering;

    /**
     * 社区实体构造函数
     *
     * @param x 社区所在的横坐标
     * @param y 社区所在的纵坐标
     */
    public Community(double x, double y) {
        setLayoutX(x);
        setLayoutY(y);
        setPrefWidth(100);
        setPrefHeight(100);
        setStyle("-fx-border-width: 1; -fx-border-radius: 5; -fx-border-color: #d4d4d4");
    }

    /**
     * 向社区添加一个人
     *
     * @param people 待添加的人
     */
    public void addPeople(People people) {
        getChildren().add(people);
    }

    /**
     * 从社区中移除一个人
     *
     * @param people 待移除的人
     */
    public void removePeople(People people) {
        getChildren().remove(people);
    }

    /**
     * 开始模拟该社区
     */
    public void play() {
        for (People people : getPeople()) {
            people.play();
        }
    }

    /**
     * 停止模拟该社区
     */
    public void stop() {
        for (People people : getPeople()) {
            people.stop();
        }
    }

    /**
     * 模拟社区人员群聚
     */
    public void peopleCluster(double clusterRate) {
        for (People people : getPeople()) {
            if (Math.random() < clusterRate) {
                people.cluster();
            }
        }
    }

    /**
     * 切换社区是否封锁的状态
     */
    public void toggleLockDown() {
        lockDown = !lockDown;
        if (lockDown) {
            setStyle("-fx-border-width: 2; -fx-border-radius: 5; -fx-border-color: #ce9178");
        } else {
            setStyle("-fx-border-width: 1; -fx-border-radius: 5; -fx-border-color: #d4d4d4");
        }
    }

    /**
     * 取得该社区的所有人
     *
     * @return 该社区中所有人的ArrayList
     */
    public ArrayList<People> getPeople() {
        ArrayList<People> people = new ArrayList<>();
        for (Node node : getChildren()) {
            people.add((People) node);
        }
        return people;
    }

    /**
     * 获取该社区和另一个社区之间的横坐标差
     *
     * @param community 另一个社区
     * @return 该社区和另一个社区之间的横坐标差
     */
    public double getDistanceX(Community community) {
        return community.getLayoutX() - getLayoutX();
    }

    /**
     * 获取该社区和另一个社区之间的纵坐标差
     *
     * @param community 另一个社区
     * @return 该社区和另一个社区之间的纵坐标差
     */
    public double getDistanceY(Community community) {
        return community.getLayoutY() - getLayoutY();
    }

    /**
     * 查询社区是否被封锁
     *
     * @return 封锁状态
     */
    public boolean isLockDown() {
        return lockDown;
    }

    /**
     * 查询当前社区的群聚状态
     *
     * @return 群聚状态
     */
    public boolean isClustering() {
        return clustering;
    }

    /**
     * 设置当前社区的群聚状态
     *
     * @param clustering 群聚状态
     */
    public void setClustering(boolean clustering) {
        this.clustering = clustering;
    }
}
