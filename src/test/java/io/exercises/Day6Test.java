package io.exercises;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Day6Test extends DayTest
{
    private final String smallData = "3,4,3,1,2";
    private final String bigData;

    public Day6Test() throws IOException
    {
        super(new Day6());
        bigData = Files.readString(Path.of("resources/input/day6.txt"));
    }

    @Override
    Map<Long, String> testCasesPart1()
    {
        return Map.of(5934L, smallData, 353079L, bigData);
    }

    @Override
    Map<Long, String> testCasesPart2()
    {
        return Map.of(26984457539L, smallData, 1605400130036L, bigData);
    }
}
