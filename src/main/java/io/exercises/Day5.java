package io.exercises;

import static java.lang.Integer.parseInt;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.Comparator;
import java.util.stream.Stream;

public class Day5 implements Day
{
    record Point(int x, int y) implements Comparable<Point>
    {
        static final Comparator<Point> COMPARATOR = Comparator.comparingInt(Point::x).thenComparingInt(Point::y);

        Point move(final int dx, final int dy)
        {
            return new Point(x + dx, y + dy);
        }

        @Override
        public int compareTo(final Point point)
        {
            return COMPARATOR.compare(this, point);
        }
    }

    record Segment(Point p1, Point p2)
    {
        Point minPoint()
        {
            return p1.compareTo(p2) < 0 ? p1 : p2;
        }

        Point maxPoint()
        {
            return p1.compareTo(p2) < 0 ? p2 : p1;
        }
    }

    @Override
    public long part1(final String input)
    {
        return countOverlappingPoints(parseSegments(input)
                .filter(segment -> segment.p1().x() == segment.p2().x() || segment.p1().y() == segment.p2().y()));
    }

    @Override
    public long part2(final String input)
    {
        return countOverlappingPoints(parseSegments(input));
    }

    private Stream<Segment> parseSegments(final String input)
    {
        return input.lines()
                .map(line -> line.split(" -> "))
                .map(tokens -> new Segment(parsePoint(tokens[0]), parsePoint(tokens[1])));
    }

    private long countOverlappingPoints(final Stream<Segment> segmentStream)
    {
        return segmentStream.flatMap(this::generateSegmentPoints)
                .collect(toMap(identity(), __ -> 1, Integer::sum))
                .values().stream()
                .filter(n -> n > 1)
                .count();
    }

    private Point parsePoint(final String pointStr)
    {
        final var xy = pointStr.split(",");

        return new Point(parseInt(xy[0]), parseInt(xy[1]));
    }

    private Stream<Point> generateSegmentPoints(final Segment segment)
    {
        final int deltaX = segment.maxPoint().x() - segment.minPoint().x();
        final int deltaY = segment.maxPoint().y() - segment.minPoint().y();
        final int dx = deltaX > 0 ? 1 : 0;
        final int dy = deltaY > 0 ? 1 : deltaY < 0 ? -1 : 0;

        return Stream.iterate(segment.minPoint(),
                point -> point.compareTo(segment.maxPoint()) <= 0,
                point -> point.move(dx, dy));
    }
}
