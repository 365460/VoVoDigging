//import com.sun.deploy.net.proxy.pac.PACFunctions;
package Game;
import Reminder.Reminder;
import Setting.Setting;
import Upgrade.Upgrade;
import ddf.minim.Minim;
import processing.core.PApplet;
import Setting.*;
import Map.*;
import Store.*;
import Menu.*;
import Battle.*;
import processing.core.PConstants;
import  ddf.minim.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.SynchronousQueue;

import static Setting.Setting.BlockSize;

/**
 * Created by Rober on 2017/5/5.
 */
public class Game {
    /*   music   */
    Minim minim ;
    AudioPlayer s0,s1,s2,s3,s4,s5;
    ArrayList<AudioPlayer> s= new ArrayList<>();
    int snum=6;
    Boolean open=false;

    PApplet par;

    MapDigging map;
    MapGround mapground;
    Player player;
    Battle battle;

    Store store;
    Upgrade upgrade;

    Log log;
    Menu menu;

    Loading loading;
    SettingTab settingtab;

    Victory victory;
    Lose lose;

    GameStatus gameStatus = GameStatus.MENU;
    GameStatus preStatus = GameStatus.MENU;

    Queue<Reminder> Qre;
    Reminder nowReminder;
    boolean isReminder = false;
    Thread thCheck_Reminder;

    int height, width;
    int hp  = 100;
    int Time = 5*100;
    int now = -1;

