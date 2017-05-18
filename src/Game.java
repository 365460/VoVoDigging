//import com.sun.deploy.net.proxy.pac.PACFunctions;
import processing.core.PApplet;
import Map.*;

/**
 * Created by Rober on 2017/5/5.
 */
public class Game {
    PApplet par;
    Map map;
    Player player;

    GameStatus gameStatus = GameStatus.DIGGING;

    int height, width;
    boolean isShifting;
    public Game(PApplet par, int height, int width){
        this.par = par;
        this.height = height;
        this.width = width;

        map = new Map(this.par, 8, 0);
        player = new Player(this.par, 3, 3, map);
    }

    public void draw(){
        switch (gameStatus){
            case DIGGING:
                map.display((int)player.pos.x, (int)player.pos.y);
                player.display();
                break;
        }
    }

    public void keyPressed(){

        switch (gameStatus){
            case DIGGING:
                if(par.keyCode == par.SHIFT) isShifting = true;
                int dir = 0;
                if(par.keyCode==par.UP || par.key=='w') dir = 1;
                else if(par.keyCode==par.RIGHT || par.key=='d') dir = 2;
                else if(par.keyCode==par.DOWN || par.key=='s') dir = 3;
                else if(par.keyCode==par.LEFT || par.key=='a') dir = 4;

                if(par.key=='q') player.putItem();

                else if(par.key == par.CODED){
                    player.ismoving = true;
                    player.dir = dir;
                    player.move(dir);
                }
                else player.digBlock(dir);

                break;
        }

    }

    public void keyReleased(){

        if(par.keyCode == par.SHIFT)
            isShifting = false;

        if(par.key == par.CODED){
            player.ismoving = false;
        }
    }
}

enum GameStatus {
    DIGGING,
    BAGOPEN
}

