//import com.sun.deploy.net.proxy.pac.PACFunctions;
import processing.core.PApplet;
import Map.*;
import Store.*;

/**
 * Created by Rober on 2017/5/5.
 */
public class Game {
    PApplet par;
    Map map;
    Player player;
    Store store;

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

        store = new Store(this.par, this.par.height, this.width, player.bag);
    }

    public void draw(){
        switch (gameStatus){
            case DIGGING:
                map.display((int)player.pos.x, (int)player.pos.y);
                player.display();
                if(isBag==1) player.bag.display();
                break;
            case ShOPPING:
                store.draw();
        }
    }

    public void keyPressed(){

        switch (gameStatus){
            case ShOPPING:
//                store.keyPressed();
                if(par.key == 'o') gameStatus = GameStatus.DIGGING;
                break;
            case DIGGING:
                if(isBag==1){
                    if(par.key == 'b') isBag ^= 1;
                    player.bag.keyPressed(par.keyCode);
                    break;
                }
                else{

                    if(par.key =='p') {
                        gameStatus = GameStatus.ShOPPING;
                       return;
                    }
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

    public void mousePressed(){
        switch (gameStatus){
            case ShOPPING:
                store.mousePressed();
                break;
        }
    }
}

enum GameStatus {
    DIGGING,
    ShOPPING,
    BAGOPEN
}

