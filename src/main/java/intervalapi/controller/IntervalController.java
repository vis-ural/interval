package intervalapi.controller;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import intervalapi.service.Interval;
import intervalapi.service.IntervalSet;
 
@RestController
@RequestMapping("/api")
public class IntervalController {

    public List<Interval> set;
    
    @PostMapping("/init")
    public void init(@RequestParam double x1, @RequestParam double x2) {
         set = new IntervalSet();
    }

     // Метод для добавления интервала вида (-∞, x1] U [x2, +∞)
    @PostMapping("/addInfiniteInterval")
    public void addInfiniteInterval(@RequestParam double x1, @RequestParam double x2) {
         set.addInfiniteInterval(x1, x2);
    }
    // Метод для добавления интервала вида [x1, x2]
    @PostMapping("/addFiniteInterval")
    public void addFiniteInterval(@RequestParam double x1, @RequestParam double x2) {
         set.addFiniteInterval(x1, x2);
    }

    // Метод для решения задачи 1
    @GetMapping("/findClosest")
    public Double findClosest(@RequestParam double x) {
        return set.findClosestInIntersection(x);

    }

    // Метод для решения задачи 2
    @GetMapping("/getIntersections")
    public List  getIntersections() {
        System.out.println("Пересечение интервалов:");
        return set.getIntersection();
    }


}