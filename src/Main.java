import entity.City;
import entity.Community;
import entity.People;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 主类
 *
 * @author F5 (bsy)
 */
public class Main extends Application {

    /**
     * 主方法
     * <p>针对已经过时的IDE不能自动从上述start方法开始运行而设计</p>
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * JavaFX启动方法。
     * <p>本程序使用了JavaFX制作可视化界面模拟，并非使用命令行</p>
     *
     * @param primaryStage 主窗口
     * @throws Exception 相关异常
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("COVID-19 Simulator");
        primaryStage.setScene(new Scene(root, 850, 400));
        primaryStage.show();
    }
}
