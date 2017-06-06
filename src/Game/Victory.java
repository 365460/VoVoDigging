
package Game;
/**
 * Created by Rober on 2017/6/5.
 */
public class Victory {
    Game game;

    Victory(Game game){
       this.game = game;
    }

    public void display(){

    }

    public void saveGame(){
        game.saveGame(1);
    }
}
