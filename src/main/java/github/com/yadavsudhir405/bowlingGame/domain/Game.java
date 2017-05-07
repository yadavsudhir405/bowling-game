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
    Frame extraBonusFrame=new Frame(3);
    int currentFrameIndex=0;
    {
        for(int i=0;i<10;i++){
            frames.add(new Frame(2));
        }
    }
    public void roll(int pins){
        if(tenthFrameOver()){
            checkForExtraFrame(pins);
            return;
        }
        Frame currentFrame=frames.get(currentFrameIndex);
        currentFrame.pinBalls(pins);
        if(isMakingTransitionToNextFrame()){
            if(currentFrameIndex==0){
                currentFrame.initCumulativeScoreBoard();
            }else{
                doScoreSettlement(frames.get(currentFrameIndex-1),frames.get(currentFrameIndex));
            }
        }
        if(currentFrame.isFramesTotalChanceExhausted()){
            currentFrameIndex++;
        }

    }

    private boolean checkForExtraFrame(int pins) {
        Frame tenthFrame=frames.get(9);
        if(tenthFrame.eligibleForStrikeBonus()){
            return true;
        }else if(tenthFrame.eligibleForFrameBonus()){
            return true;
        }else {
            return false;
        }
    }

    private boolean tenthFrameOver(){
        return currentFrameIndex>9?true:false;
    }
    boolean isMakingTransitionToNextFrame(){
        Frame currentFrame=frames.get(currentFrameIndex);
        return (currentFrame.eligibleForStrikeBonus()||currentFrame.isFramesTotalChanceExhausted())==true?true:false;
    }
    private void doScoreSettlement(Frame previousFrame,Frame currentFrame){
        int bonusPoints=currentFrame.getBonusPointsForPreviousFotFrame(previousFrame);
        previousFrame.updateCumulativeScoreBoardWithBonusPoints(bonusPoints);
        currentFrame.updateCumulativeScoreBoardFromPreviousFrame(previousFrame);
    }
    public int score(){
        if(currentFrameIndex>=10){
            return frames.get(9).getCumulativeScoreSoFar();
        }else {
            return frames.get(currentFrameIndex).getCumulativeScoreSoFar();
        }
    }

}
