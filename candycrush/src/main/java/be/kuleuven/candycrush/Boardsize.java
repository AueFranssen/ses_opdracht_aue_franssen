package be.kuleuven.candycrush;

import java.util.ArrayList;
import java.util.Collection;

public record Boardsize(int rows, int cols) {
    public Boardsize {
        if (rows <= 0) throw new IllegalArgumentException("rijen moet meer dan 0 zijn");
        if (cols <= 0) throw new IllegalArgumentException("kolommen moet meer dan 0 zijn");
    }

    public Collection<Position> positions(){
        ArrayList<Position> result = new ArrayList<>();
        Boardsize size = new Boardsize(rows, cols);
        for(int i = 0; i < rows*cols; i++){
            result.add(Position.fromIndex(i, size));
        }
        return result;
    }
}