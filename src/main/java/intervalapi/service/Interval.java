package intervalapi.service;

import java.util.*;

public abstract class Interval {
    double start;
    double end;
    boolean startInclusive;
    boolean endInclusive;

    public Interval(double start, double end, boolean startInclusive, boolean endInclusive) {
        this.start = start;
        this.end = end;
        this.startInclusive = startInclusive;
        this.endInclusive = endInclusive;
    }

    // Метод для проверки, содержит ли интервал число
    public boolean contains(double x) {
        if (x < start || x > end) return false;
        if (x == start) return startInclusive;
        if (x == end) return endInclusive;
        return true;
    }
}