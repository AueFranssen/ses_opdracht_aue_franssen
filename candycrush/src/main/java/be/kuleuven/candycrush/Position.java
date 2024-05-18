package be.kuleuven.candycrush;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;

public record Position(int row, int col, Boardsize boardsize) {
    public Position{
        if(row < 0||row > (boardsize.rows()-1)) throw new IllegalArgumentException("Given row is out of bounds");
        if(col < 0||col > (boardsize.cols()-1)) throw new IllegalArgumentException("Given column is out of bounds");
    }
    public int toIndex(){
        return col+row*boardsize.cols();
    }
    public static Position fromIndex(int index, Boardsize size){
      if(index>=(size.cols()*size.rows())) throw new IllegalArgumentException("Given index is out of bounds");
      int row = index / size.cols();
      int col = index % size.cols();
      return new Position(row,col,size);
    }
    public Iterable<Position> neighbourPositions(){
        ArrayList<Position> neighbours = new ArrayList<>();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1},{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        for (int[] dir : directions) {
            int neighbourRow = row + dir[0];
            int neighbourCol = col + dir[1];
            if (isNeighbourPresent(neighbourRow,neighbourCol)) {
                neighbours.add(new Position(neighbourRow, neighbourCol, boardsize));
            }
        }
        return neighbours;
    }

    private boolean isNeighbourPresent(int row,int col){
        try {
            Position neighbourPos = new Position(row, col, boardsize);
        }
        catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
    public boolean isLastColumn(){return col == boardsize.cols()-1;}
    public Stream<Position> walkLeft(){
        return this.boardsize.positions().stream()
                .filter(p -> p.row() == this.row())
                .filter(p -> p.col() <= this.col())
                .sorted(Comparator.comparingInt(Position::col).reversed());
    }
    public Stream<Position> walkRight(){
        return this.boardsize.positions().stream()
                .filter(p -> p.row() == this.row())
                .filter(p -> p.col() >= this.col())
                .sorted(Comparator.comparingInt(Position::col));
    }
    public Stream<Position> walkUp(){
        return this.boardsize.positions().stream()
                .filter(p -> p.col() == this.col())
                .filter(p -> p.row() <= this.row())
                .sorted(Comparator.comparingInt(Position::row).reversed());
    }
    public Stream<Position> walkDown(){
        return this.boardsize.positions().stream()
                .filter(p -> p.col() == this.col())
                .filter(p -> p.row() >= this.row())
                .sorted(Comparator.comparingInt(Position::row));
    }
    }