package be.kuleuven.candycrush;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
public class Board<E> {
    private ArrayList<E> cells;
    private Boardsize boardsize;

    public Board(Boardsize boardsize) {
        this.boardsize = boardsize;
        cells = new ArrayList<>();
    }
    public Boardsize getBoardSize() {
        return boardsize;
    }

    public Iterator<E> getCells(){
        return cells.iterator();
    }

    public E getCellAt(Position position){
        return cells.get(position.toIndex());
    }

    public void replaceCellAt(Position position, E newCell){
        cells.set(position.toIndex(), newCell);
    }

    public void fill(Function<Position, E> cellCreator){
        for (int i = 0; i  <boardsize.cols() *  boardsize.rows(); i++) {
            cells.add(cellCreator.apply(Position.fromIndex(i, boardsize)));
        }
    }

    public void copyTo(Board<E> otherBoard){
        if(!(boardsize.equals(otherBoard.getBoardSize()))){
            throw new RuntimeException("Boards not same size");
        }
        for (int i = 0; i <boardsize.cols() *  boardsize.rows(); i++){
            otherBoard.cells.add(cells.get(i));
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board<?> board = (Board<?>) o;
        return Objects.equals(cells, board.cells) && Objects.equals(boardsize, board.boardsize);
    }
}