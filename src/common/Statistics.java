package common;

import entity.People;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 统计数据静态类
 * <p>统计模型在模拟过程中的感染人数数据</p>
 *
 * @author F5 (bsy)
 */
public class Statistics {
    /**
     * 各感染状态人数统计数据
     */
    private static final int[] statistics = new int[5];

    /**
     * 根据提供的当前所有人列表更新当前统计数据
     *
     * @param people 当前所有人人列表
     */
    public static void updateStatistics(ArrayList<People> people) {
        Arrays.fill(statistics, 0);
        for (People p : people) {
            statistics[p.getStatus()]++;
        }
    }

    /**
     * 查询当前统计数据
     *
     * @return 当前统计数据
     */
    public static int[] getStatistics() {
        return statistics;
    }
}
