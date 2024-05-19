package be.kuleuven.candycrush;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class CandycrushController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label Label;

    @FXML
    private Label scoreLabel;

    @FXML
    private Button resetBtn;

    @FXML
    private Button btn;

    @FXML
    private AnchorPane paneel;

    @FXML
    private AnchorPane speelbord;

    @FXML
    private TextField textInput;

    private CandycrushModel model;
    private CandycrushView view;
    @FXML
    void initialize() {
        assert Label != null : "fx:id=\"Label\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        assert scoreLabel != null : "fx:id=\"scoreLabel\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        assert resetBtn != null : "fx:id=\"resetBtn\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        assert btn != null : "fx:id=\"btn\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        assert paneel != null : "fx:id=\"paneel\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        assert speelbord != null : "fx:id=\"speelbord\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        assert textInput != null : "fx:id=\"textInput\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        CandycrushModel model1 = createBoardFromString("""
                                                               @@o#
                                                               o*#o
                                                               @@**
                                                               *#@@""");

        CandycrushModel model2 = createBoardFromString("""
                                                               #oo##
                                                               #@o@@
                                                               *##o@
                                                               @@*@o
                                                               **#*o""");

        CandycrushModel model3 = createBoardFromString("""
                                                               #@#oo@
                                                               @**@**
                                                               o##@#o
                                                               @#oo#@
                                                               @*@**@
                                                               *#@##*""");

        model = model3;
        view = new CandycrushView(model);
        speelbord.getChildren().add(view);
        view.setOnMouseClicked(this::onCandyClicked);
        btn.setOnMouseClicked(this::onStartClicked);
        resetBtn.setOnMouseClicked(this::onResetClicked);
        resetBtn.setDisable(true);
        Solution solution = model.maximizeScore();
        solution.printSolution();
    }

    public void update(){
        scoreLabel.setText(String.format("Score: %d", model.getScore()));
        view.update();
    }

    public void onCandyClicked(MouseEvent me){
        int candyIndex = view.getIndexOfClicked(me);
        model.candyWithIndexSelected(Position.fromIndex(candyIndex,model.getBoardsize()));
        update();
    }

    public void onStartClicked(MouseEvent me){
        if(textInput.getText().equals("Aue")){
            if (!model.isLoggedIn()){
                model.goodLogIn();
            }
        }
        update();
    }

    public void onResetClicked(MouseEvent me){
        model.reset();
        update();
    }
    public static CandycrushModel createBoardFromString(String configuration) {
        var lines = configuration.toLowerCase().lines().toList();
        Boardsize size = new Boardsize(lines.size(), lines.getFirst().length());
        var model = new CandycrushModel("Speler", size.rows(), size.cols());
        for (int row = 0; row < lines.size(); row++) {
            var line = lines.get(row);
            for (int col = 0; col < line.length(); col++) {
                model.getSpeelbord().replaceCellAt(new Position(row, col, size), characterToCandy(line.charAt(col)));
            }
        }
        return model;
    }
    private static Candy characterToCandy(char c) {
        return switch(c) {
            case '.' -> null;
            case 'o' -> new NormalCandy(0);
            case '*' -> new NormalCandy(1);
            case '#' -> new NormalCandy(2);
            case '@' -> new NormalCandy(3);
            default -> throw new IllegalArgumentException("Unexpected value: " + c);
        };
    }
}
