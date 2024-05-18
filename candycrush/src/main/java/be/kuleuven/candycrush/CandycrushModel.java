package be.kuleuven.candycrush;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CandycrushModel {
    private String speler;
    private Board<Candy> candyBoard;
    private Boardsize boardsize;
    private int score;
    private boolean loggedIn;

    public CandycrushModel(String speler,int width,int height) {
        this.speler = speler;
        candyBoard = new Board<>(boardsize);
        boardsize = new Boardsize(height,width);
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
        this(speler,10,10);
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
        candyBoard.replaceCellAt(position, new noCandy());
        updateBoard();
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
    public boolean firstTwoHaveCandy(Candy candy, Stream<Position> positions){
        return positions
                .limit(2)
                .allMatch(p -> candyBoard.getCellAt(p).equals(candy));
    }
    public Stream<Position> horizontalStartingPositions(){
        return boardsize.positions().stream()
                .filter(p -> {
                    Stream<Position> buren = p.walkLeft();
                    return !firstTwoHaveCandy(candyBoard.getCellAt(p), buren);
                });
    }
    public Stream<Position> verticalStartingPositions(){
        return boardsize.positions().stream()
                .filter(p -> {
                    Stream<Position> buren = p.walkUp();
                    return !firstTwoHaveCandy(candyBoard.getCellAt(p), buren);
                });
    }
    public List<Position> longestMatchToRight(Position pos){
        Stream<Position> walked = pos.walkRight();
        return walked
                .takeWhile(p -> getSpeelbord().getCellAt(p).equals(getSpeelbord().getCellAt(pos))
                        && !(getSpeelbord().getCellAt(p) instanceof noCandy) && !(getSpeelbord().getCellAt(pos) instanceof noCandy))
                .toList();
    }

    public List<Position> longestMatchDown(Position pos){
        Stream<Position> walked = pos.walkDown();
        return walked
                .takeWhile(p -> getSpeelbord().getCellAt(p).equals(getSpeelbord().getCellAt(pos))
                        && !(getSpeelbord().getCellAt(p) instanceof noCandy) && !(getSpeelbord().getCellAt(pos) instanceof noCandy))
                .toList();
    }

    public Set<List<Position>> findAllMatches(){
        List<List<Position>> allMatches = Stream.concat(horizontalStartingPositions(), verticalStartingPositions())
                .flatMap(p -> {
                    List<Position> horizontalMatch = longestMatchToRight(p);
                    List<Position> verticalMatch = longestMatchDown(p);
                    return Stream.of(horizontalMatch, verticalMatch);
                })
                .filter(m -> m.size() > 2)
                .sorted((match1, match2) -> match2.size() - match1.size())
                .toList();
        System.out.println(allMatches);

        return allMatches.stream()
                .filter(match -> allMatches.stream()
                        .noneMatch(longerMatch -> longerMatch.size() > match.size() && new HashSet<>(longerMatch).containsAll(match)))
                .collect(Collectors.toSet());
    }
    public void clearMatch(List<Position> match){
        List<Position> copy = new ArrayList<>(match);

        if(copy.isEmpty()) return;
        Position first = copy.getFirst();
        candyBoard.replaceCellAt(first, new noCandy()); // ZOU NULL WERKEN OF HEEFT DEZE EEN EMPTY CANDY TYPE NODIG??
        System.out.println(copy);
        copy.removeFirst();
        clearMatch(copy);
    }

    public void fallDownto(List<Position> match){
        if(horizontalMatch(match)){
            match.forEach(this::fallDownTo);
        } else {
            match.stream()
                    .min(Comparator.comparingInt(Position::row)).ifPresent(this::fallDownTo);
        }
    }

    public void fallDownTo(Position pos){
        try{
            Position boven = new Position(pos.row() - 1, pos.col(), boardsize);
            if(candyBoard.getCellAt(pos) instanceof noCandy){
                candyBoard.replaceCellAt(pos, candyBoard.getCellAt(boven));
                fallDownTo(boven);
            } else{
                fallDownTo(boven);
            }
        } catch (IllegalArgumentException ignored){
            return;
        }
    }

    public boolean horizontalMatch(List<Position> match){
        return match.getFirst().row() == match.getLast().row();
    }

    public boolean updateBoard(){
        Set<List<Position>> matches = findAllMatches();
        System.out.println(horizontalStartingPositions().toList());
        if (matches.isEmpty()) return false;

        for(List<Position> match : matches){
            System.out.println(match);
        }
        return true;
    }

    public static void main(String[] args) {
        CandycrushModel model = new CandycrushModel("Speler", 3, 3);
        Board<Candy> board = model.getSpeelbord();

        List<Position> positionStream = model.horizontalStartingPositions().toList();
        int a = 0;
    }

}
