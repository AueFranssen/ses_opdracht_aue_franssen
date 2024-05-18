package be.kuleuven.candycrush;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.Map;

public class CandycrushView extends Region {
    private CandycrushModel model;
    private int widthCandy;
    private int heigthCandy;

    public CandycrushView(CandycrushModel model) {
        this.model = model;
        widthCandy = 30;
        heigthCandy = 30;
        update();
    }

    public void update(){
        getChildren().clear();
        if(model.isLoggedIn()) {
            Map<Position, Candy> cells = model.getSpeelbord().getCells();
            Boardsize size = model.getBoardsize();
            for (Position position : cells.keySet()){
                Rectangle rectangle = new Rectangle(position.col() * widthCandy, position.row() * heigthCandy, widthCandy,heigthCandy);
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);

                Node node = makeCandyShape(position, model.getSpeelbord().getCellAt(position));
                getChildren().addAll(rectangle,node);
            }
        }
    }

    public int getIndexOfClicked(MouseEvent me){
        int index = -1;
        int row = (int) me.getY()/heigthCandy;
        int column = (int) me.getX()/widthCandy;
        System.out.println(me.getX()+" - "+me.getY()+" - "+row+" - "+column);
        if (row < model.getWidth() && column < model.getHeight()){
            Position pos = new Position(row, column, model.getBoardsize());
            index = pos.toIndex();
            System.out.println(index);
        }
        return index;
    }
    private Node makeCandyShape(Position position, Candy candy){
        return switch(candy) {
            case Jawbreaker ignored -> makeRec(position, Color.GRAY);
            case Worm ignored -> makeRec(position, Color.GREEN);
            case Cherry ignored -> makeRec(position, Color.RED);
            case Gumball ignored -> makeRec(position, Color.BLUE);
            case NormalCandy c -> makeNormalCandy(position, c);
            case noCandy ignored -> makeCirc(position, Color.TRANSPARENT);

            default -> throw new IllegalStateException("Unexpected value: " + candy);
        };
    }

    private Rectangle makeRec(Position position, Paint paint){
        Rectangle r;
        r = new Rectangle(position.col() * widthCandy, position.row() * heigthCandy, widthCandy, heigthCandy);
        r.setFill(paint);
        return r;
    }

    private Circle makeNormalCandy(Position position, NormalCandy candy){
        return switch (candy.colour()){
            case 0 -> makeCirc(position, Color.GREEN);
            case 1 -> makeCirc(position, Color.BLUE);
            case 2 -> makeCirc(position, Color.RED);
            case 3 -> makeCirc(position, Color.BLACK);
            default -> throw new IllegalStateException("Unexpected value: " + candy.colour());
        };
    }
    private Circle makeCirc(Position position, Paint paint){
        Circle c;
        c = new Circle(position.col() * widthCandy + widthCandy/2, position.row() * heigthCandy + heigthCandy/2, widthCandy/2);
        c.setFill(paint);
        return c;
    }
}
