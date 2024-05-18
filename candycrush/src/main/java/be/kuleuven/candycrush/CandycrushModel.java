package be.kuleuven.candycrush;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;

public class CandycrushModel {
    private String speler;
    private Board<Candy> candyBoard;
    private Boardsize boardsize;
    private int score;
    private boolean loggedIn;

    public CandycrushModel(String speler,int width,int height) {
        this.speler = speler;
        candyBoard = new Board<>(boardsize);
        boardsize = new Boardsize(width,height);
        score = 0;
        loggedIn = false;
        Function<Position, Candy> candyCreator = position -> randomCandy();
        candyBoard.fill(candyCreator);
    }
    public Candy randomCandy(){
        Random random = new Random();
        int randomGetal = random.nextInt(8);

        return switch (randomGetal) {
            case 4 -> new Jawbreaker();
            case 5 -> new Worm();
            case 6 -> new Cherry();
            case 7 -> new Gumball();
            default -> new NormalCandy(randomGetal);
        };
    }
    public CandycrushModel(String speler){
        this(speler,4,4);
    }
    public String getSpeler() {
        return speler;
    }
    public Boardsize getBoardsize(){return boardsize;}
    public Board<Candy> getSpeelbord() {
        return candyBoard;
    }

    public int getWidth() {
        return boardsize.cols();
    }

    public int getHeight() {
        return boardsize.rows();
    }

    public  int getScore(){ return this.score; }

    public boolean isLoggedIn(){ return this.loggedIn; }

    public void goodLogIn(){
        this.loggedIn = true;
    }

    public void reset(){
        this.score = 0;
        this.loggedIn = false;
    }
    public void candyWithIndexSelected(Position position){
        Iterable<Position> Neighbours = getSameNeighbourPositions(position);
        int size = 0;
        for(Position Neighbour:Neighbours){
            size++;
        }
        if(size > 2) {
            for (Position Neighbour : Neighbours) {
                candyBoard.replaceCellAt(Neighbour, randomCandy());
                score++;
            }
        }
        candyBoard.replaceCellAt(position, randomCandy());
        score++;
    }

    Iterable<Position> getSameNeighbourPositions(Position position){
        Iterable<Position> neighbors = position.neighbourPositions();
        ArrayList<Position> result = new ArrayList<>();

        for(Position neighbor : neighbors){
            Candy candy = candyBoard.getCellAt(position);
            Candy neighborCandy = candyBoard.getCellAt(neighbor);
            if(candy.equals(neighborCandy)){
                result.add(neighbor);
            }
        }
        return result;
    }
}
