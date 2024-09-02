package intervalapi.service;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Interval {
    double start;
    double end;
    boolean startInclusive; // true, если начало включительно, иначе - открыто
    boolean endInclusive;   // true, если конец включительно, иначе - открыто


    /**
     * @param start double
     * @param end double
     * @param startInclusive boolean
     * @param endInclusive boolean
     */
    public Interval(double start, double end, boolean startInclusive, boolean endInclusive) {
        this.start = start;
        this.end = end;
        this.startInclusive = startInclusive;
        this.endInclusive = endInclusive;
    }

    public Interval() {
    }

    boolean isEmpty() {
        return start > end;
    }

    @Override
    public String toString() {
        if (Double.isInfinite(start) && Double.isInfinite(end)) {
            return "(-∞, +∞)";
        } else if (Double.isInfinite(start)) {
            return "(-∞, " + end + "]";
        } else if (Double.isInfinite(end)) {
            return "[" + start + ", +∞)";
        } else {
            return "[" + start + ", " + end + "]";
        }
    }

    /**
     * @param a
     * @param b
     * @return
     */
    static Interval intersect(Interval a, Interval b) {
        double newStart = Math.max(a.start, b.start);
        double newEnd = Math.min(a.end, b.end);

        boolean newStartInclusive = false;
        boolean newEndInclusive = false;

        // Определение новой включенности начала интервала
        if (newStart == a.start && newStart == b.start) {
            newStartInclusive = a.startInclusive && b.startInclusive;
        } else if (newStart == a.start) {
            newStartInclusive = a.startInclusive;
        } else if (newStart == b.start) {
            newStartInclusive = b.startInclusive;
        }

        // Определение новой включенности конца интервала
        if (newEnd == a.end && newEnd == b.end) {
            newEndInclusive = a.endInclusive && b.endInclusive;
        } else if (newEnd == a.end) {
            newEndInclusive = a.endInclusive;
        } else if (newEnd == b.end) {
            newEndInclusive = b.endInclusive;
        }

        // Проверка на пустое пересечение
        if (newStart > newEnd || (newStart == newEnd && (!newStartInclusive || !newEndInclusive))) {
            return null;  // Возвращаем null вместо пустого интервала
        }

        return new Interval(newStart, newEnd, newStartInclusive, newEndInclusive);
    }


    /**
     * @param intervalsA
     * @param intervalsB
     * @return
     */
    static List<Interval> intersect(List<Interval> intervalsA, List<Interval> intervalsB) {
        List<Interval> result = new ArrayList<>();

        if (intervalsA.isEmpty() || intervalsB.isEmpty()) {
            return result;
        }

        for (Interval a : intervalsA) {
            for (Interval b : intervalsB) {
                Interval intersection = Interval.intersect(a, b);
                if (intersection != null && !intersection.isEmpty()) {
                    result.add(intersection);
                }
            }
        }

        return result;
    }


    static List<Interval> intersectMultiple(List<List<Interval>> intervalGroups) {
        if (intervalGroups.isEmpty()) return new ArrayList<>();

        List<Interval> result = intervalGroups.get(0);
        for (int i = 1; i < intervalGroups.size(); i++) {
            result = intersect(result, intervalGroups.get(i));
            if (result.isEmpty()) break; // Если результат пустой, дальнейшие пересечения бессмысленны
        }
        return result;
    }


    @JsonProperty("start")
    public double getStart() {
        return start;
    }

    @JsonProperty("end")
    public double getEnd() {
        return end;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public void setEnd(double end) {
        this.end = end;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interval interval = (Interval) o;
        return Double.compare(interval.start, start) == 0 &&
                Double.compare(interval.end, end) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }


    public boolean contains(double x) {
        boolean startCheck = startInclusive ? x >= start : x > start;
        boolean endCheck = endInclusive ? x <= end : x < end;
        return startCheck && endCheck;
    }


}