package io.exercises;

import static io.exercises.Day4.CellStatus.MARKED;
import static io.exercises.Day4.CellStatus.UNMARKED;
import static java.util.Arrays.stream;
import static java.util.function.Predicate.not;
import static java.util.stream.IntStream.range;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class Day4 implements Day
{
    enum CellStatus
    {
        UNMARKED, MARKED
    }

    record Cell(int number, CellStatus status)
    {
        Cell(final int number)
        {
            this(number, UNMARKED);
        }

        Cell mark()
        {
            return new Cell(number, MARKED);
        }
    }

    record BingoBoard(Cell[][] cells)
    {
        record Position(int row, int column)
        {}

        BingoBoard markCell(final int number)
        {
            final var positionToMark = range(0, cells.length)
                    .mapToObj(i -> findPosition(number, i))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .findFirst();

            positionToMark.ifPresent(pos -> {
                final var cell = cells[pos.row()][pos.column()];
                cells[pos.row()][pos.column()] = cell.mark();
            });

            return this;
        }

        private Optional<Position> findPosition(final int number, int row)
        {
            return range(0, cells[row].length)
                    .filter(j -> cells[row][j].number() == number)
                    .mapToObj(j -> new Position(row, j))
                    .findFirst();
        }
    }

    record BingoGame(int drawCount, int[] numbersToDraw, List<BingoBoard> boards)
    {
        BingoGame drawNumber()
        {
            return new BingoGame(drawCount + 1, numbersToDraw, markBingoBoards(numbersToDraw[drawCount]));
        }

        private List<BingoBoard> markBingoBoards(final int number)
        {
            return boards.stream().map(board -> board.markCell(number)).toList();
        }
    }

    @Override
    public long part1(final String input)
    {
        return playTheGame(input, BingoGame::drawNumber, this::hasWinner);
    }

    @Override
    public long part2(final String input)
    {
        return playTheGame(input,
                game -> dismissWinnersUnlessLast(game.drawNumber()),
                game -> game.boards().size() == 1 && hasWinner(game));
    }

    private long playTheGame(final String input, final UnaryOperator<BingoGame> gameStep,
            final Predicate<BingoGame> endGame)
    {
        final var bingoGame = parseBingoGame(input);

        return Stream.iterate(bingoGame.drawNumber(), gameStep)
                .filter(endGame)
                .findFirst()
                .map(this::calculateFinalScore)
                .orElseThrow();
    }

    private BingoGame parseBingoGame(final String input)
    {
        final var inputSections = input.split("\n\n");
        final var numbersToDraw = stream(inputSections[0].split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
        final var bingoBoards = range(1, inputSections.length)
                .mapToObj(i -> inputSections[i].split("\n"))
                .map(boardLines -> new BingoBoard(parseBingoBoard(boardLines)))
                .toList();

        return new BingoGame(0, numbersToDraw, bingoBoards);
    }

    private Cell[][] parseBingoBoard(final String[] boardLines)
    {
        return stream(boardLines)
                .map(String::trim)
                .map(this::parseBoardRow)
                .toArray(Cell[][]::new);
    }

    private Cell[] parseBoardRow(final String line)
    {
        return stream(line.split("\s+"))
                .map(Integer::parseInt)
                .map(Cell::new)
                .toArray(Cell[]::new);
    }

    private boolean hasWinner(final BingoGame game)
    {
        return game.boards().stream().anyMatch(this::isWinner);
    }

    private int calculateFinalScore(final BingoGame game)
    {
        final var lastDrawnNumber = game.numbersToDraw()[game.drawCount() - 1];
        final var winnerBoard = game.boards().stream()
                .filter(this::isWinner)
                .map(BingoBoard::cells)
                .findFirst()
                .orElse(new Cell[][]{});

        return stream(winnerBoard)
                .mapToInt(row -> stream(row).filter(cell -> UNMARKED == cell.status()).mapToInt(Cell::number).sum())
                .sum() * lastDrawnNumber;
    }

    private boolean isWinner(final BingoBoard board)
    {
        return anyRowComplete(board.cells()) || anyRowComplete(transpose(board.cells()));
    }

    private boolean anyRowComplete(final Cell[][] cells)
    {
        return stream(cells)
                .anyMatch(row -> stream(row).map(Cell::status).allMatch(MARKED::equals));
    }

    private Cell[][] transpose(final Cell[][] matrix)
    {
        return range(0, matrix[0].length)
                .mapToObj(i -> range(0, matrix.length).mapToObj(j -> matrix[j][i]).toArray(Cell[]::new))
                .toArray(Cell[][]::new);
    }

    private BingoGame dismissWinnersUnlessLast(final BingoGame game)
    {
        final var filteredBoards = game.boards().size() > 1
                ? game.boards().stream().filter(not(this::isWinner)).toList()
                : game.boards();

        return new BingoGame(game.drawCount(), game.numbersToDraw(), filteredBoards);
    }
}
