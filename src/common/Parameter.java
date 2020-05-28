package common;

public class Parameter {
    /**
     * R0 without any control measures
     * <p>number source: wikipedia</p>
     */
    private static final double[] basicTransmissionProb = {0, 0.05, 0.2, 0, 0};
    private static final double[] basicImmunity = {0, 1, 1, 0.96, 1};
    private static final String[] statusColor = {"#4ec9b0", "#dcdcaa", "#ce9178", "#e7e7e7", "#3c3c3c"};

    private static double deathRate = 0.03;
    private static double clusterRate = 0.6;

    public static double getBasicTransmissionProb(int status) {
        return basicTransmissionProb[status];
    }

    public static void setBasicTransmissionProb(int status, double reproduction) {
        basicImmunity[status] = reproduction;
    }

    public static double getBasicImmunity(int status) {
        return basicImmunity[status];
    }

    public static void setBasicImmunity(int status, double immunity) {
        basicImmunity[status] = immunity;
    }

    public static String getStatusColor(int status) {
        return statusColor[status];
    }

    public static double getDeathRate() {
        return deathRate;
    }

    public static double getClusterRate() {
        return clusterRate;
    }

    public static void setClusterRate(double clusterRate) {
        Parameter.clusterRate = clusterRate;
    }

    public static double getChanceReduceWithMask() {
        return Math.sqrt(1 - clusterRate);
    }
}
