package io.exercises;

import static java.util.Arrays.stream;
import static java.util.stream.IntStream.generate;
import static java.util.stream.IntStream.range;

import java.util.function.BiFunction;
import java.util.stream.Stream;

public class Day3 implements Day
{
    record ReportState(int[][] matrix, int bitPos)
    {
        ReportState filter(final int bitCriteria)
        {
            final var filteredReport = stream(matrix)
                    .filter(row -> row[bitPos] == bitCriteria)
                    .toArray(int[][]::new);

            return new ReportState(filteredReport, (bitPos + 1) % matrix[0].length);
        }
    }

    @Override
    public long part1(final String input)
    {
        final var diagnosticReport = getDiagnosticReport(input);
        final int gammaRate = toDecimal(stream(transpose(diagnosticReport))
                .map(row -> stream(row).summaryStatistics())
                .mapToInt(stats -> stats.getCount() - stats.getSum() > stats.getSum() ? 0 : 1)
                .toArray());
        final int bitMask = toDecimal(generate(() -> 1)
                .limit(diagnosticReport[0].length)
                .toArray());
        final int epsilonRate = ~gammaRate & bitMask;

        return gammaRate * epsilonRate;
    }

    @Override
    public long part2(final String input)
    {
        final var diagnosticReport = getDiagnosticReport(input);
        final BiFunction<Long, Long, Integer> o2GenBitCriteria = (nrOfZeros, nrOfOnes) -> nrOfZeros > nrOfOnes ? 0
                : nrOfZeros < nrOfOnes ? 1 : 1;
        final int o2GenRating = findLifeSupportRating(diagnosticReport, o2GenBitCriteria);
        final int co2ScrubRating = findLifeSupportRating(diagnosticReport, o2GenBitCriteria.andThen(bit -> bit ^ 1));

        return o2GenRating * co2ScrubRating;
    }

    private int toDecimal(final int[] bits)
    {
        return range(0, bits.length).reduce(0, (sum, pos) -> sum + (bits[bits.length - pos - 1] << pos));
    }

    private int[][] getDiagnosticReport(final String input)
    {
        return input.lines()
                .map(line -> line.chars().map(Character::getNumericValue).toArray())
                .toArray(int[][]::new);
    }

    private int[][] transpose(final int[][] matrix)
    {
        return range(0, matrix[0].length)
                .mapToObj(i -> range(0, matrix.length).map(j -> matrix[j][i]).toArray())
                .toArray(int[][]::new);
    }

    private int findLifeSupportRating(final int[][] diagnosticReport, final BiFunction<Long, Long, Integer> bitCriteria)
    {
        return Stream.iterate(new ReportState(diagnosticReport, 0), state -> filterByBitCriteria(state, bitCriteria))
                .filter(state -> state.matrix().length == 1)
                .findFirst()
                .map(state -> toDecimal(state.matrix()[0]))
                .orElseThrow();
    }

    private ReportState filterByBitCriteria(final ReportState state, final BiFunction<Long, Long, Integer> bitCriteria)
    {
        final var stats = stream(transpose(state.matrix())[state.bitPos()]).summaryStatistics();
        final long nrOfZeros = stats.getCount() - stats.getSum();
        final long nrOfOnes = stats.getSum();

        return state.filter(bitCriteria.apply(nrOfZeros, nrOfOnes));
    }
}
