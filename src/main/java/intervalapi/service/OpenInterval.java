package intervalapi.service;



public class OpenInterval implements Interval {
    private final double x1;
    private final double x2;

    /**
     * Add openInterval
     * @param x1 Double
     * @param x2 Double
     */
    public OpenInterval(double x1, double x2) {
        System.out.println("OpenInterval");
        this.x1 = x1;
        this.x2 = x2;
    }

    /**
     * get point interval
     * @param x Double
     * @return boolean
     */
    @Override
    public boolean contains(double x) {
        return x <= x1 || x >= x2;
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    @Override
    public String toString() {
        return "(-∞, " + x1 + "] ∪ [" + x2 + ", +∞)";
    }
}
