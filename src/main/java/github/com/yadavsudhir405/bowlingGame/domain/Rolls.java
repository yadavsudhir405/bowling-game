package github.com.yadavsudhir405.bowlingGame.domain;

/**
 * @author sudhir
 *         Date:6/5/17
 *         Time:8:16 AM
 *         Project:bowling-game
 */
public class Rolls {
    int score;
    public void roll(int n){
        score=n;
        //System.out.println("Rolled "+n+" balls down");
    }
    public int getScore(){
        return score;
    }
}
