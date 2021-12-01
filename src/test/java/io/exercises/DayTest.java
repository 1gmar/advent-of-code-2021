package io.exercises;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.Test;

public abstract class DayTest 
{
    private final Day solution;

    public DayTest(final Day solution) 
    {
        this.solution = solution;
    }

    abstract Map<Integer, String> testCasesPart1() throws IOException;
    abstract Map<Integer, String> testCasesPart2() throws IOException;

    @Test
    public void testPart1() throws IOException
    {
        testCasesPart1().forEach((expected, input) -> assertEquals(expected, solution.part1(input)));
    }

    @Test
    public void testPart2() throws IOException
    {
        testCasesPart2().forEach((expected, input) -> assertEquals(expected, solution.part2(input)));
    }
}
