package io.exercises;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

public abstract class DayTest
{
    private final Day solution;

    public DayTest(final Day solution)
    {
        this.solution = solution;
    }

    abstract Map<Long, String> testCasesPart1();
    abstract Map<Long, String> testCasesPart2();

    @Test
    public void testPart1()
    {
        testCasesPart1().forEach((expected, input) -> assertEquals(expected, solution.part1(input)));
    }

    @Test
    public void testPart2()
    {
        testCasesPart2().forEach((expected, input) -> assertEquals(expected, solution.part2(input)));
    }
}
