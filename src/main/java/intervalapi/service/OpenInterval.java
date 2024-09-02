package intervalapi.service;

public class OpenInterval extends Interval {
    public OpenInterval(double start, double end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean contains(double x) {
        return x > start && x < end;
    }
}
