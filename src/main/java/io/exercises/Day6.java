package io.exercises;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class Day6 implements Day
{
    record GenerationCounter(long count, List<Integer> birthDays)
    {}

    record FishState(int age, int startDay)
    {}

    @Override
    public long part1(final String input)
    {
        final var memo = new HashMap<FishState, Long>();

        return parseInput(input)
                .mapToLong(age -> 1 + countAllDescendantsIfAbsent(age, 0, 80, memo))
                .sum();
    }

    @Override
    public long part2(final String input)
    {
        final var memo = new HashMap<FishState, Long>();

        return parseInput(input)
                .mapToLong(age -> 1 + countAllDescendantsIfAbsent(age, 0, 256, memo))
                .sum();
    }

    private Stream<Integer> parseInput(final String input)
    {
        return input.lines()
                .map(line -> line.split(","))
                .flatMap(digits -> Arrays.stream(digits).map(Integer::parseInt));
    }

    private long countAllDescendantsIfAbsent(final int age, final int startDay, final int period,
            final Map<FishState, Long> cache)
    {
        final var state = new FishState(age, startDay);

        return Optional.of(state)
                .map(cache::get)
                .orElseGet(() -> countAllDescendantsFor(period, cache, state));
    }

    private Long countAllDescendantsFor(final int period, final Map<FishState, Long> cache, final FishState state)
    {
        final var counter = countDescendants(state.age, state.startDay, period);

        if (counter.birthDays.isEmpty())
        {
            cache.putIfAbsent(state, counter.count);

            return counter.count;
        }
        else
        {
            final long count = counter.count + counter.birthDays.stream()
                    .mapToLong(day -> countAllDescendantsIfAbsent(6, day, period, cache))
                    .sum();

            cache.putIfAbsent(state, count);

            return count;
        }
    }

    private GenerationCounter countDescendants(final int age, final int startDay, final int period)
    {
        final int relativePeriod = period - startDay;
        final int extraFish = startDay == 0 && relativePeriod % 7 > age ? 1 : 0;
        final long count = relativePeriod / 7 + extraFish;
        final var birthDays = Stream.iterate(startDay + age + 3, day -> day + 7).limit(count).toList();

        return new GenerationCounter(count, birthDays);
    }
}
