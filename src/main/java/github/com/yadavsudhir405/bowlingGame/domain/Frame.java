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

    private Queue<Rolls> rolls;
    private BonusType bonusType=BonusType.NONE;
    private int[] scores;
    int maxAllowedChances;
    int chance=0;
    private int pinnedUpBalls=TOTAL_PINNED_BALLS_AT_STARTING_OF_FRAME;
    private int pinnedDownBalls;
    private int cumulativeScoreSoFar=0;

    public Frame(int maxAllowedChances){
        this.maxAllowedChances=maxAllowedChances;
        this.rolls =new ArrayDeque<>(maxAllowedChances);
        this.scores=new int[maxAllowedChances];

        int i=0;
        while(i<maxAllowedChances){
            rolls.add(new Rolls());
            i++;
        }
    }

    public void pinBalls(int n){
        if(thisIsStrikeHit(n)){
            setFrameEligibleForStrikeBonus();
            updateScoresAndPinnedUpAndDownBalls(n);
        }else if(thisIsFrameHit(n)){
            setFrameEligibleForFrameBonus();
            updateScoresAndPinnedUpAndDownBalls(n);
        }else {
            justRollIt(n);
        }
    }

    private void justRollIt(int n) {
        rolls.poll().roll(n);
        pinnedDownBalls=pinnedDownBalls+n;
        pinnedUpBalls=pinnedUpBalls-n;
        scores[chance++]=n;
    }

    private void updateScoresAndPinnedUpAndDownBalls(int n){
        rolls.poll().roll(n);
        pinnedDownBalls=pinnedDownBalls+n;
        pinnedUpBalls=pinnedUpBalls-n;
        scores[chance++]=n;
    }

    private boolean thisIsFrameHit(int n) {
        if(pinnedUpBalls==n && lastChanceOfThisFrame()){
            return true;
        }else {
            return false;
        }
    }

    private boolean lastChanceOfThisFrame(){
        return (chance==maxAllowedChances-1)?true:false;
    }

    private boolean thisIsStrikeHit(int n){
        if(n==TOTAL_PINNED_BALLS_AT_STARTING_OF_FRAME && firstChanceOfThisFrame()){
            return true;
        }else{
            return false;
        }
    }
    private boolean firstChanceOfThisFrame(){
        return chance==0?true:false;
    }
    private void setFrameEligibleForStrikeBonus(){
        bonusType=BonusType.STRIKE;
    }
    private  void setFrameEligibleForFrameBonus(){
        bonusType=BonusType.SPARE;
    }
    public boolean isFramesTotalChanceExhausted(){
        return (this.bonusType==BonusType.STRIKE|| rolls.isEmpty())==true?true:false;
    }

    public boolean eligibleForStrikeBonus(){
        return bonusType==BonusType.STRIKE;
    }
    public boolean eligibleForFrameBonus(){
        return bonusType==BonusType.SPARE;
    }
    public void updateCumulativeScoreBoardWithBonusPoints(int bonusPoints){
        this.cumulativeScoreSoFar=this.cumulativeScoreSoFar+bonusPoints;
    }
    public void updateCumulativeScoreBoardFromPreviousFrame(Frame previousFrame){
        int scoresIndividuallyInChances=scores[0]+scores[1];
        this.cumulativeScoreSoFar=previousFrame.cumulativeScoreSoFar+scoresIndividuallyInChances;
    }
    public int getBonusPointsForPreviousFotFrame(Frame frame){
        if(frame.eligibleForFrameBonus()){
            return scores[0];
        }else if(frame.eligibleForStrikeBonus()){
            int totalScores=0;
            for(int i=0;i<scores.length;i++){
                totalScores=totalScores+scores[i];
            }
            return totalScores;
        }else {
            return 0;
        }
    }
    public void initCumulativeScoreBoard(){
        int totalScores=0;
        for(int i=0;i<scores.length;i++){
            totalScores=totalScores+scores[i];
        }
        this.cumulativeScoreSoFar=totalScores;
    }

    int getCumulativeScoreSoFar(){
        return cumulativeScoreSoFar;
    }
}
