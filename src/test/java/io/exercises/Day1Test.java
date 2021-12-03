package io.exercises;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Day1Test extends DayTest
{
    private final String smallData = """
            199
            200
            208
            210
            200
            207
            240
            269
            260
            263
            """;
    private final String bigData;

    public Day1Test() throws IOException
    {
        super(new Day1());
        bigData = Files.readString(Path.of("resources/input/day1.txt"));
    }

    @Override
    Map<Long, String> testCasesPart1()
    {
        return Map.of(7L, smallData, 1482L, bigData);
    }

    @Override
    Map<Long, String> testCasesPart2()
    {
        return Map.of(5L, smallData, 1518L, bigData);
    }
}
