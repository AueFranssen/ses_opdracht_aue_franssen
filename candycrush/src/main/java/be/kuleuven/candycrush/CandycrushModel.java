package be.kuleuven.candycrush;

import java.util.ArrayList;
import java.util.Random;

public class CandycrushModel {
    private String speler;
    private ArrayList<Candy> speelbord;
    private Boardsize boardsize;
    private int score;
    private boolean loggedIn;

    public CandycrushModel(String speler,int width,int height) {
        this.speler = speler;
        speelbord = new ArrayList<>();
        boardsize = new Boardsize(width,height);
        score = 0;
        loggedIn = false;

        for (int i = 0; i < width*height; i++){
            speelbord.add(randomCandy());
        }
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
    public ArrayList<Candy> getSpeelbord() {
        return speelbord;
    }

    public int getWidth() {
        return boardsize.cols();
    }

    public int getHeight() {
        return boardsize.rows();
    }

    public  int getScore(){ return score; }

    public boolean isLoggedIn(){ return loggedIn; }

    public void goodLogIn(){
        loggedIn = true;
    }

    public void reset(){
        score = 0;
        loggedIn = false;
    }
    public void candyWithIndexSelected(Position position){
        Iterable<Position> Neighbours = getSameNeighbourPositions(position);
        int size = 0;
        for(Position Neighbour:Neighbours){
            size++;
        }
        if(size > 2) {
            for (Position Neighbour : Neighbours) {
                speelbord.set(Neighbour.toIndex(), randomCandy());
                score++;
            }
        }
        speelbord.set(position.toIndex(), randomCandy());
        score++;
    }

    Iterable<Position> getSameNeighbourPositions(Position position){
        Iterable<Position> neighbors = position.neighbourPositions();
        ArrayList<Position> result = new ArrayList<>();

        for(Position neighbor : neighbors){
            Candy candy = speelbord.get(position.toIndex());
            Candy neighborCandy = speelbord.get(neighbor.toIndex());
            if(candy.equals(neighborCandy)){
                result.add(neighbor);
            }
        }
        return result;
    }
}
