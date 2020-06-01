package ui;

import common.Parameter;
import common.Statistics;
import entity.City;
import entity.People;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * 主窗口控制器
 *
 * @author F5 (bsy)
 */
public class Controller {
    /**
     * 当前模拟进行到的天数
     */
    private static int days = 0;
    /**
     * 城市视图
     */
    @FXML
    public City city;
    /**
     * 启动按钮
     */
    @FXML
    public Button startButton;
    /**
     * 暂停按钮
     */
    @FXML
    public Button toggleButton;
    /**
     * 统计面板
     */
    @FXML
    public Pane statisticsPane;
    /**
     * 统计面板天数记录
     */
    @FXML
    public Label daysLabel;
    /**
     * 传播概率设置文本框
     */
    @FXML
    public TextField transProbTextField;
    /**
     * 传播半径设置文本框
     */
    @FXML
    public TextField transRadiusTextField;
    /**
     * 死亡率设置文本框
     */
    @FXML
    public TextField deathProbTextField;
    /**
     * 统计面板更新动画
     */
    private Timeline statisticsLive;

    /**
     * 启动/重置模拟
     * <p>若当前处于启动状态，则点击效果为重置</p>
     *
     * @param actionEvent 触发事件
     */
    @FXML
    public void startSimulate(ActionEvent actionEvent) {
        if (startButton.getText().equals("Reset")) { // 重置当前模拟
            // 停止动画
            statisticsLive.stop();
            city.stop();
            statisticsPane.getChildren().clear();
            // 创建新的城市视图并取代现有的
            GridPane root = (GridPane) city.getParent();
            City newCity = new City();
            newCity.setId("city");
            newCity.setPrefWidth(550);
            newCity.setPrefHeight(340);
            root.getChildren().remove(city);
            root.add(newCity, 0, 0, 1, 2);
            city = newCity;
            // 更新界面和参数
            daysLabel.setText("0 Days");
            startButton.setText("Start");
            toggleButton.setDisable(true);
            days = 0;
            Parameter.setPlay(false);

        } else if (setParam()) { // 如果参数设置正确，开始模拟
            // 随机产生一个感染者
            ArrayList<People> people = city.getAllPeople();
            int rand = (int) (Math.random() * people.size());
            people.get(rand).setStatus(1);
            // 统计面板动画
            statisticsLive = new Timeline(new KeyFrame(Duration.millis(500), e -> {
                Statistics.updateStatistics(people);
                int[] statistics = Statistics.getStatistics();
                double sum = 0;
                for (int i = 0; i < 5; i++) {
                    sum += statistics[i];
                }
                double statisticsY = 0;
                double statisticsX = (days % 365) / 2.0 + 20;
                for (int i : new int[]{3, 0, 2, 1, 4}) {
                    Line line = new Line(statisticsX, statisticsY, statisticsX, statisticsY + statistics[i] / sum * 140);
                    line.setStroke(Color.web(Parameter.getStatusColor(i)));
                    statisticsY += statistics[i] / sum * 140;
                    statisticsPane.getChildren().add(line);
                }
                days++;
                if (days % 365 == 0) {
                    statisticsPane.getChildren().clear();
                }
                daysLabel.setText(days + " Days");
            }));
            statisticsLive.setCycleCount(-1);
            statisticsLive.play();
            city.play();
            // 更新界面和参数
            startButton.setText("Reset");
            toggleButton.setText("Pause");
            startButton.setDisable(true);
            toggleButton.setDisable(false);
            Parameter.setPlay(true);
        }
    }

    /**
     * 暂停/继续模拟
     *
     * @param actionEvent 触发事件
     */
    @FXML
    public void toggleSimulate(ActionEvent actionEvent) {
        if (Parameter.isPlay()) { // 继续模拟
            statisticsLive.stop();
            city.stop();
            toggleButton.setText("Resume");
            startButton.setDisable(false);
            Parameter.setPlay(false);
        } else { // 暂停模拟
            if (setParam()) {
                statisticsLive.play();
                city.play();
                toggleButton.setText("Pause");
                startButton.setDisable(true);
                Parameter.setPlay(true);
            }
        }
    }

    /**
     * 设置模拟模型参数
     *
     * @return 如果参数没有问题，返回true，参数存在问题则取消设置，返回false
     */
    private boolean setParam() {
        try {
            double basicTransmissionProb = Double.parseDouble(transProbTextField.getText());
            double transmissionRadius = Double.parseDouble(transRadiusTextField.getText());
            double deathProb = Double.parseDouble(deathProbTextField.getText());
            if (basicTransmissionProb < 0 || basicTransmissionProb > 1) {
                throw new Exception("Transmission probability should be 0 ~ 1!");
            }
            if (transmissionRadius < 0 || transmissionRadius > 20) {
                throw new Exception("Transmission radius should be 0 ~ 20");
            }
            if (deathProb < 0 || deathProb > 1) {
                throw new Exception("Death probability should be 0 ~ 1!");
            }
            Parameter.setBasicTransmissionProb(1, basicTransmissionProb * 0.1);
            Parameter.setBasicTransmissionProb(2, basicTransmissionProb);
            Parameter.setTransmissionRadius(transmissionRadius);
            Parameter.setDeathProb(deathProb);
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid params!");
            alert.setHeaderText(null);
            alert.setContentText("Please inout numeric value!");
            alert.showAndWait();
            return false;
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid params!");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
