package io.exercises;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Day3Test extends DayTest
{
    private final String smallData = """
            00100
            11110
            10110
            10111
            10101
            01111
            00111
            11100
            10000
            11001
            00010
            01010
            """;
    private final String bigData;

    public Day3Test() throws IOException
    {
        super(new Day3());
        bigData = Files.readString(Path.of("resources/input/day3.txt"));
    }

    @Override
    Map<Long, String> testCasesPart1()
    {
        return Map.of(198L, smallData, 3320834L, bigData);
    }

    @Override
    Map<Long, String> testCasesPart2()
    {
        return Map.of(230L, smallData, 4481199L, bigData);
    }
}
