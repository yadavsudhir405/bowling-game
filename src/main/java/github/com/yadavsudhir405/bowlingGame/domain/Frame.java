package github.com.yadavsudhir405.bowlingGame.domain;

import github.com.yadavsudhir405.bowlingGame.constants.BonusType;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author sudhir
 *         Date:6/5/17
 *         Time:8:01 AM
 *         Project:bowling-game
 */
public class Frame {
    private static final int TOTAL_PINNED_BALLS_AT_STARTING_OF_FRAME=10;
    private static final int HALF_NUMBER_OF_BALLS=5;
    private Queue<Rolls> rollses=new ArrayDeque<>(2);
    private BonusType bonusType=BonusType.NONE;
    private int[] scores=new int[2];
    int chance=0;
    private int pinnedUpBalls=TOTAL_PINNED_BALLS_AT_STARTING_OF_FRAME;
    private int pinnedDownBalls;
    private int cumulativeScoreSoFar=0;
    {
        rollses.add(new Rolls());
        rollses.add(new Rolls());
    }
    public void pinBalls(int n){
        if(n==TOTAL_PINNED_BALLS_AT_STARTING_OF_FRAME&&chance==0){
            setFrameEligibleForStrikeBonus();
            rollses.poll().roll(n);
            pinnedDownBalls=pinnedDownBalls+n;
            pinnedUpBalls=pinnedUpBalls-n;
            scores[chance++]=n;
        }else if(n==HALF_NUMBER_OF_BALLS){
            rollses.poll().roll(n);
            setFrameEligibleForFrameBonus();
            pinnedDownBalls=pinnedDownBalls+n;
            pinnedUpBalls=pinnedDownBalls-n;
            scores[chance++]=n;
        }else {
            rollses.poll().roll(n);
            pinnedDownBalls=pinnedDownBalls+n;
            pinnedUpBalls=pinnedDownBalls-n;
            scores[chance++]=n;
        }
    }
    private void setFrameEligibleForStrikeBonus(){
        bonusType=BonusType.STRIKE;
    }
    private  void setFrameEligibleForFrameBonus(){
        bonusType=BonusType.SPARE;
    }
    public boolean isFramesTotalChanceExhausted(){
        return (this.bonusType==BonusType.STRIKE||rollses.isEmpty())==true?true:false;
    }

    public boolean eligibleForStrikeBonus(){
        return bonusType==BonusType.STRIKE;
    }
    public boolean eligibleForFrameBonus(){
        return bonusType==BonusType.SPARE;
    }
    public void updateCumulativeScoreBoardWithBonusPoints(int bonusPoints){
        this.cumulativeScoreSoFar+=bonusPoints;
    }
    public void updateCumulativeScoreBoardFromPreviousFrame(Frame previousFrame){
        int scoresIndividuallyInChances=scores[0]+scores[1];
        this.cumulativeScoreSoFar=previousFrame.cumulativeScoreSoFar+scoresIndividuallyInChances;
    }
    public int getBonusPointsForPreviousFotFrame(Frame frame){
        if(frame.eligibleForFrameBonus()){
            return scores[0];
        }else if(frame.eligibleForStrikeBonus()){
            return scores[0]+scores[1];
        }else {
            return 0;
        }
    }
    public void initCumulativeScoreBoard(){
        this.cumulativeScoreSoFar=scores[0]+scores[1];
    }
    int getCumulativeScoreSoFar(){
        return cumulativeScoreSoFar;
    }
}
