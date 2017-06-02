//import com.sun.deploy.net.proxy.pac.PACFunctions;
import Reminder.Reminder;
import Setting.Setting;
import Upgrade.Upgrade;
import processing.core.PApplet;
import Map.*;
import Store.*;

import java.util.EmptyStackException;
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
    Upgrade upgrade;

    Loading loading;

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
        loading = new Loading(par);

        Thread load = new Thread(new Runnable() {
            @Override
            public void run() {
                loading.setMessage("loading map....");
                map    = new Map(par, 8, 0);
                loading.setProgress(30);

                loading.setMessage("loading player....");
                player = new Player(par, Setting.HeightSpaceNum-1,Setting.HeightSpaceNum-1, map);
                loading.setProgress(60);

                loading.setMessage("loading store...");
                store  = new Store(par, par.height, width, player.bag);
                loading.setProgress(80);

                loading.setMessage("loading Upgrade...");
                upgrade = new Upgrade(par, par.height, width, player.bag);
                loading.setProgress(100);

                while(loading.isOk()==false){
                    try{
                        Thread.sleep(10);
                    }catch (Exception e){}
                }
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
                            Thread.sleep(nowReminder.getDelay());
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
                loading.display();
                break;

            case DIGGING:
                map.display((int)player.pos.x, (int)player.pos.y, player.getLight());
                player.display();
                break;

            case BAGMINE:
                map.display((int)player.pos.x, (int)player.pos.y, player.getLight());
                player.display();
                player.displayMineBag();
                break;

            case BAGTOOL:
                map.display((int)player.pos.x, (int)player.pos.y, player.getLight());
                player.display();
                player.displayToolBag();
                break;

            case SHOPPING:
                store.draw();
                break;

            case UPGRADE:
                upgrade.draw();
                break;

            case VICTORY:
                par.background(0);
                par.textSize(40);
                par.text("Victory", 50, 50);
                break;
        }

        if(isReminder) nowReminder.display();
    }

    public void keyPressed(){
        try{
            switch (gameStatus){
                case LOADING:
                    break;

                case UPGRADE:
                    if(par.key == 'o') gameStatus = GameStatus.DIGGING;
                    break;

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
                    else if(par.key == 'u')  gameStatus = GameStatus.UPGRADE;
                    else{
                        int dir = 0;
                        if(par.keyCode==par.UP         || par.key=='w' || par.key=='W') dir = 1;
                        else if(par.keyCode==par.RIGHT || par.key=='d' || par.key=='D') dir = 2;
                        else if(par.keyCode==par.DOWN  || par.key=='s' || par.key=='S') dir = 3;
                        else if(par.keyCode==par.LEFT  || par.key=='a' || par.key=='A') dir = 4;

                        if(player.isIdle()==false) return; // so busy

                        if(par.key=='q') player.putItem();
                        else if(par.key == par.CODED){
                            player.dir = dir;
                            boolean win = player.tryMove(dir);
                            if(win) gameStatus = GameStatus.VICTORY;
                        }
                        else if(dir!=0){
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
    }

    public void mousePressed(){
        switch (gameStatus){
            case SHOPPING:
                store.mousePressed();
                break;
            case UPGRADE:
                upgrade.mousePressed();
                break;
        }
    }
}

enum GameStatus {
    LOADING,
    DIGGING,
    SHOPPING,
    UPGRADE,
    BAGMINE,
    BAGTOOL,
    VICTORY
}

