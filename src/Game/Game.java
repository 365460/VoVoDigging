//import com.sun.deploy.net.proxy.pac.PACFunctions;
package Game;
import Reminder.Reminder;
import Setting.Setting;
import Upgrade.Upgrade;
import processing.core.PApplet;
import Setting.*;
import Map.*;
import Store.*;
import Menu.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by Rober on 2017/5/5.
 */
public class Game {
    PApplet par;

    Map map;
    Player player;

    Store store;
    Upgrade upgrade;

    Log log;
    Menu menu;

    Loading loading;
    SettingTab settingtab;

    GameStatus gameStatus = GameStatus.MENU;

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
        this.par    = par;
        this.height = height;
        this.width  = width;
        this.menu   = new Menu(par, this);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                loading = new Loading(par);
            }
        });
        thread.start();

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
                            Thread.sleep(700);
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

    public void initNewGame(){ // call by menu

        gameStatus = GameStatus.LOADING;

        loading.clean();
        Game game = this; // for build settingTab
        Thread load = new Thread(new Runnable() {
            @Override
            public void run() {
                loading.setMessage("loading map....");
                log    = new Log(par);
                map    = new Map(par, 10, 0);
                loading.setProgress(20);

                loading.setMessage("loading setting....");
                settingtab = new SettingTab(par, game);
                loading.setProgress(30);

                loading.setMessage("loading player....");
                player = new Player(par, 6,Setting.HeightSpaceNum-1, map, log);
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

    }

    public void initOldGame(String pre){ // call by menu

        gameStatus = GameStatus.LOADING;

        loading.clean();
        Game game = this;
        Thread load = new Thread(new Runnable() {
            @Override
            public void run() {
                loading.setMessage("loading map....");
                log    = new Log(par);
                map    = new Map(par, 10, 0);
                loading.setProgress(20);

                loading.setMessage("loading setting....");
                settingtab = new SettingTab(par, game);
                loading.setProgress(30);

                loading.setMessage("loading player....");
                player = new Player(par, 6,Setting.HeightSpaceNum-1, map, log);
                loading.setProgress(50);

                loading.setMessage("loading store...");
                store  = new Store(par, par.height, width, player.bag);
                loading.setProgress(80);

                loading.setMessage("loading Upgrade...");
                upgrade = new Upgrade(par, par.height, width, player.bag);
                loading.setProgress(90);

                loading.setMessage("reading from recorder...");
                map.read(pre, player);
                player.read(pre);
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
    }

    public void draw(){
        switch (gameStatus){
            case MENU:
                menu.display();
                break;

            case SETTING:
                map.display((int)player.pos.x, (int)player.pos.y, player.getLight(), player.pos);
                player.display();
                settingtab.display();
                break;

            case LOADING:
                loading.display();
                break;

            case DIGGING:
                map.display((int)player.pos.x, (int)player.pos.y, player.getLight(), player.pos);
                player.display();
                break;

            case BAGMINE:
                map.display((int)player.pos.x, (int)player.pos.y, player.getLight(), player.pos);
                player.display();
                player.displayMineBag();
                break;

            case BAGTOOL:
                map.display((int)player.pos.x, (int)player.pos.y, player.getLight(), player.pos);
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
                par.textAlign(par.LEFT);
                par.textSize(40);
                par.text("Victory", 50, 50);
                log.display();
                break;
        }
        if(isReminder) nowReminder.display();
    }

    public void keyPressed(){
        System.out.println("keycode = " + par.keyCode);
        try{
            switch (gameStatus){
                case SETTING:
                    int key = par.keyCode;
                    par.keyCode = 0;
                    settingtab.keyPressed(key);
                    break;

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
                    else if(par.keyCode == 27) {
                        par.keyCode = 0;
                        settingtab.open();
                        gameStatus = GameStatus.SETTING;
                    }
                    else{
                        int dir = 0;
                        if(par.keyCode==par.UP         || par.key=='w' || par.key=='W') dir = 1;
                        else if(par.keyCode==par.RIGHT || par.key=='d' || par.key=='D') dir = 2;
                        else if(par.keyCode==par.DOWN  || par.key=='s' || par.key=='S') dir = 3;
                        else if(par.keyCode==par.LEFT  || par.key=='a' || par.key=='A') dir = 4;

                        if(player.isIdle()==false) return; // so busy

                        if(par.key=='q') player.putItem();
                        else if(par.key == par.CODED){

                            if(dir==1 && map.atShop(player.pos)) gameStatus = GameStatus.SHOPPING;
                            else if(dir==1 && map.atUpgrade(player.pos)) gameStatus = GameStatus.UPGRADE;
                            else{
                                player.dir = dir;
                                boolean win = player.tryMove(dir);
                                if(win){
                                    gameStatus = GameStatus.VICTORY;
                                    saveGame(1);
                                }
                            }
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

    public void keyReleased(){}

    public void mousePressed(){
        try{
            switch (gameStatus){
                case MENU:
                    menu.mousePressed();
                    break;
                case SETTING:
                    settingtab.mousePressed();
                    break;

                case SHOPPING:
                    store.mousePressed();
                    break;
                case UPGRADE:
                    upgrade.mousePressed();
                    break;
            }
        }
        catch(Reminder re){
            if(Qre.size()<2){
                Qre.offer(re);
            }
        }
    }

    public void saveGame(int id){
        gameStatus = GameStatus.DIGGING;
        try{
            Thread.sleep(500);
        }catch(Exception e){

        }

        map.save("keep/K" + id , player);
        System.out.println("map save OK");

        player.save("keep/K" + id);
        System.out.println("player save OK");

        par.saveFrame("keep/K" + id + "/img.png");
        System.out.println("img save OK");

        try{
            FileReader f = new FileReader("keep/keep");
            BufferedReader br = new BufferedReader(f);

            String[] keeps = new String[6];
            for(int i=0; i<6; i++){
                keeps[i] = br.readLine();
            }
            f.close();

            keeps[(id-1)*2] = "1";
//            keeps[(id-1)*2+1] = DateFormat.getDateTimeInstance().format( new Date(System.currentTimeMillis()) );
            keeps[(id-1)*2+1] = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(System.currentTimeMillis()) ;


            FileWriter fw = new FileWriter("keep/keep");
            for(int i=0; i<6; i++){
                fw.write( keeps[i]+"\n" );
            }
            fw.close();

        }catch(IOException e){

        }
        System.out.println("keep save OK");

        Qre.offer(new Reminder(par, "Save successfully"));
    }

    public void goToDigging(){
        gameStatus = GameStatus.DIGGING;
    }

    public void goToMenu(){
        menu.toNormal();
        gameStatus = GameStatus.MENU;
    }
}

enum GameStatus {
    MENU,
    SETTING,

    LOADING,
    DIGGING,

    SHOPPING,
    UPGRADE,

    BAGMINE,
    BAGTOOL,

    VICTORY
}

