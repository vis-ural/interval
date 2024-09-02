package intervalapi.controller;

import intervalapi.service.Interval;
import intervalapi.service.IntervalService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/api")
public class IntervalController {

    @Resource
    @Qualifier("intervalService")
    private final IntervalService intervalService = new IntervalService();

    /**
     * Добавление закрытого интервала
     *
     * @param x1 Double
     * @param x2 Double
     */
    @PostMapping("/addClosedInterval")
    public List<List<Interval>> addClosedInterval(@RequestParam double x1, @RequestParam double x2) {
        if (intervalService.getCount() < 10) {
            return intervalService.addCloseInterval(x1, x2);
        } else
            System.out.println("Error max limit 10 elements");
        return List.of();
    }

    /**
     * Добавление открытого интервала
     *
     * @param x1 Double
     * @param x2 Double
     */
    @PostMapping("/addOpenInterval")
    public List<List<Interval>> addOpenInterval(@RequestParam double x1, @RequestParam double x2) {
        if (intervalService.getCount() < 10) {
            return  intervalService.addOpenInterval(x1, x2);
        } else
            System.out.println("Error max limit 10 elements");
        return List.of();

    }

    /**
     * Пересечение подмножеств
     *
     * @return List<Interval>
     */
    @GetMapping("/getIntersections")
    public List<Interval> getIntersections() {
        System.out.println(intervalService.findIntersections());
        return intervalService.findIntersections();
    }

    /**
     * Число принадлежащее
     * пересечению подмножеств
     *
     * @param x Double
     * @return Double
     */
    @GetMapping("/findClosest")
    public Double findClosest(@RequestParam double x) {
        System.out.println(intervalService.findClosest(x));
        return intervalService.findClosest(x);
    }

    /**
     * Просмотр текущего списка
     *
     * @return String
     */
    @GetMapping("/list")
    public List<List<Interval>>  list() {
        return intervalService.list();
    }

    /**
     * Очистка текущего списка
     */
    @GetMapping("/clear")
    public void clear() {
        intervalService.clear();
    }

}