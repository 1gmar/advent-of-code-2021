package io.exercises;

import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.function.IntToLongFunction;
import java.util.stream.IntStream;

public class Day7 implements Day
{
    @Override
    public long part1(final String input)
    {
        return findMinFuelCost(input, x -> position -> Math.abs(x - position));
    }

    @Override
    public long part2(final String input)
    {
        return findMinFuelCost(input, x -> position -> {
            final int diff = Math.abs(x - position);
            return diff * (diff + 1) / 2;
        });
    }

    private long findMinFuelCost(final String input, final IntFunction<IntToLongFunction> costFunction)
    {
        final int[] positions = input.lines()
                .map(line -> line.split(","))
                .flatMapToInt(digits -> Arrays.stream(digits).mapToInt(Integer::parseInt))
                .toArray();
        final var stats = Arrays.stream(positions).summaryStatistics();

        return IntStream.rangeClosed(stats.getMin(), stats.getMax())
                .mapToLong(x -> evaluateFuelCost(positions, costFunction.apply(x)))
                .min()
                .orElseThrow();
    }

    private long evaluateFuelCost(final int[] positions, final IntToLongFunction costFunction)
    {
        return Arrays.stream(positions)
                .mapToLong(costFunction)
                .sum();
    }
}
