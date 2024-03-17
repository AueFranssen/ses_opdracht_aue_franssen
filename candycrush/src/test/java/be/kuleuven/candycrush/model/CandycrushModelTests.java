package be.kuleuven.candycrush.model;

import be.kuleuven.candycrush.CandycrushController;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static be.kuleuven.CheckNeighboursInGrid.*;

public class CandycrushModelTests {

    @Test
    public void gegevenNaamModelAue_wanneerNieuwModelWordtAangemaakt_danIsNaamModelAue(){
        CandycrushModel model = new CandycrushModel("Aue");
        String result = model.getSpeler();
        assert (result.equals("Aue"));
    }

    @Test
    public void gegevenArray_wanneerBurenWordenBerekent_danKrijgTerugVolgendeArray(){
        ArrayList<Integer> gridList = new ArrayList<>();
        gridList.add(0);
        gridList.add(0);
        gridList.add(1);
        gridList.add(0);
        gridList.add(1);
        gridList.add(1);
        gridList.add(0);
        gridList.add(2);
        gridList.add(2);
        gridList.add(0);
        gridList.add(1);
        gridList.add(3);
        gridList.add(0);
        gridList.add(1);
        gridList.add(1);
        gridList.add(1);
        ArrayList<Integer> output = (ArrayList<Integer>) getSameNeighboursIds(gridList, 4, 4, 5);
        ArrayList<Integer> predictedOutput = new ArrayList<>();
        predictedOutput.add(4);
        predictedOutput.add(2);
        predictedOutput.add(10);

        assert (output.equals(predictedOutput));
    }

    @Test
    public void gegevenScore_wanneerBordReset_danWordtScore0(){
        CandycrushModel model = new CandycrushModel("Naam");
        model.goodLogIn();
        model.candyWithIndexSelected(0);
        model.reset();
        assert(model.getScore()==0);
    }

    @Test
    public void gegevenBeginScore_wanneerHetEerstKlikt_danKanScoreNietBoven9(){
        CandycrushModel model = new CandycrushModel("Naam");
        model.goodLogIn();
        model.candyWithIndexSelected((model.getWidth() + 1));
        assert (model.getScore()<10);
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
        model.goodLogIn();
        model.candyWithIndexSelected(0);
        assert (model.getScore()>0);
    }

    @Test
    public void gegevenScore_wanneerSnoepGeklikt_danMoetScoreVerhogen(){
        CandycrushModel model = new CandycrushModel("Naam");
        model.goodLogIn();
        model.candyWithIndexSelected(0);
        int beginScore = model.getScore();
        model.candyWithIndexSelected(0);
        assert (model.getScore()>beginScore);
    }

    @Test
    public void gegevenAlIngelogd_wanneerNogInlogd_danMoetScoreblijven(){
        CandycrushModel model = new CandycrushModel("Naam");
        model.goodLogIn();
        model.candyWithIndexSelected(0);
        int beginScore = model.getScore();
        model.goodLogIn();
        assert (model.getScore()==beginScore);
    }

    @Test
    public void gegevenSpeelbord_wanneerGeinitialiseerd_danMoetBreedteMaalHoogteLengteArrayListZijn(){
        CandycrushModel model = new CandycrushModel("Naam");
        model.goodLogIn();
        assert ((model.getWidth() * model.getHeight()) == model.getSpeelbord().size());
    }

    @Test
    public void gegevenSpeelbord_wanneerGereset_danMoetSpelbordVerwijderd(){
        CandycrushModel model = new CandycrushModel("Naam");
        model.goodLogIn();
        model.reset();
        assert (!model.isLoggedIn());
    }
}
