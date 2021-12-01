package io.exercises;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day1 implements Day 
{
    record Counter(int increases, int prevValue) 
    {
        Counter count(final int value)
        {
            return new Counter(increases + 1, value);
        }

        Counter skip(final int value)
        {
            return new Counter(increases, value);
        }
    }

    @Override
    public int part1(final String input) 
    {
        return countIncreases(input.lines().map(Integer::parseInt));
    }

    @Override
    public int part2(final String input) 
    {
        final var depths = input.lines().mapToInt(Integer::parseInt).toArray();
        return countIncreases(IntStream.range(0, depths.length - 2)
                .mapToObj(i -> depths[i] + depths[i + 1] + depths[i + 2]));
    }

    private int countIncreases(final Stream<Integer> stream)
    {
        return stream.reduce(new Counter(0, Integer.MAX_VALUE), 
                (counter, value) -> value > counter.prevValue() ? counter.count(value) : counter.skip(value), 
                (counter, __) -> counter).increases();
    }
}
