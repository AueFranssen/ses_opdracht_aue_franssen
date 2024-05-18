package be.kuleuven.candycrush;

import be.kuleuven.candycrush.Candy;
import be.kuleuven.candycrush.CandycrushModel;
import be.kuleuven.candycrush.Position;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.Iterator;

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
            int i = 0;
            int height = 0;
            Iterator<Candy> iter = model.getSpeelbord().iterator();
            while (iter.hasNext()) {
                Candy candy = iter.next();
                Rectangle rectangle = new Rectangle(i * widthCandy, height * heigthCandy, widthCandy, heigthCandy);
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);
                Position position = new Position(height, i, model.getBoardsize());
                Node node = makeCandyShape(position, candy);
                getChildren().addAll(rectangle, node);

                if (i == model.getWidth() - 1) {
                    i = 0;
                    height++;
                } else {
                    i++;
                }
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
            case Jawbreaker ignored -> makeRec(position, Color.WHITE);
            case Worm ignored -> makeRec(position, Color.GREEN);
            case Cherry ignored -> makeRec(position, Color.RED);
            case Gumball ignored -> makeRec(position, Color.BLUE);
            case NormalCandy c -> makeNormalCandy(position, c);

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
