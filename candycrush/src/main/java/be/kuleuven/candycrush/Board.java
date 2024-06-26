package be.kuleuven.candycrush;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
public class Board<E> {
    private Map<Position, E> cells;
    private Map<E, Position> positions;
    private Boardsize boardsize;

    public Board(Boardsize boardsize) {
        this.boardsize = boardsize;
        this.cells = new ConcurrentHashMap<>();
        this.positions = new ConcurrentHashMap<>();
    }
    public Boardsize getBoardSize() {return boardsize;}

    public Map<Position, E> getCells(){ return Collections.unmodifiableMap(cells);}

    public synchronized E getCellAt(Position position){
        return cells.get(position);
    }

    public synchronized void replaceCellAt(Position position, E newCell){
        cells.put(position, newCell);
        positions.put(newCell, position);
    }
    public void fill(Function<Position, E> cellCreator){
        for (int i = 0; i  <boardsize.cols() *  boardsize.rows(); i++) {
            Position position = Position.fromIndex(i, boardsize);
            E cell = cellCreator.apply(position);
            replaceCellAt(position, cell);
        }
    }
    public void copyTo(Board<E> otherBoard){
        if(!(boardsize.equals(otherBoard.getBoardSize()))){
            throw new RuntimeException("Boards not same size");
        }
        for (Position position : cells.keySet()){
            otherBoard.replaceCellAt(position, this.cells.get(position));
        }
    }
    public List<E> getPositionsOfElement(E element){
        ArrayList<E> list = new ArrayList<>();
        for (E cell : positions.keySet()){
            if(element.equals(cell)){
                list.add(cell);
            }
        }
        return Collections.unmodifiableList(list);
    }
    public void printBoard() {
        for (int row = 0; row < boardsize.rows(); row++) {
            for (int col = 0; col < boardsize.cols(); col++) {
                Position position = new Position(row, col, boardsize);
                E cell = getCellAt(position);
                System.out.print(cell != null ? cell.toString() : " ");
                System.out.print(" ");
            }
            System.out.println();
        }
    }
    @Override
    public synchronized boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board<?> board = (Board<?>) o;
        return Objects.equals(cells, board.cells) && Objects.equals(boardsize, board.boardsize);
    }
}