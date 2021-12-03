package io.exercises;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Day2Test extends DayTest
{
    private final String smallData = """
            forward 5
            down 5
            forward 8
            up 3
            down 8
            forward 2
            """;
    private final String bigData;

    public Day2Test() throws IOException
    {
        super(new Day2());
        bigData = Files.readString(Path.of("resources/input/day2.txt"));
    }

    @Override
    Map<Long, String> testCasesPart1()
    {
        return Map.of(150L, smallData, 2091984L, bigData);
    }

    @Override
    Map<Long, String> testCasesPart2()
    {
        return Map.of(900L, smallData, 2086261056L, bigData);
    }
}
