import Setting.Setting;
import processing.core.PApplet;
import Game.*;
/**
 * Created by Rober on 2017/5/5.
 */
public class Main extends PApplet {

    Game game;

    @Override
    public void settings() {
         size(Setting.GameWidth, Setting.GameHeight); // width, height
    }

    public void setup(){
        game = new Game(this, height, width);
    }

    public void draw(){
        game.draw();
    }

    public void keyPressed(){
        if(keyCode==27){
            key = 0;
            game.keyPressed();
        }
        game.keyPressed();
    }

    public void keyReleased(){
        game.keyReleased();
    }

    public void mousePressed(){ game.mousePressed(); }

    public static void main(String _args[]) {
        PApplet.main(new String[]{ Main.class.getName()});
    }

}
