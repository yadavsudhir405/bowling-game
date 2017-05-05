package github.com.uadavsudhir405.bowlinGame;

import github.com.yadavsudhir405.bowlingGame.Game;
import junit.framework.TestCase;
import org.assertj.core.api.Assertions;

/**
 * @author sudhir
 *         Date:5/5/17
 *         Time:12:57 PM
 *         Project:bowling-game
 */
public class BowlingGameTest extends TestCase {

    private static  Game game;

    @Override
    public void setUp() throws Exception {
        game=new Game();

    }

    public void testGutterGame() throws Exception{

        for(int i=0;i<20;i++){
            game.roll(0);

        }
        Assertions.assertThat(game.score()).isEqualTo(0);
    }
    public void testAllOnes(){
        for(int i=0;i<20;i++){
            game.roll(1);
        }
        Assertions.assertThat(game.score()).isEqualTo(20);
    }


}
