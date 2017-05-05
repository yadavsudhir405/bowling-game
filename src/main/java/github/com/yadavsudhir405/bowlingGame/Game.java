package github.com.yadavsudhir405.bowlingGame;

/**
 * @author sudhir
 *         Date:5/5/17
 *         Time:1:02 PM
 *         Project:bowling-game
 */
public class Game {
    int score=0;
    public void roll(int pins){
        score=score+pins;
    }
    public int score(){
        return score;
    }
}
