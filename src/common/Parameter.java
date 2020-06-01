package common;

/**
 * 模型参数静态类
 *
 * @author F5 (bsy)
 */
public class Parameter {
    /**
     * 不同感染状态下的传播概率
     */
    private static final double[] basicTransmissionProb = {0, 0.05, 0.2, 0, 0};
    /**
     * 不同感染状态下的免疫能力
     */
    private static final double[] basicImmunity = {0, 1, 1, 1, 1};
    /**
     * 不同感染状态下对应的颜色
     */
    private static final String[] statusColor = {"#4ec9b0", "#dcdcaa", "#ce9178", "#e7e7e7", "#3c3c3c"};
    /**
     * 模型是否处于模拟状态
     */
    private static boolean play;
    /**
     * 病毒所能造成传播的半径
     */
    private static double transmissionRadius = 8;
    /**
     * 病毒死亡率
     */
    private static double deathProb = 0.03;

    /**
     * 获取指定状态下的传播概率
     *
     * @param status 指定状态
     * @return 该状态的传播概率
     */
    public static double getBasicTransmissionProb(int status) {
        return basicTransmissionProb[status];
    }

    /**
     * 设置指定状态下的传播概率
     *
     * @param status           指定状态
     * @param transmissionProb 该状态的传播概率
     */
    public static void setBasicTransmissionProb(int status, double transmissionProb) {
        basicTransmissionProb[status] = transmissionProb;
    }

    /**
     * 获取指定状态下的免疫力
     *
     * @param status 指定状态
     * @return 该状态的免疫力
     */
    public static double getBasicImmunity(int status) {
        return basicImmunity[status];
    }

    /**
     * 获取指定状态的代表颜色
     *
     * @param status 指定状态
     * @return 十六进制web颜色字符串
     */
    public static String getStatusColor(int status) {
        return statusColor[status];
    }

    /**
     * 获取病毒传播半径
     *
     * @return 传播半径
     */
    public static double getTransmissionRadius() {
        return transmissionRadius;
    }

    /**
     * 设置病毒传播半径
     *
     * @param transmissionRadius 传播半径
     */
    public static void setTransmissionRadius(double transmissionRadius) {
        Parameter.transmissionRadius = transmissionRadius;
    }

    /**
     * 获取病毒的死亡概率
     *
     * @return 死亡概率
     */
    public static double getDeathProb() {
        return deathProb;
    }

    /**
     * 设置病毒的死亡概率
     *
     * @param deathProb 死亡概率
     */
    public static void setDeathProb(double deathProb) {
        Parameter.deathProb = deathProb;
    }

    /**
     * 查询当前是否处于模拟状态
     *
     * @return 是否处于模拟状态
     */
    public static boolean isPlay() {
        return play;
    }

    /**
     * 设置当前是否处于模拟状态
     *
     * @param play 是否处于模拟状态
     */
    public static void setPlay(boolean play) {
        Parameter.play = play;
    }
}
