import java.util.*;

// Класс для представления интервала
class Interval {
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

// Основной класс API
public class IntervalSet {
    private List<Interval> intervals;

    public IntervalSet() {
        intervals = new ArrayList<>();
    }

    // Метод для добавления интервала вида (-∞, x1] U [x2, +∞)
    public void addInfiniteInterval(double x1, double x2) {
        intervals.add(new Interval(Double.NEGATIVE_INFINITY, x1, false, true));
        intervals.add(new Interval(x2, Double.POSITIVE_INFINITY, true, false));
    }

    // Метод для добавления интервала вида [x1, x2]
    public void addFiniteInterval(double x1, double x2) {
        intervals.add(new Interval(x1, x2, true, true));
    }

    // Метод для решения задачи 1
    public double findClosestInIntersection(double x) {
        double result = x;
        double minDistance = Double.POSITIVE_INFINITY;
        boolean inAllIntervals = true;

        for (Interval interval : intervals) {
            if (!interval.contains(x)) {
                inAllIntervals = false;
                double closestPoint = (x < interval.start) ? interval.start : interval.end;
                double distance = Math.abs(x - closestPoint);
                if (distance < minDistance) {
                    minDistance = distance;
                    result = closestPoint;
                }
            }
        }

        return inAllIntervals ? x : result;
    }

    // Метод для решения задачи 2
    public List<Interval> getIntersection() {
        if (intervals.isEmpty()) return new ArrayList<>();

        List<Double> points = new ArrayList<>();
        for (Interval interval : intervals) {
            points.add(interval.start);
            points.add(interval.end);
        }
        Collections.sort(points);

        List<Interval> result = new ArrayList<>();
        for (int i = 0; i < points.size() - 1; i++) {
            double start = points.get(i);
            double end = points.get(i + 1);
            if (isInAllIntervals((start + end) / 2)) {
                boolean startInclusive = isInAllIntervals(start);
                boolean endInclusive = isInAllIntervals(end);
                result.add(new Interval(start, end, startInclusive, endInclusive));
            }
        }

        return result;
    }

    private boolean isInAllIntervals(double x) {
        for (Interval interval : intervals) {
            if (!interval.contains(x)) return false;
        }
        return true;
    }

    // Вспомогательный метод для вывода результатов
    public static void printIntervals(List<Interval> intervals) {
        for (Interval interval : intervals) {
            System.out.println((interval.startInclusive ? "[" : "(") +
                    interval.start + ", " + interval.end +
                    (interval.endInclusive ? "]" : ")"));
        }
    }

    // Пример использования
    public static void main(String[] args) {
        IntervalSet set = new IntervalSet();
        set.addInfiniteInterval(1, 5);
        set.addFiniteInterval(2, 4);
        set.addFiniteInterval(3, 6);

        System.out.println("Ближайшее число к 0 в пересечении: " + set.findClosestInIntersection(0));
        System.out.println("Ближайшее число к 3.5 в пересечении: " + set.findClosestInIntersection(3.5));

        System.out.println("Пересечение интервалов:");
        printIntervals(set.getIntersection());
    }
}