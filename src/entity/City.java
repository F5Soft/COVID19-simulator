package entity;

import common.Parameter;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * 城市实体
 * <p>既是城市模型，也是窗口中的城市视图</p>
 *
 * @author F5 (bsy)
 */
public class City extends Pane {
    /**
     * 人在各个社区间旅行的动画
     */
    private final Timeline peopleTravel;

    /**
     * 城市实体构造函数
     */
    public City() {
        // 添加15个社区，每个社区48人
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                Community community = new Community(110 * j + 10, 110 * i + 10);
                for (int k = 0; k < 48; k++) {
                    People people = new People();
                    community.addPeople(people);
                }
                addCommunity(community);
            }
        }
        // 人在各个社区间旅行的动画
        peopleTravel = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            ArrayList<Community> communities = getCommunities();
            Community community1 = communities.get((int) (Math.random() * communities.size()));
            Community community2 = communities.get((int) (Math.random() * communities.size()));
            if (community1.isLockDown() || community2.isLockDown() || community1 == community2) {
                return;
            }
            try {
                People people = community1.getPeople().get((int) (Math.random() * community1.getPeople().size()));
                if (people.getStatus() == 4 || people.isOnTravel()) {
                    return;
                }
                people.setOnTravel(true);
                people.stop();
                people.setVisible(false);
                community1.removePeople(people);
                community2.addPeople(people);
                PathTransition pathTransition = new PathTransition();
                pathTransition.setNode(people);
                pathTransition.setCycleCount(1);
                pathTransition.setDuration(Duration.millis(1000));
                Line line = new Line(community2.getDistanceX(community1), community2.getDistanceY(community1), 0, 0);
                pathTransition.setPath(line);
                people.setVisible(true);
                pathTransition.play();
                pathTransition.setOnFinished(ee -> {
                    if (Parameter.isPlay()) {
                        people.play();
                    }
                    people.setOnTravel(false);
                });
            } catch (IndexOutOfBoundsException ignored) {
            }
        }));
        peopleTravel.setCycleCount(-1);
        // 当被点击时，根据所处位置设置社区的状态
        // 这里没有在各个社区上绑定该事件，因为实验发现多个社区同时存在时，绑定可能造成错误的触发，可能是JavaFX自身问题
        setOnMouseClicked(e -> {
            double x = e.getSceneX() - getLayoutX();
            double y = e.getSceneY() - getLayoutY();
            int col = (int) ((x - 10) / 110);
            int row = (int) ((y - 10) / 110);
            if (e.getButton() == MouseButton.PRIMARY) {
                getCommunities().get(5 * row + col).peopleCluster();
            } else if (e.getButton() == MouseButton.SECONDARY) {
                getCommunities().get(5 * row + col).toggleLockDown();
            }
        });
    }

    /**
     * 开始模拟城市
     */
    public void play() {
        peopleTravel.play();
        for (Community community : getCommunities()) {
            community.play();
        }
    }

    /**
     * 停止模拟城市
     */
    public void stop() {
        peopleTravel.stop();
        for (Community community : getCommunities()) {
            community.stop();
        }
    }

    /**
     * 向城市添加一个社区
     * @param community 待添加的社区
     */
    public void addCommunity(Community community) {
        getChildren().add(community);
    }

    /**
     * 取得城市中的所有社区
     * @return 城市中所有社区的ArrayList
     */
    public ArrayList<Community> getCommunities() {
        ArrayList<Community> communities = new ArrayList<>();
        for (Node node : getChildren()) {
            communities.add((Community) node);
        }
        return communities;
    }

    /**
     * 取得城市中的所有人
     * @return 城市中所有人的ArrayList
     */
    public ArrayList<People> getAllPeople() {
        ArrayList<People> people = new ArrayList<>();
        for (Community community : getCommunities()) {
            people.addAll(community.getPeople());
        }
        return people;
    }
}
