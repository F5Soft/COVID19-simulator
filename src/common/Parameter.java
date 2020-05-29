package common;

public class Parameter {

    private static final double[] basicTransmissionProb = {0, 0.05, 0.2, 0, 0};
    private static final double[] basicImmunity = {0, 1, 1, 1, 1};
    private static final String[] statusColor = {"#4ec9b0", "#dcdcaa", "#ce9178", "#e7e7e7", "#3c3c3c"};

    private static double transmissionRadius = 8;
    private static double deathProb = 0.03;

    public static double getBasicTransmissionProb(int status) {
        return basicTransmissionProb[status];
    }

    public static void setBasicTransmissionProb(int status, double transmissionProb) {
        basicTransmissionProb[status] = transmissionProb;
    }

    public static double getBasicImmunity(int status) {
        return basicImmunity[status];
    }

    public static String getStatusColor(int status) {
        return statusColor[status];
    }

    public static double getTransmissionRadius() {
        return transmissionRadius;
    }

    public static void setTransmissionRadius(double transmissionRadius) {
        Parameter.transmissionRadius = transmissionRadius;
    }

    public static double getDeathProb() {
        return deathProb;
    }

    public static void setDeathProb(double deathProb) {
        Parameter.deathProb = deathProb;
    }
}
