package be.kuleuven.candycrush;

import be.kuleuven.candycrush.CandycrushModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;


public class CandycrushModelTests {

    @Test
    public void gegevenNaamModelAue_wanneerNieuwModelWordtAangemaakt_danIsNaamModelAue(){
        CandycrushModel model = new CandycrushModel("Aue");
        String result = model.getSpeler();
        assert (result.equals("Aue"));
    }
    @Test
    public void gegevenScore_wanneerBordReset_danWordtScore0(){
        CandycrushModel model = new CandycrushModel("Naam");
        Boardsize boardsize = new Boardsize(4,4);
        Position position = new Position(0,0,boardsize);
        model.goodLogIn();
        model.candyWithIndexSelected(position);
        model.reset();
        assert(model.getScore()==0);
    }
    @Test
    public void gegevenPositie_wanneerToIndex_danKrijgvolgendeIndex(){
        Boardsize boardsize = new Boardsize(2,4);
        Position position = new Position(2,2,boardsize);
        assert(position.toIndex()==5);
    }
    @Test
    public void gegevenIndex_wanneerFromIndex_danKrijgVolgendePositie(){
        Boardsize boardsize = new Boardsize(2,4);
        Position actualPosition = new Position(2,2,boardsize);
        Position predictedPosition = Position.fromIndex(5,boardsize);
        assert(actualPosition.equals(predictedPosition));
    }
    @Test
    public void gegevenPositie_wanneerNeighbourPositions_danKrijgVolgendePositie(){
        Boardsize boardsize = new Boardsize(2,4);
        Position position = new Position(2,2,boardsize);
        Iterable<Position> predictedNeighbours = position.neighbourPositions();
        ArrayList<Position> actualNeighbours = new ArrayList<>();
        Position neighbour1 = new Position(1,2,boardsize);
        Position neighbour2 = new Position(2,1,boardsize);
        Position neighbour3 = new Position(2,3,boardsize);
        Position neighbour4 = new Position(1,1,boardsize);
        Position neighbour5 = new Position(1,3,boardsize);
        actualNeighbours.add(neighbour1);
        actualNeighbours.add(neighbour2);
        actualNeighbours.add(neighbour3);
        actualNeighbours.add(neighbour4);
        actualNeighbours.add(neighbour5);
        assert(actualNeighbours.equals(predictedNeighbours));
    }
    @Test
    public void gegevenPositie_wanneerIsLastColumnNietDelaatsteIs_danIsLastColumnFalse(){
        Boardsize boardsize = new Boardsize(2,4);
        Position position = new Position(2,2,boardsize);
        assert(position.isLastColumn()==false);
    }
    @Test
    public void gegevenBoardsize_wanneerPositiesOpgeroept_danGeeftVolgendePosities(){
        Boardsize boardsize = new Boardsize(2,4);
        Iterable<Position> predictedPositions = boardsize.positions();
        ArrayList<Position> actualPositions = new ArrayList<>();
        actualPositions.add(new Position(1,1,boardsize));
        actualPositions.add(new Position(1,2,boardsize));
        actualPositions.add(new Position(1,3,boardsize));
        actualPositions.add(new Position(1,4,boardsize));
        actualPositions.add(new Position(2,1,boardsize));
        actualPositions.add(new Position(2,2,boardsize));
        actualPositions.add(new Position(2,3,boardsize));
        actualPositions.add(new Position(2,4,boardsize));
        assert(actualPositions.equals(predictedPositions));
    }
    @Test
    public void gegevenGeresetSpel_wanneerWordtOpgestart_danKanSpelOpnieuwStarten(){
        CandycrushModel model = new CandycrushModel("Naam");
        model.goodLogIn();
        model.reset();
        model.goodLogIn();
        assert (model.isLoggedIn());
    }
    @Test
    public void gegevenScore_wanneerEersteSnoepGeklikt_danMoetScoreBoven0(){
        CandycrushModel model = new CandycrushModel("Naam");
        Boardsize boardsize = new Boardsize(4,4);
        Position position = new Position(0,0,boardsize);
        model.goodLogIn();
        model.candyWithIndexSelected(position);
        assert (model.getScore()>0);
    }
    @Test
    public void gegevenScore_wanneerSnoepGeklikt_danMoetScoreVerhogen(){
        CandycrushModel model = new CandycrushModel("Naam");
        Boardsize boardsize = new Boardsize(4,4);
        Position position = new Position(0,0,boardsize);
        model.goodLogIn();
        model.candyWithIndexSelected(position);
        int beginScore = model.getScore();
        model.candyWithIndexSelected(position);
        assert (model.getScore()>beginScore);
    }

    @Test
    public void gegevenAlIngelogd_wanneerNogInlogd_danMoetScoreblijven(){
        CandycrushModel model = new CandycrushModel("Naam");
        Boardsize boardsize = new Boardsize(4,4);
        Position position = new Position(0,0,boardsize);
        model.goodLogIn();
        model.candyWithIndexSelected(position);
        int beginScore = model.getScore();
        model.goodLogIn();
        assert (model.getScore()==beginScore);
    }
    @Test
    public void gegevenSpeelbord_wanneerGereset_danMoetSpelbordVerwijderd(){
        CandycrushModel model = new CandycrushModel("Naam");
        model.goodLogIn();
        model.reset();
        assert (!model.isLoggedIn());
    }
}