    public Game(PApplet par, int height, int width){
        /* load music */
        minim = new Minim(par);
        s5 = minim. loadFile ("music/defeat.wav");
        s4 = minim. loadFile ("music/bag.wav");
        s3 = minim. loadFile ("music/store.wav");
        s2 = minim. loadFile ("music/upgrade.wav");
        s1 = minim. loadFile ("music/victory.wav");
        s0 = minim. loadFile ("music/menu.wav");
        s.add(s0);
        s.add(s1);
        s.add(s2);
        s.add(s3);
        s.add(s4);
        s.add(s5);
        this.par    = par;
        this.height = height;
        this.width  = width;
        this.menu   = new Menu(par, this);

        this.loading = new Loading(par);

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

        /* battle timer*/
        Thread timer = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    now --;
                    if(now==0){
                        goToBattle();
                    }
                    try{
                        Thread.sleep(1000);
                    }catch(Exception e){

                    }
                }
            }
        });
        timer.start();
    }

    public void resetTimer(){
        now = Time;
    }

    public void initNewGame(){ // call by menu

        setStatus( GameStatus.LOADING );

        loading.clean();
        Game game = this; // for build settingTab
        Thread load = new Thread(new Runnable() {
            @Override
            public void run() {
                loading.setMessage("loading map....");
                log    = new Log(par);
                map    = new MapDigging(par, 0, 0);
                mapground = new MapGround(par, 0, 0);
                loading.setProgress(20);

                loading.setMessage("loading setting....");
                settingtab = new SettingTab(par, game);
                loading.setProgress(30);

                loading.setMessage("loading player....");
                player = new Player(par, 6,Setting.HeightSpaceNum-1, map, log);
                loading.setProgress(40);

                loading.setMessage("loading store...");
                store  = new Store(par, par.height, width, player.bag);
                loading.setProgress(50);

                loading.setMessage("loading Upgrade...");
                upgrade = new Upgrade(par, par.height, width, player.bag);
                loading.setProgress(60);

                loading.setMessage("loading Battle...");
                battle = new Battle(par, player.bag.getBagMine());
                victory=new  Victory( game,  par,par.width,par.height);
                lose =new Lose( game,  par,par.width,par.height);
                loading.setProgress(100);

                while(loading.isOk()==false){
                    try{
                        Thread.sleep(10);
                    }catch (Exception e){}
                }
                resetTimer();
                goToGround(false);
            }
        });
        load.start();
    }

    public void initOldGame(String pre){ // call by menu

        setStatus(GameStatus.LOADING);

        loading.clean();
        Game game = this;
        Thread load = new Thread(new Runnable() {
            @Override
            public void run() {

                loading.setMessage("loading map....");
                log    = new Log(par);
                map    = new MapDigging(par, 0, 0);
                mapground = new MapGround(par, 0, 0);
                loading.setProgress(20);

                loading.setMessage("loading setting....");
                settingtab = new SettingTab(par, game);
                loading.setProgress(30);

                loading.setMessage("loading player....");
                player = new Player(par, 6,Setting.HeightSpaceNum-1, map, log);
                loading.setProgress(40);

                loading.setMessage("loading store...");
                store  = new Store(par, par.height, width, player.bag);
                loading.setProgress(50);

                loading.setMessage("loading Upgrade...");
                upgrade = new Upgrade(par, par.height, width, player.bag);
                loading.setProgress(60);

                loading.setMessage("loading Battle...");
                battle = new Battle(par, player.bag.getBagMine());
                victory=new  Victory( game,  par,par.width,par.height);
                lose =new Lose( game,  par,par.width,par.height);
                loading.setProgress(100);

                loading.setMessage("reading from recorder...");
                map.read(pre, player);
                player.read(pre);
                loading.setProgress(100);

                while(loading.isOk()==false){
                    try{
                        Thread.sleep(10);
                    }catch (Exception e){}
                }
                resetTimer();
                goToGround(false);
            }
        });
        load.start();
    }

    public void drawTime(){
        par.textAlign(PConstants.LEFT);
        par.textSize(25);
        par.noStroke();
        par.fill(155,134);
        par.rect(50, 30, 250, 50, 30, 30,30,30);
        par.fill(255,0,0);
        par.text("Hp:"+hp+" Time:"+now, 60, 60);
    }

    public void draw(){
        switch (gameStatus){
            case MENU:
                for(int i=0;i< snum;i++)
                {
                    if(i==0)
                    {
                        if (! s.get(i).isPlaying()){
                            s.get(i).pause();
                            s.get(i).rewind();
                            s.get(i).play();
                        }
                        else
                            s.get(i).play();
                    }

                    else
                        s.get(i).pause();
                }
                menu.display();
                break;

            case SETTING:
                for(int i=0;i< snum;i++)
                {
                    s.get(i).pause();
                }
                if(preStatus==GameStatus.DIGGING)
                    map.displayQ(player.pos);
                else mapground.display(player.pos);
                player.display(map.camera);
                settingtab.display();
                break;

            case LOADING:
                for(int i=0;i< snum;i++)
                {
                    s.get(i).pause();
                }
                loading.display();
                break;

            case GROUND:
                for(int i=0;i< snum;i++)
                {
                    s.get(i).pause();
                }
                mapground.display(player.pos);
                player.display(mapground.camera);
                drawTime();
                break;

            case DIGGING:
                for(int i=0;i< snum;i++)
                {
                    s.get(i).pause();
                }
                map.displayQ(player.pos);
                player.display(map.camera);
                drawTime();
                break;

            case BAGMINE:
                for(int i=0;i< snum;i++)
                {
                    if(i==4) {
                        if (open == true) {
                            if (! s.get(i).isPlaying()){
                                s.get(i).pause();
                                s.get(i).rewind();
                            }
                            s.get(i).play();
                            open=false;
                        }
                    }
                    else
                        s.get(i).pause();
                }
                if(preStatus==GameStatus.DIGGING)
                    map.displayQ(player.pos);
                else mapground.display(player.pos);
                player.display(map.camera);
                player.displayMineBag();
                drawTime();
                break;

            case BAGTOOL:
                for(int i=0;i< snum;i++)
                {
                    if(i==4) {
                        if (open == true) {
                            if (! s.get(i).isPlaying()){
                                s.get(i).pause();
                                s.get(i).rewind();
                            }
                            s.get(i).play();
                            open=false;
                        }
                    }
                    else
                        s.get(i).pause();
                }
                if(preStatus==GameStatus.DIGGING)
                    map.displayQ(player.pos);
                else mapground.display(player.pos);
                player.display(map.camera);
                player.displayToolBag();
                drawTime();
                break;

            case SHOPPING:
                for(int i=0;i< snum;i++)
                {
                    if(i==3) {
                        if (!s.get(i).isPlaying()) {
                            s.get(i).pause();
                            s.get(i).rewind();
                            s.get(i).play();
                        } else
                            s.get(i).play();
                    }
                    else
                        s.get(i).pause();
                }
                store.draw();
                break;

            case UPGRADE:
                for(int i=0;i< snum;i++)
                {
                    if(i==2){
                        if (!s.get(i).isPlaying()) {
                            s.get(i).pause();
                            s.get(i).rewind();
                            s.get(i).play();
                        } else
                            s.get(i).play();
                    }
                    else
                        s.get(i).pause();
                }
                upgrade.draw();
                break;

            case BATTLE:
                for(int i=0;i< snum;i++)
                {
                    s.get(i).pause();
                }
                int result = battle.display();
                if(result==1){
                    if(battle.battleResult()<0){
                        hp += battle.battleResult();
                    }
                    if(hp<=0) setStatus(GameStatus.LOSE);
                    else{
                        resetTimer();
                        goToGround(true);
                    }
                }
                break;

            case VICTORY:
                for(int i=0;i< snum;i++)
                {
                    if(i==1){
                        s.get(i).play();
                    }
                    else
                        s.get(i).pause();
                }
                map.display((int)player.pos.x, (int)player.pos.y, player.getLight(), player.pos);
                victory.draw((int)player.pos.x, (int)player.pos.y);
                break;

            case LOSE:
                for(int i=0;i< snum;i++)
                {
                    if(i==5){
                        s.get(i).play();
                    }
                    else
                        s.get(i).pause();
                }
                lose.draw();
        }

        if(isReminder){
            par.translate(-map.camera.x,-map.camera.y);
            nowReminder.display();
            par.translate(map.camera.x,map.camera.y);
        }

    }

    public void keyPressed(){
        if(par.keyCode==par.SHIFT) return;
        try{
            switch (gameStatus){
                case BATTLE:
                    battle.checkKeyPressed();
                    break;

                case SETTING:
                    int key = par.keyCode;
                    par.keyCode = 0;
                    settingtab.keyPressed(key);
                    break;

                case LOADING:
                    break;

                case UPGRADE:
                    if(par.key == 'o') goToGround(false);
                    break;

                case SHOPPING:
                    if(par.key == 'o') goToGround(false);
                    break;

                case BAGMINE:
                    if(par.key == 'b') setStatus( preStatus );
                    else player.bag.keyPressed(par.keyCode, 0);
                    break;

                case BAGTOOL:
                    if(par.key == 't') setStatus( preStatus );
                    else player.bag.keyPressed(par.keyCode, 1);
                    break;

                case GROUND:
                    if(par.key == 'b')       {setStatus( GameStatus.BAGMINE ); open  =true;}
                    else if(par.key == 't' ) {setStatus( GameStatus.BAGTOOL ); open = true;}
                    else if(par.key == 'l') now = 5;
                    else if(par.keyCode == 27) {
                        par.keyCode = 0;
                        settingtab.open();
                        setStatus( GameStatus.SETTING );
                    }
                    else{
                        int dir = 0;
                        if(par.keyCode==par.UP         || par.key=='w' || par.key=='W') dir = 1;
                        else if(par.keyCode==par.RIGHT || par.key=='d' || par.key=='D') dir = 2;
                        else if(par.keyCode==par.DOWN  || par.key=='s' || par.key=='S') dir = 3;
                        else if(par.keyCode==par.LEFT  || par.key=='a' || par.key=='A') dir = 4;

                        if(player.isIdle()==false) return; // so busy

                        else if(par.key == par.CODED){

                            if(dir==1 && mapground.atShop(player.pos))         setStatus( GameStatus.SHOPPING );
                            else if(dir==1 && mapground.atUpgrade(player.pos)) setStatus( GameStatus.UPGRADE );
                            else if(dir==1 && mapground.atGate(player.pos)) goToDigging();
                            else{
                                player.dir = dir;
                                boolean win = player.tryMove(dir);
                                if(win){
                                    gameStatus = GameStatus.VICTORY;
                                    saveGame(1);
                                }
                            }
                        }
                    }
                    break;

                case DIGGING:
                    if(par.key == 'b')       {setStatus(GameStatus.BAGMINE); open = true;}
                    else if(par.key == 't' ) {setStatus( GameStatus.BAGTOOL ); open = true;}
                    else{
                        int dir = 0;
                        if(par.keyCode==par.UP         || par.key=='w' || par.key=='W') dir = 1;
                        else if(par.keyCode==par.RIGHT || par.key=='d' || par.key=='D') dir = 2;
                        else if(par.keyCode==par.DOWN  || par.key=='s' || par.key=='S') dir = 3;
                        else if(par.keyCode==par.LEFT  || par.key=='a' || par.key=='A') dir = 4;

                        if(player.isIdle()==false) return; // so busy

                        if(par.key=='q') player.putItem();
                        else if(par.key == par.CODED){
                            if(dir==1 && player.pos.x==BlockSize && player.pos.y==BlockSize) goToGround(true);
                            else{
                                player.dir = dir;
                                boolean win = player.tryMove(dir);
                                if(win){
                                    setStatus(GameStatus.VICTORY);
                                    saveGame(1);
                                }
                            }
                        }
                        else if(dir!=0){
                            //if(par.key>='A' && par.key<='Z') System.out.println("put " + dir);
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

                case BATTLE:
                    battle.checkMouseClicked();
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

    public void goToGround(boolean fromMap){
        player.setMap( mapground );
        player.dir = 3;
        if(fromMap) player.setPos(BlockSize*11, BlockSize*3);
        player.setPos(BlockSize*6, BlockSize*3);
        setStatus(GameStatus.GROUND);
    }

    public void goToDigging(){
        player.setMap( map );
        player.setPos(BlockSize, BlockSize);
        map.camera.x = 0;
        map.camera.y = 0;
        map.cameraf.x = 0;
        map.cameraf.y = 0;
        player.extend();
        setStatus(GameStatus.DIGGING);
    }

    public void goToMenu(){
        menu.toNormal();
        setStatus( GameStatus.MENU);
    }

    public void setStatus(GameStatus status){
        preStatus = gameStatus;
        gameStatus = status;
    }

    public void goToBattle(){

        battle.reset();
        setStatus(GameStatus.BATTLE );
    }
}

enum GameStatus {
    MENU,
    SETTING,

    LOADING,
    GROUND,
    DIGGING,

    SHOPPING,
    UPGRADE,

    BAGMINE,
    BAGTOOL,

    VICTORY,
    LOSE,
    BATTLE;
}

