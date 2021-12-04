package io.exercises;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.joining;
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
        final var gammaRate = stream(transpose(diagnosticReport))
                .map(row -> stream(row).summaryStatistics())
                .map(stats -> stats.getCount() - stats.getSum() > stats.getSum() ? "0" : "1")
                .collect(collectingAndThen(joining(), bitString -> parseInt(bitString, 2)));

        final var epsilonRate = ~gammaRate & parseInt("1".repeat(diagnosticReport[0].length), 2);

        return gammaRate * epsilonRate;
    }

    @Override
    public long part2(final String input)
    {
        final var diagnosticReport = getDiagnosticReport(input);
        final BiFunction<Long, Long, Integer> o2GenBitCriteria = (nrOfZeros, nrOfOnes) -> nrOfZeros > nrOfOnes ? 0
                : nrOfZeros < nrOfOnes ? 1 : 1;

        final var o2GenRating = findLifeSupportRating(diagnosticReport, o2GenBitCriteria);
        final var co2ScrubRating = findLifeSupportRating(diagnosticReport, o2GenBitCriteria.andThen(bit -> bit ^ 1));

        return o2GenRating * co2ScrubRating;
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
                .map(state -> stream(state.matrix()[0]).mapToObj(String::valueOf).collect(joining()))
                .map(bitString -> parseInt(bitString, 2))
                .orElseThrow();
    }

    private ReportState filterByBitCriteria(final ReportState state, final BiFunction<Long, Long, Integer> bitCriteria)
    {
        final var stats = stream(transpose(state.matrix())[state.bitPos()]).summaryStatistics();
        final var nrOfZeros = stats.getCount() - stats.getSum();
        final var nrOfOnes = stats.getSum();

        return state.filter(bitCriteria.apply(nrOfZeros, nrOfOnes));
    }
}
