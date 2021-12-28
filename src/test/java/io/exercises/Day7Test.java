package io.exercises;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Day7Test extends DayTest
{
    private final String smallData = "16,1,2,0,4,2,7,1,2,14";
    private final String bigData;

    public Day7Test() throws IOException
    {
        super(new Day7());
        bigData = Files.readString(Path.of("resources/input/day7.txt"));
    }

    @Override
    Map<Long, String> testCasesPart1()
    {
        return Map.of(37L, smallData, 342641L, bigData);
    }

    @Override
    Map<Long, String> testCasesPart2()
    {
        return Map.of(168L, smallData, 93006301L, bigData);
    }
}
