package github.com.uadavsudhir405.bowlinGame;

import github.com.yadavsudhir405.bowlingGame.domain.Game;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author sudhir
 *         Date:5/5/17
 *         Time:12:57 PM
 *         Project:bowling-game
 */
public class BowlingGameTest  {

    @Rule
    public ExpectedException thrown=ExpectedException.none();

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
    public void testWithOneSpareAndAllOnes(){
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
        Assertions.assertThat(game.score()).isEqualTo(190);
    }

    @Test
    public void testForNotAllowedRollsBallsShouldThrowRuntimeException() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Not Allowed to roll balls now");
        for(int i=0;i<20;i++){
            game.roll(1);
        }
        game.roll(1);
    }
    @Test
    public void testWithLastFrameAsStrikeAndKeeprollingballsFourTimesShouldThrowRuntimeException(){
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Not Allowed to roll balls now");
        for(int i=0;i<18;i++){
            game.roll(1);
        }
        game.roll(10);
        game.roll(1);
        game.roll(1);
        game.roll(1);
        game.roll(1);
    }
    @Test
    public void test1WithLastFrameAsStrikeAndKeeprollingballsFourTimesShouldThrowRuntimeException(){
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Not Allowed to roll balls now");
        for(int i=0;i<18;i++){
            game.roll(1);
        }
        game.roll(10);
        game.roll(10);
        game.roll(1);
    }
    @Test
    public void testWithLastFrameAsSpareAndKeeprollingballsFourTimesShouldThrowRuntimeException(){
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Not Allowed to roll balls now");
        for(int i=0;i<18;i++){
            game.roll(1);
        }
        game.roll(6);
        game.roll(4);
        game.roll(1);
        game.roll(1);
        game.roll(1);
        game.roll(1);
    }

    @Test
    public void testAllStrikesWithExtraFrameAsStrike(){
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Not Allowed to roll balls now");
        for(int i=0;i<10;i++){
            game.roll(10);
        }
        game.roll(10);
        Assertions.assertThat(game.score()).isEqualTo(210);
    }
    @Test
    public void testAllStrikesWithExtraFrameAsStrikeWithExtraRollShouldThrowRuntimeException(){
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Not Allowed to roll balls now");
        for(int i=0;i<10;i++){
            game.roll(10);
        }
        game.roll(10);
        game.roll(1);
    }
    @Test
    public void testAllWithAllSpareAsStrikeWithExtraRollShouldThrowRuntimeException(){
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Not Allowed to roll balls now");
        for(int i=0;i<20;i++){
            if(i%2==0){
                game.roll(6);
            }else {
                game.roll(4);
            }
        }
        game.roll(10);
    }
    @Test
    public void testAllWithAllSpareWithExtraRollShouldReturnCorrectScore(){
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Not Allowed to roll balls now");
        for(int i=0;i<20;i++){
            if(i%2==0){
                game.roll(6);
            }else {
                game.roll(4);
            }
        }
        game.roll(1);
        game.roll(3);
        game.roll(5);

    }

    @Test
    public void testWithRandomInputs(){
        game.roll(1);
        game.roll(4);

        game.roll(4);
        game.roll(5);

        game.roll(6);
        game.roll(4);

        game.roll(5);
        game.roll(5);

        game.roll(10);

        game.roll(0);
        game.roll(1);

        game.roll(7);
        game.roll(3);

        game.roll(6);
        game.roll(4);

        game.roll(10);

        game.roll(2);
        game.roll(2);
        game.roll(6);

        Assertions.assertThat(game.score()).isEqualTo(121);
    }

}
