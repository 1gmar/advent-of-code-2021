package io.exercises;

import java.util.stream.IntStream;

public class Day1 implements Day
{
    @Override
    public long part1(final String input)
    {
        return countIncreasesWindowed(input, 1);
    }

    @Override
    public long part2(final String input)
    {
        return countIncreasesWindowed(input, 3);
    }

    private long countIncreasesWindowed(final String input, final int windowSize)
    {
        final var depths = input.lines()
                .mapToInt(Integer::parseInt)
                .toArray();

        return IntStream.range(0, depths.length - windowSize)
                .filter(i -> depths[i] < depths[i + windowSize])
                .count();
    }

}
