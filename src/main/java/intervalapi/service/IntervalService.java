package intervalapi.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class IntervalService {

    private final List<Interval> intervalList = new ArrayList<>();

    /**
     * Просмотр списка
     *
     * @return String
     */
    public List<Interval> list() {
        try {
            System.out.println(intervalList.toString());
            return intervalList;
        } catch (Exception e) {
            System.err.println("Ошибка при получении списка интервалов: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Interval> clear() {
        try {
            intervalList.clear();
            System.out.println(intervalList.toString());
            return intervalList;
        } catch (Exception e) {
            System.err.println("Ошибка при очистке списка интервалов: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public int getCount() {
        try {
            System.out.println(intervalList.toString());
            return intervalList.size();
        } catch (Exception e) {
            System.err.println("Ошибка при получении количества интервалов: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Добавление закрытого интервала
     *
     * @param x1 Double
     * @param x2 Double
     * @return
     */
    public List<Interval> addClosedInterval(double x1, double x2) {
        try {
            intervalList.add(new ClosedInterval(x1, x2));
            return intervalList;
        } catch (IllegalArgumentException e) {
            System.err.println("Некорректные границы интервала: " + e.getMessage());
            return intervalList;
        } catch (Exception e) {
            System.err.println("Ошибка при добавлении закрытого интервала: " + e.getMessage());
            return intervalList;
        }
    }

    /**
     * Добавление открытого интервала
     *
     * @param x1 Double
     * @param x2 Double
     * @return
     */
    public List<Interval> addOpenInterval(double x1, double x2) {
        try {
            intervalList.add((Interval) new OpenInterval(x1, x2));
            return intervalList;
        } catch (IllegalArgumentException e) {
            System.err.println("Некорректные границы интервала: " + e.getMessage());
            return intervalList;
        } catch (Exception e) {
            System.err.println("Ошибка при добавлении открытого интервала: " + e.getMessage());
            return intervalList;
        }
    }

    /**
     * Пересечение подмножеств
     *
     * @return List<Interval>
     */
    public List<Interval> getIntersectionIntervals() {
        try {
            List<double[]> points = new ArrayList<>();
            double inf = Double.POSITIVE_INFINITY;
            for (Interval interval : intervalList) {
                if (interval instanceof ClosedInterval) {
                    ClosedInterval ci = (ClosedInterval) interval;
                    points.add(new double[]{ci.getX1(), ci.getX2()});
                } else if (interval instanceof OpenInterval) {
                    OpenInterval oi = (OpenInterval) interval;
                    points.add(new double[]{inf * -1, oi.getX1()});
                    points.add(new double[]{oi.getX2(), inf});
                }
            }
            points.sort(Comparator.comparingDouble(a -> a[0]));
            List<Interval> result = new ArrayList<>();
            double start = -inf;
            double end = inf;
            for (double[] point : points) {
                if (point[0] > end) {
                    result.add(new ClosedInterval(start, end));
                    start = point[0];
                    end = point[1];
                } else {
                    start = point[0];
                    end = Math.min(end, point[1]);
                }
            }
            result.add(new ClosedInterval(start, end));
            return result;
        } catch (Exception e) {
            System.err.println("Ошибка при нахождении пересечений интервалов: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Метод для нахождения ближайшего числа в пересечении интервалов
     *
     * @param x Double
     * @return Double
     */
    public Double findClosest(double x) {
        try {
            List<Interval> intersections = getIntersectionIntervals();
            // Собираем концы всех пересекающихся интервалов
            List<Double> points = new ArrayList<>();

            for (Interval interval : intersections) {

                // проверим, не в диапазоне ли мы
                if (interval.contains(x)){
                    return x;
                }

                if (interval instanceof ClosedInterval) {
                    ClosedInterval ci = (ClosedInterval) interval;
                    points.add(ci.getX1());
                    points.add(ci.getX2());
                } else if (interval instanceof OpenInterval) {
                    OpenInterval oi = (OpenInterval) interval;
                    points.add(oi.getX1());
                    points.add(oi.getX2());
                }
            }
            Collections.sort(points);

            return binarySearchClosest(points, x);
        } catch (Exception e) {
            System.err.println("Ошибка при поиске ближайшего значения: " + e.getMessage());
            return null;
        }
    }

    /**
     * Реализация бинарного поиска для нахождения ближайшей точки
     *
     * @param points List<Double>
     * @param target Double
     * @return Double
     */
    private Double binarySearchClosest(List<Double> points, double target) {
        double inf = Double.POSITIVE_INFINITY;
        int low = 0;
        int high = points.size() - 1;
        try {
            // Проверка на крайние случаи
            if (target <= points.get(low)) {
                return points.get(low);
            }
            if (target >= points.get(high)) {
                return points.get(high);
            }

            // Инициализация переменной для хранения ближайшего значения
            Double closest = null;
            Double minDiff = inf;
            // Основной цикл бинарного поиска
            while (low <= high) {
                // Вычисляем средний индекс
                int mid = low + (high - low) / 2;
                Double midValue = points.get(mid);
                // Если нашли точное совпадение, возвращаем его
                if (midValue.equals(target)) {
                    return midValue;
                }
                // Обновляем ближайшее значение, если найдено более близкое
                double diff = Math.abs(midValue - target);
                if (closest == null || diff < minDiff) {
                    closest = midValue;
                    minDiff = diff;
                }
                // Сужаем диапазон поиска
                if (midValue < target) {
                    low = mid + 1; // Ищем в правой половине
                } else {
                    high = mid - 1; // Ищем в левой половине
                }
            }
            // Проверка ближайших значений после завершения цикла
            if (low < points.size()) {
                Double lowValue = points.get(low);
                if (Math.abs(lowValue - target) < Math.abs(closest - target)) {
                    closest = lowValue;
                }
            }
            if (high >= 0) {
                Double highValue = points.get(high);
                if (Math.abs(highValue - target) < Math.abs(closest - target)) {
                    closest = highValue;
                }
            }
            return closest; // Возвращаем ближайшее значение
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Ошибка при доступе к элементам списка: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Ошибка при бинарном поиске: " + e.getMessage());
            return null;
        }
    }
}
