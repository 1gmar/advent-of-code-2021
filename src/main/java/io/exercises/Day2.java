package io.exercises;

public class Day2 implements Day
{
    enum Direction
    {
        forward, down, up
    }

    record Command(Direction direction, int units)
    {}

    interface Position
    {
        int x();
        int y();
        Position move(Command command);

        default int checkSum()
        {
            return x() * y();
        }
    }

    @Override
    public long part1(final String input)
    {
        record Position2D(int x, int y) implements Position
        {
            @Override
            public Position move(final Command command)
            {
                return switch (command.direction())
                {
                    case up -> new Position2D(x, y - command.units());
                    case down -> new Position2D(x, y + command.units());
                    case forward -> new Position2D(x + command.units(), y);
                };
            }
        }

        return getFinalPosition(input, new Position2D(0, 0)).checkSum();
    }

    @Override
    public long part2(final String input)
    {
        record Position3D(int x, int y, int z) implements Position
        {
            @Override
            public Position move(final Command command)
            {
                return switch (command.direction())
                {
                    case up -> new Position3D(x, y, z - command.units());
                    case down -> new Position3D(x, y, z + command.units());
                    case forward -> new Position3D(x + command.units(), y + z * command.units(), z);
                };
            }
        }

        return getFinalPosition(input, new Position3D(0, 0, 0)).checkSum();
    }

    private Position getFinalPosition(final String input, final Position start)
    {
        return input.lines()
                .map(line -> line.split(" "))
                .map(tokens -> new Command(Direction.valueOf(tokens[0]), Integer.parseInt(tokens[1])))
                .reduce(start, (pos, command) -> pos.move(command), (pos, __) -> pos);
    }
}
