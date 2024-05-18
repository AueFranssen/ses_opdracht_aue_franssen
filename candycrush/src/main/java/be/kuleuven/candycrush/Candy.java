package be.kuleuven.candycrush;

public sealed interface Candy permits NormalCandy,Jawbreaker,Worm,Cherry,Gumball{
}
record NormalCandy(int colour) implements Candy{
    NormalCandy {
        if(colour < 0 || colour > 3) throw new IllegalArgumentException("Value is " + colour);
    }
}
record Jawbreaker() implements Candy{}
record Worm() implements Candy{}
record Cherry() implements Candy{}
record Gumball() implements Candy{}