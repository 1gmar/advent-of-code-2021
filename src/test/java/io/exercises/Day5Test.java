package io.exercises;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Day5Test extends DayTest
{
    private final String smallData = """
            0,9 -> 5,9
            8,0 -> 0,8
            9,4 -> 3,4
            2,2 -> 2,1
            7,0 -> 7,4
            6,4 -> 2,0
            0,9 -> 2,9
            3,4 -> 1,4
            0,0 -> 8,8
            5,5 -> 8,2
            """;
    private final String bigData;

    public Day5Test() throws IOException
    {
        super(new Day5());
        bigData = Files.readString(Path.of("resources/input/day5.txt"));
    }

    @Override
    Map<Long, String> testCasesPart1()
    {
        return Map.of(5L, smallData, 7085L, bigData);
    }

    @Override
    Map<Long, String> testCasesPart2()
    {
        return Map.of(12L, smallData, 20271L, bigData);
    }
}
