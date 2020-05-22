package Common;

public class Parameter {
    /**
     * R0 without any control measures
     * <p>number source: wikipedia</p>
     */
    private static final double[] basicReproduction = {0, 0.28, 2.79, 0};
    private static final double[] basicImmunity = {0, 1, 1, 0.8};
    /**
     * The scale of infections reduced by
     */
    private static double maskEffect = 0.75;

    public static double getBasicReproduction(int status) {
        return basicReproduction[status];
    }

    public static void setBasicReproduction(int status, double reproduction){
        basicImmunity[status] = reproduction;
    }

    public static double getBasicImmunity(int status) {
        return basicImmunity[status];
    }

    public static void setBasicImmunity(int status, double immunity){
        basicImmunity[status] = immunity;
    }

    public static double getMaskEffect() {
        return maskEffect;
    }

    public static void setMaskEffect(double maskEffect) {
        Parameter.maskEffect = maskEffect;
    }

    public static double getChanceReduceWithMask() {
        return Math.sqrt(1 - maskEffect);
    }
}
