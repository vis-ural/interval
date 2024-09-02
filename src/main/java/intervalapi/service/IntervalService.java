package intervalapi.service;


import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class IntervalService {

    public final List<List<Interval>> intervalList =  new ArrayList<>();


    /**
     * Просмотр списка
     *
     * @return String
     */
    public List<List<Interval>> list() {
        try {
            System.out.println(intervalList);
            return intervalList;
        } catch (Exception e) {
            System.err.println("Ошибка при получении списка интервалов: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Очистка списка
     */
    public void clear() {
        try {
            intervalList.clear();

        } catch (Exception e) {
            System.err.println("Ошибка при очистке списка интервалов: " + e.getMessage());
             
        }
    }

    /**
     * getCount
     * @return
     */
    public int getCount() {
        try {
            System.out.println(intervalList);
            return intervalList.size();
        } catch (Exception e) {
            System.err.println("Ошибка при получении количества интервалов: " + e.getMessage());
            return 0;
        }
    }



    /**
     *  Пересечение подмножеств
     * @return
     */
    public List<Interval> findIntersections(){
        return Interval.intersectMultiple(intervalList);
    }


    /**
     * addOpenInterval
     * @param x1
     * @param x2
     * @return
     */
    public List<List<Interval>> addOpenInterval(Double x1, Double x2){
        List<Interval> intervals = new ArrayList<>();
        intervals.add(new Interval(Double.NEGATIVE_INFINITY, x1, false, true));
        intervals.add(new Interval(x2, Double.POSITIVE_INFINITY, true, false));
        intervalList.add(intervals);
        return intervalList;
    }

    /**
     * addCloseInterval
     * @param x1
     * @param x2
     * @return
     */
    public List<List<Interval>> addCloseInterval(Double x1, Double x2){
        List<Interval> intervals = new ArrayList<>();
        intervals.add(new Interval(x1, x2, true, true));
        intervalList.add(intervals);
        return intervalList;
    }

    public Double findClosest(double x) {
        List<Interval> intersections = findIntersections();

        // Если пересечений нет, возвращаем null
        if (intersections.isEmpty()) {
            return null;
        }

        List<Double> points = new ArrayList<>();

        for (Interval interval : intersections) {
            // Если x внутри интервала, возвращаем x
            if (interval.contains(x)) {
                return x;
            }

            // Добавляем границы интервалов
            if (!Double.isInfinite(interval.getStart())) {
                points.add(interval.getStart());
            }
            if (!Double.isInfinite(interval.getEnd())) {
                points.add(interval.getEnd());
            }
        }

        // Если список точек пуст, возвращаем null
        if (points.isEmpty()) {
            return null;
        }

        // Сортируем точки
        Collections.sort(points);

        // Ищем ближайшее значение
        return binarySearchClosest(points, x);
    }

    private Double binarySearchClosest(List<Double> points, double x) {
        if (points.isEmpty()) {
            return null;
        }

        int low = 0;
        int high = points.size() - 1;

        if (x <= points.get(low)) {
            return points.get(low);
        }
        if (x >= points.get(high)) {
            return points.get(high);
        }

        while (low <= high) {
            int mid = (low + high) / 2;
            double midVal = points.get(mid);

            if (midVal < x) {
                low = mid + 1;
            } else if (midVal > x) {
                high = mid - 1;
            } else {
                return midVal;
            }
        }

        // Определяем ближайшее значение
        double lowDiff = Math.abs(points.get(low) - x);
        double highDiff = Math.abs(points.get(high) - x);

        return lowDiff < highDiff ? points.get(low) : points.get(high);
    }



}

