package intervalapi.controller;

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
    private IntervalService intervalService;

    /**
     * Добавление закрытого интервала
     *
     * @param x1 Double
     * @param x2 Double
     */
    @PostMapping("/addClosedInterval")
    public void addClosedInterval(@RequestParam double x1, @RequestParam double x2) {
        if (intervalService.getCount() < 10) {
            intervalService.addClosedInterval(x1, x2);
        } else
            System.out.println("Error max limit 10 elements");

    }

    /**
     * Добавление открытого интервала
     *
     * @param x1 Double
     * @param x2 Double
     */
    @PostMapping("/addOpenInterval")
    public void addOpenInterval(@RequestParam double x1, @RequestParam double x2) {
        if (intervalService.getCount() < 10) {
            intervalService.addOpenInterval(x1, x2);
        } else
            System.out.println("Error max limit 10 elements");


    }

    /**
     * Пересечение подмножеств
     *
     * @return List<Interval>
     */
    @GetMapping("/getIntersections")
    public List<intervalapi.service.Interval> getIntersections() {
        return intervalService.getIntersectionIntervals();
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
        return intervalService.findClosest(x);
    }

    /**
     * Просмотр текущего списка
     */
    @GetMapping("/list")
    public void list() {
        intervalService.list();
    }

    /**
     * Очистка текущего списка
     */
    @GetMapping("/clear")
    public void clear() {
        intervalService.clear();
    }

}