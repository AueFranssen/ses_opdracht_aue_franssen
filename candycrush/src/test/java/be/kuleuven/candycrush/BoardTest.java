package be.kuleuven.candycrush;

import be.kuleuven.candycrush.Board;
import be.kuleuven.candycrush.Boardsize;
import be.kuleuven.candycrush.Candy;
import be.kuleuven.candycrush.Position;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
    public void TestFillBoard(){
        Boardsize size = new Boardsize(3,3);
        Board<Integer> board = new Board<>(size);

        Function<Position, Integer> IntegerCreator = position -> position.row() + position.col();
        board.fill(IntegerCreator);

        assertNotNull(board);
    }
    @Test
    public void testDatBoardKopierd(){
        Boardsize size = new Boardsize(3,3);
        Board<Integer> board = new Board<>(size);
        Board<Integer> otherBoard = new Board<>(size);

        Function<Position, Integer> IntegerCreator = position -> position.row() + position.col();
        board.fill(IntegerCreator);

        board.copyTo(otherBoard);
        assert board.equals(otherBoard);
    }
}