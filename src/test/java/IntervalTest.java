import intervalapi.service.ClosedInterval;
import intervalapi.service.Interval;
import intervalapi.service.IntervalService;
import intervalapi.service.OpenInterval;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.*;

public class IntervalTest {

    private IntervalService intervalService;

    @BeforeEach
    public void setUp() {
        intervalService = new IntervalService();
    }

    @Test
    public void testAddClosedInterval() {
        intervalService.addClosedInterval(1.0, 5.0);
        List<Interval> intervals = intervalService.list();
        assertEquals(1, intervals.size());
        assertTrue(intervals.get(0) instanceof ClosedInterval);
        ClosedInterval interval = (ClosedInterval) intervals.get(0);
        assertEquals(1.0, interval.getX1());
        assertEquals(5.0, interval.getX2());
    }

    @Test
    public void testAddOpenInterval() {
        intervalService.addOpenInterval(2.0, 6.0);
        List<Interval> intervals = intervalService.list();
        assertEquals(1, intervals.size());
        assertTrue(intervals.get(0) instanceof OpenInterval);
        OpenInterval interval = (OpenInterval) intervals.get(0);
        assertEquals(2.0, interval.getX1());
        assertEquals(6.0, interval.getX2());
    }

    @Test
    public void testGetIntersectionIntervals() {
        intervalService.addClosedInterval(1.0, 5.0);
        intervalService.addClosedInterval(3.0, 7.0);
        List<Interval> intersections = intervalService.getIntersectionIntervals();
        assertEquals(1, intersections.size());
        assertTrue(intersections.get(0) instanceof ClosedInterval);
        ClosedInterval interval = (ClosedInterval) intersections.get(0);
        assertEquals(3.0, interval.getX1());
        assertEquals(5.0, interval.getX2());
    }

    @Test
    public void testFindClosestExactMatch() {
        intervalService.addClosedInterval(1.0, 5.0);
        intervalService.addClosedInterval(3.0, 7.0);
        Double closest = intervalService.findClosest(4.0);
        assertEquals(4.0, closest);
    }

    @Test
    public void testFindClosestNearest() {
        intervalService.addClosedInterval(1.0, 5.0);
        intervalService.addClosedInterval(3.0, 7.0);
        Double closest = intervalService.findClosest(8.0);
        assertEquals(5.0, closest);
    }

    @Test
    public void testClearIntervals() {
        intervalService.addClosedInterval(1.0, 5.0);
        intervalService.clear();
        List<Interval> intervals = intervalService.list();
        assertTrue(intervals.isEmpty());
    }

    @Test
    public void testGetCount() {
        intervalService.addClosedInterval(1.0, 5.0);
        intervalService.addOpenInterval(2.0, 6.0);
        assertEquals(2, intervalService.getCount());
    }
}

