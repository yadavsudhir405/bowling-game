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
    int currentFrameIndex=0;
    {
        for(int i=0;i<9;i++){
            frames.add(new Frame(2));
        }
        frames.add(new Frame(2));
    }
    public void roll(int pins){
        if(tenthFrameOver()&&tenthFrameIsEligibleForExtrallRoll&&frames.get(9).isFramesTotalChanceExhausted()&&frames
                .get(9).allBallsAreNotDown()){
            currentFrameIndex=9;
        }else if(tenthFrameOver()&&tenthFrameIsEligibleForExtrallRoll&&!frames.get(9).allBallsAreNotDown()){
            throw new RuntimeException("Not Allowed to roll balls now");
        }else if(tenthFrameOver()&&!tenthFrameIsEligibleForExtrallRoll){
            throw new RuntimeException("Not Allowed to roll balls now");
        }else{
            Frame currentFrame=frames.get(currentFrameIndex);

            if(currentFrameIsTenthFrame()&&currentFrame.allBallsAreNotDown()&&currentFrame
                    .isFramesTotalChanceExhausted()&&tenthFrameIsEligibleForExtrallRoll){
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
                if(currentFrameisNinthFrame()&& eligibleForExtraBallRoll()){
                    tenthFrameIsEligibleForExtrallRoll=true;
                }
                if(currentFrameIndex==9&&tenthFrameIsEligibleForExtrallRoll&&currentFrame.allBallsAreNotDown()){

                }else{
                    currentFrameIndex++;
                }

            }
        }

    }

    private void rollExtraBall(Frame tenthFrame,int pinnedBalls) {
        int cumulativeScoredBoard=tenthFrame.getCumulativeScoreSoFar();
        tenthFrame.addExtraScoreToCumulativeScore(pinnedBalls);
    }

    private boolean currentFrameIsTenthFrame() {
        return currentFrameIndex==9?true:false;
    }

    private boolean eligibleForExtraBallRoll() {
        Frame frame=frames.get(currentFrameIndex);
        return (frame.eligibleForStrikeBonus()||frame.eligibleForSpareBonus())?true:false;
    }

    private boolean currentFrameisNinthFrame() {
        return currentFrameIndex==8?true:false;
    }


    private boolean tenthFrameOver(){
        return currentFrameIndex>9? true:false;
    }
    private boolean isMakingTransitionToNextFrame(){
        Frame currentFrame=frames.get(currentFrameIndex);
        return (currentFrame.eligibleForStrikeBonus()||currentFrame.isFramesTotalChanceExhausted())==true?true:false;
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
