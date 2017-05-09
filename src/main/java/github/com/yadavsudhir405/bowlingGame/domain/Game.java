package github.com.yadavsudhir405.bowlingGame.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sudhir
 *         Date:5/5/17
 *         Time:1:02 PM
 *         Project:bowling-game
 */
public class Game {

    List<Frame> frames=new ArrayList<>();
    private boolean tenthFrameIsEligibleForExtrallRoll;
    private int currentFrameIndex=0;
    public Game(){
        for(int i=0;i<9;i++){
            frames.add(new Frame(2));
        }
        frames.add(new Frame(2));
    }
    public void roll(int pins){
        if(gameOver()){
            throw new RuntimeException("Not Allowed to roll balls now");
        }else{
            playGame(pins);
        }

    }

    private void playGame(int pins) {
        Frame currentFrame=frames.get(currentFrameIndex);

        if(tenthFrameEligibleToPlayExtraRollAndAllBallsAreNotDown(currentFrame)){
            rollExtraBall(currentFrame,pins);
            tenthFrameIsEligibleForExtrallRoll=false;
            return;
        }

        currentFrame.pinBalls(pins);
        if(isMakingTransitionToNextFrame()){
            if(currentFrameIndex==0){
                currentFrame.initCumulativeScoreBoard();
            }else{
                doScoreSettlement(frames.get(currentFrameIndex-1),frames.get(currentFrameIndex));
            }
            if(ninthFrameAndItsStrikeOrSpare(currentFrame)){
                setTenthFrameEligibleToPlayExtraBall();
            }
            if(!tenthFrameAllBallsAreNotDownButEligibleToPlayExtraBall(currentFrame)){
                currentFrameIndex++;
            }

        }
    }
    private boolean tenthFrameEligibleToPlayExtraRollAndAllBallsAreNotDown(Frame currentFrame){
        return currentFrameIsTenthFrame()&&currentFrame.allBallsAreNotDown()&&currentFrame
                .isFramesTotalChanceExhausted()&&tenthFrameIsEligibleForExtrallRoll;
    }
    private void setTenthFrameEligibleToPlayExtraBall() {
        tenthFrameIsEligibleForExtrallRoll=true;
    }

    private boolean ninthFrameAndItsStrikeOrSpare(Frame currentFrame){
        return currentFrameisNinthFrame()&& eligibleForExtraBallRoll();
    }
    private boolean tenthFrameAllBallsAreNotDownButEligibleToPlayExtraBall(Frame currentFrame){
        return currentFrameIndex==9&&tenthFrameIsEligibleForExtrallRoll&&currentFrame.allBallsAreNotDown();
    }

    private boolean gameOver(){
        boolean isOver;
        if(tenthFrameFinishedEligibleToPlayExtraBallButAllBallsAreAlreadyDown()
                ||tenthFrameFinishedAndNotEligibleToPlayExtraBall()){
            isOver=true;
        }else{
            isOver=false;
        }
        return isOver;
    }
    private boolean tenthFrameFinishedEligibleToPlayExtraBallButAllBallsAreAlreadyDown(){
        return tenthFrameOver()&&tenthFrameIsEligibleForExtrallRoll&&frames.get(9).isFramesTotalChanceExhausted();
    }
    private boolean tenthFrameFinishedAndNotEligibleToPlayExtraBall(){
        return tenthFrameOver()&&!tenthFrameIsEligibleForExtrallRoll;
    }
    private void rollExtraBall(Frame tenthFrame,int pinnedBalls) {
        tenthFrame.addExtraScoreToCumulativeScore(pinnedBalls);
    }

    private boolean currentFrameIsTenthFrame() {
        return currentFrameIndex==9;
    }

    private boolean eligibleForExtraBallRoll() {
        Frame frame=frames.get(currentFrameIndex);
        return (frame.eligibleForStrikeBonus()||frame.eligibleForSpareBonus());
    }

    private boolean currentFrameisNinthFrame() {
        return currentFrameIndex==8;
    }


    private boolean tenthFrameOver(){
        return currentFrameIndex>9;
    }
    private boolean isMakingTransitionToNextFrame(){
        Frame currentFrame=frames.get(currentFrameIndex);
        return currentFrame.eligibleForStrikeBonus()||currentFrame.isFramesTotalChanceExhausted();
    }
    private void doScoreSettlement(Frame previousFrame,Frame currentFrame){
        int bonusPoints=currentFrame.getBonusPointsForPreviousFotFrame(previousFrame);
        previousFrame.updateCumulativeScoreBoardWithBonusPoints(bonusPoints);
        currentFrame.updateCumulativeScoreBoardFromPreviousFrame(previousFrame);
    }
    public int score(){
        int score;
        if(currentFrameIndex>9){
            score=frames.get(9).getCumulativeScoreSoFar();
        }else{
            score=frames.get(currentFrameIndex).getCumulativeScoreSoFar();
        }

       return score;
    }

}
