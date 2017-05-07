package github.com.uadavsudhir405.bowlinGame;

import github.com.yadavsudhir405.bowlingGame.domain.Game;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

/**
 * @author sudhir
 *         Date:5/5/17
 *         Time:12:57 PM
 *         Project:bowling-game
 */
public class BowlingGameTest  {

    private static  Game game;

    @Before
    public void setUp() throws Exception {
        game=new Game();

    }
    @Test
    public void testGutterGame() throws Exception{

        for(int i=0;i<20;i++){
            game.roll(0);

        }
        Assertions.assertThat(game.score()).isEqualTo(0);
    }
    @Test
    public void testAllOnes(){
        for(int i=0;i<20;i++){
            game.roll(1);
        }
        Assertions.assertThat(game.score()).isEqualTo(20);
    }
    @Test
    public void testWithOneSpareAndAllones(){
        game.roll(4);
        game.roll(6);
        for(int i=0;i<18;i++){
            game.roll(1);
        }
        Assertions.assertThat(game.score()).isEqualTo(29);
    }
    @Test
    public void testAllStrikes(){
        for(int i=0;i<10;i++){
            game.roll(10);
        }
        Assertions.assertThat(game.score()).isEqualTo(200);
    }


}
