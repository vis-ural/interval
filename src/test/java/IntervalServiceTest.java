import static org.junit.jupiter.api.Assertions.assertEquals;

import intervalapi.service.Interval;
import intervalapi.service.IntervalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNull;


import java.util.List;

public class IntervalServiceTest {

    private IntervalService intervalService;

    @BeforeEach
    public void setUp() {
        intervalService = new IntervalService();
    }

    @Test
    public void testExample1() {
        intervalService.clear();
        intervalService.addOpenInterval(5.0, 10.0);
        intervalService.addCloseInterval(1.0, 15.0);

        List<Interval> intersections = intervalService.findIntersections();

        assertEquals(2, intersections.size());
        assertEquals(new Interval(1.0, 5.0,true, true), intersections.get(0));
        assertEquals(new Interval(10.0, 15.0, true, true), intersections.get(1));
        System.out.println("testExample1");
        System.out.println(intersections);

    }

    @Test
    public void testExample2() {
        intervalService.clear();
        intervalService.addOpenInterval(5.0, 10.0);
        intervalService.addOpenInterval(1.0, 7.0);

        List<Interval> intersections = intervalService.findIntersections();
        System.out.println("testExample2");
        System.out.println(intersections);
        assertEquals(2, intersections.size());
        assertEquals(new Interval(Double.NEGATIVE_INFINITY, 1.0, false, true), intersections.get(0));
        assertEquals(new Interval(10.0, Double.POSITIVE_INFINITY, true, false), intersections.get(1));

    }

    @Test
    public void testExample3() {
        intervalService.clear();
        intervalService.addCloseInterval(10.0, 20.0);
        intervalService.addCloseInterval(18.0, 28.0);
        intervalService.addCloseInterval(25.0, 35.0);

        List<Interval> intersections = intervalService.findIntersections();
        System.out.println("testExample3");
        System.out.println(intersections);
        assertEquals(0, intersections.size());

    }

    @Test
    public void testExample4() {
        intervalService.clear();
        intervalService.addOpenInterval(5.0, 10.0);
        intervalService.addCloseInterval(1.0, 15.0);

        List<Interval> intersections = intervalService.findIntersections();
        System.out.println("testExample4");
        System.out.println(intersections);
        assertEquals(2, intersections.size());
        assertEquals(new Interval(1.0, 5.0, true, true), intersections.get(0));
        assertEquals(new Interval(10.0, 15.0, true, true), intersections.get(1));

    }


    /* Поиск ближайших значений */

    @Test
    public void testExample5() {
        intervalService.clear();
        intervalService.addCloseInterval(1.0, 5.0);
        intervalService.addCloseInterval(3.0, 8.0);

        Double result = intervalService.findClosest(4.0);
        System.out.println("testExample5 expected 4");
        System.out.println(intervalService.intervalList);
        List<Interval> intersections = intervalService.findIntersections();
        System.out.println(intersections);
        System.out.println(result);
        assertEquals(4.0, result);

    }

    @Test
    public void testExample6() {
        intervalService.clear();
        intervalService.addCloseInterval(1.0, 4.0);
        intervalService.addCloseInterval(2.0, 6.0);

        Double result = intervalService.findClosest(5.0);
        System.out.println("testExample6 expected 4");
        System.out.println(intervalService.intervalList);
        List<Interval> intersections = intervalService.findIntersections();
        System.out.println(intersections);
        System.out.println(result);
        assertEquals(4.0, result); // Ближайшее значение к 5.0 в пересечении - 4.0

    }

    @Test
    public void testExample7() {
        intervalService.clear();
        intervalService.addCloseInterval(10.0, 20.0);
        intervalService.addCloseInterval(15.0, 25.0);

        Double result = intervalService.findClosest(18.0);
        System.out.println("testExample7 expected 18");
        System.out.println(intervalService.intervalList);
        List<Interval> intersections = intervalService.findIntersections();
        System.out.println(intersections);
        System.out.println(result);
        assertEquals(18.0, result); // 18.0 принадлежит пересечению

    }

    @Test
    public void testExample8() {
        intervalService.clear();
        intervalService.addCloseInterval(0.0, 5.0);
        intervalService.addCloseInterval(8.0, 10.0);

        Double result = intervalService.findClosest(7.0);
        System.out.println("testExample8 expected null");
        System.out.println(intervalService.intervalList);
        List<Interval> intersections = intervalService.findIntersections();
        System.out.println(intersections);
        System.out.println(result);
        assertNull(result); // Пересечения нет, должно вернуться null


    }

    @Test
    public void testExample9() {
        intervalService.clear();

        intervalService.addCloseInterval(Double.NEGATIVE_INFINITY, 3.0);
        intervalService.addCloseInterval(1.0, Double.POSITIVE_INFINITY);
        Double result = intervalService.findClosest(0.0);
        System.out.println("testExample9 expected 1");
        System.out.println(intervalService.intervalList);
        List<Interval> intersections = intervalService.findIntersections();
        System.out.println(intersections);
        System.out.println(result);
        assertEquals(1.0, result); // Ближайшее значение к 0.0 в пересечении - 1.0


    }

    @Test
    public void testExample10() {
        intervalService.clear();
        intervalService.addCloseInterval(10.0, 20.0);
        intervalService.addCloseInterval(5.0, 15.0);

        Double result = intervalService.findClosest(16.0);
        System.out.println("testExample10 expected 15");
        System.out.println(intervalService.intervalList);
        List<Interval> intersections = intervalService.findIntersections();
        System.out.println(intersections);
        System.out.println(result);
        assertEquals(15.0, result); // Ближайшее значение к 16.0 в пересечении - 15.0

    }


}
