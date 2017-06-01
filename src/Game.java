//import com.sun.deploy.net.proxy.pac.PACFunctions;
import Reminder.Reminder;
import processing.core.PApplet;
import Map.*;
import Store.*;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Rober on 2017/5/5.
 */
public class Game {
    PApplet par;
    Map map;
    Player player;
    Store store;

    GameStatus gameStatus = GameStatus.LOADING;

    Queue<Reminder> Qre;
    Reminder nowReminder;
    boolean isReminder = false;
    Thread thCheck_Reminder;

    int height, width;
    boolean isShifting;
    int isBag = 0;


    int loadP = 0;
    String loadMessage;

    public Game(PApplet par, int height, int width){
        this.par = par;
        this.height = height;
        this.width = width;

        Thread load = new Thread(new Runnable() {
            @Override
            public void run() {
                loadMessage = "loading map....";
                map    = new Map(par, 8, 0);
                loadP = 30;

                loadMessage = "loading player....";
                player = new Player(par, 3, 3, map);
                loadP = 60;

                loadMessage = "loading store....";
                store  = new Store(par, par.height, width, player.bag);
                loadP = 100;

                gameStatus = GameStatus.DIGGING;
            }
        });
        load.start();

        Qre = new LinkedList<>();
        thCheck_Reminder = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    if(Qre.size()>0){
                        nowReminder = Qre.poll();
                        try {
                            isReminder = true;
                            Thread.sleep(1000);
                            isReminder = false;
                        } catch (Exception e) {}
                    }

                    try{
                        Thread.sleep(1);
                    }catch (Exception e){

                    }
                }
            }
        });
        thCheck_Reminder.start();
    }

    public void draw(){
        switch (gameStatus){
            case LOADING:
                par.textSize(40);
                par.background(0);
                par.text("LOADING.....(" + loadP + " %)", 50, 50);
                par.text(loadMessage,  50, 100);
                break;

            case DIGGING:
                map.display((int)player.pos.x, (int)player.pos.y, player.light);
                player.display();
                break;

            case BAGMINE:
                map.display((int)player.pos.x, (int)player.pos.y, player.light);
                player.display();
                player.displayMineBag();
                break;

            case BAGTOOL:
                map.display((int)player.pos.x, (int)player.pos.y, player.light);
                player.display();
                player.displayToolBag();
                break;

            case SHOPPING:
                store.draw();
                break;
        }

        if(isReminder) nowReminder.display();
    }

    public void keyPressed(){
        try{
            switch (gameStatus){
                case SHOPPING:
                    if(par.key == 'o') gameStatus = GameStatus.DIGGING;
                    break;

                case BAGMINE:
                    if(par.key == 'b') gameStatus = GameStatus.DIGGING;
                    else player.bag.keyPressed(par.keyCode, 0);
                    break;

                case BAGTOOL:
                    if(par.key == 't') gameStatus = GameStatus.DIGGING;
                    else player.bag.keyPressed(par.keyCode, 1);
                    break;

                case DIGGING:
                    if(par.key == 'b')       gameStatus = GameStatus.BAGMINE;
                    else if(par.key == 't' ) gameStatus = GameStatus.BAGTOOL;
                    else if(par.key == 'p')  gameStatus = GameStatus.SHOPPING;
                    else{
                        int dir = 0;
                        if(par.keyCode==par.UP         || par.key=='w' || par.key=='W') dir = 1;
                        else if(par.keyCode==par.RIGHT || par.key=='d' || par.key=='D') dir = 2;
                        else if(par.keyCode==par.DOWN  || par.key=='s' || par.key=='S') dir = 3;
                        else if(par.keyCode==par.LEFT  || par.key=='a' || par.key=='A') dir = 4;

                        if(par.key=='q') player.putItem();
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
        }catch(Reminder re){
            if(Qre.size()<2)
                Qre.offer(re);
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
            case SHOPPING:
                store.mousePressed();
                break;
        }
    }
}

enum GameStatus {
    LOADING,
    DIGGING,
    SHOPPING,
    BAGMINE,
    BAGTOOL
}

