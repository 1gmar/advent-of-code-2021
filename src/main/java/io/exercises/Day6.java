package io.exercises;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

public class Day6 implements Day
{
    record GenerationCount(long count, int[] birthDays)
    {}

    record Fish(int age, int startDay)
    {}

    @Override
    public long part1(final String input)
    {
        return countAllDescendantsForPeriod(input, 80);
    }

    @Override
    public long part2(final String input)
    {
        return countAllDescendantsForPeriod(input, 256);
    }

    private long countAllDescendantsForPeriod(final String input, final int period)
    {
        final var cache = new HashMap<Fish, Long>();

        return input.lines()
                .map(line -> line.split(","))
                .flatMapToInt(digits -> Arrays.stream(digits).mapToInt(Integer::parseInt))
                .mapToLong(age -> 1 + countAllDescendantsIfAbsent(new Fish(age, 0), period, cache))
                .sum();
    }

    private long countAllDescendantsIfAbsent(final Fish fish, final int period, final Map<Fish, Long> cache)
    {
        return Optional.of(fish)
                .map(cache::get)
                .orElseGet(() -> countAllDescendantsFor(fish, period, cache));
    }

    private long countAllDescendantsFor(final Fish fish, final int period, final Map<Fish, Long> cache)
    {
        final var genCount = countDescendants(fish, period);
        final long totalCount = genCount.count + Arrays.stream(genCount.birthDays)
                .mapToLong(day -> countAllDescendantsIfAbsent(new Fish(6, day), period, cache))
                .sum();

        cache.put(fish, totalCount);

        return totalCount;
    }

    private GenerationCount countDescendants(final Fish fish, final int period)
    {
        final int relativePeriod = period - fish.startDay;
        final int extraFish = fish.startDay == 0 && relativePeriod % 7 > fish.age ? 1 : 0;
        final long count = relativePeriod / 7 + extraFish;
        final int[] birthDays = IntStream.iterate(fish.startDay + fish.age + 3, day -> day + 7).limit(count).toArray();

        return new GenerationCount(count, birthDays);
    }
}
