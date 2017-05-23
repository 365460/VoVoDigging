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
    int isBag = 0;
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
                if(isBag==1) player.bag.display();
                break;
        }
    }

    public void keyPressed(){

        switch (gameStatus){
            case DIGGING:
                if(isBag==1){
                    if(par.key == 'b') isBag ^= 1;
                    player.bag.keyPressed(par.keyCode);
                    break;
                }
                else{
                    int dir = 0;
                    if(par.keyCode==par.UP         || par.key=='w' || par.key=='W') dir = 1;
                    else if(par.keyCode==par.RIGHT || par.key=='d' || par.key=='D') dir = 2;
                    else if(par.keyCode==par.DOWN  || par.key=='s' || par.key=='S') dir = 3;
                    else if(par.keyCode==par.LEFT  || par.key=='a' || par.key=='A') dir = 4;

                    if(par.key=='q') player.putItem();
                    else if(par.key=='b') isBag ^= 1;
                    else if(par.key == par.CODED){
                        player.ismoving = true;
                        player.dir = dir;
                        player.tryMove(dir);
                    }
                    else if(dir!=0){
                        System.out.println("dir = " + dir);
                        if(par.key>='A' && par.key<='Z') System.out.println("put " + dir);
                        if(par.key>='A' && par.key<='Z') player.putMine(dir);
                        else player.digBlock(dir);
                    }
                }
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
    ShOPPING,
    BAGOPEN
}

