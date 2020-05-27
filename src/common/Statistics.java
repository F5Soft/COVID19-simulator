package common;

import entity.People;

import java.util.ArrayList;
import java.util.Arrays;

public class Statistics {
    private static final int[] statistics = new int[5];
    private static double r0;

    public static void updateStatistics(ArrayList<People> people) {
        Arrays.fill(statistics, 0);
        for (People p : people) {
            statistics[p.getStatus()]++;
        }
    }

    public static int[] getStatistics() {
        return statistics;
    }
}
