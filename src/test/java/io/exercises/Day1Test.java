package io.exercises;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Day1Test extends DayTest 
{
    private final String smallData = "199\n200\n208\n210\n200\n207\n240\n269\n260\n263";
    private final String bigData;

    public Day1Test() throws IOException 
    {
        super(new Day1());
        this.bigData = Files.readString(Path.of("resources/input/day1.txt"));
    }

    @Override
    Map<Integer, String> testCasesPart1() throws IOException
    {
        return Map.of(7, smallData, 1482, bigData);
    }

    @Override
    Map<Integer, String> testCasesPart2() throws IOException
    {
        return Map.of(5, smallData, 1518, bigData);
    }
}
