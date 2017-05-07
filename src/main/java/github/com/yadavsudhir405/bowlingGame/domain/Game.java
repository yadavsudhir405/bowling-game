package github.com.yadavsudhir405.bowlingGame.domain;

/**
 * @author sudhir
 *         Date:5/5/17
 *         Time:1:02 PM
 *         Project:bowling-game
 */
public class Game {

    Frame[] frames=new Frame[10];
    int currentFrameIndex=0;
    {
        for(int i=0;i<10;i++){
            frames[i]=new Frame();
        }
    }
    public void roll(int pins){
        Frame currentFrame=frames[currentFrameIndex];
        currentFrame.pinBalls(pins);
        if(isMakingTransitionToNextFrame()){
            if(currentFrameIndex==0){
                currentFrame.initCumulativeScoreBoard();
            }else{
                doScoreSettlement(frames[currentFrameIndex-1],frames[currentFrameIndex]);
            }
        }
        if(currentFrame.isFramesTotalChanceExhausted()){
            currentFrameIndex++;
        }
    }
    
    boolean isMakingTransitionToNextFrame(){
        Frame currentFrame=frames[currentFrameIndex];
        return (currentFrame.eligibleForStrikeBonus()||currentFrame.isFramesTotalChanceExhausted())==true?true:false;
    }
    private void doScoreSettlement(Frame previousFrame,Frame currentFrame){
        int bonusPoints=currentFrame.getBonusPointsForPreviousFotFrame(previousFrame);
        previousFrame.updateCumulativeScoreBoardWithBonusPoints(bonusPoints);
        currentFrame.updateCumulativeScoreBoardFromPreviousFrame(previousFrame);
    }
    public int score(){
        if(currentFrameIndex>=10){
            return frames[9].getCumulativeScoreSoFar();
        }else {
            return frames[currentFrameIndex].getCumulativeScoreSoFar();
        }
    }

}
