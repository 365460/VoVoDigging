import Bag.Bag;
import Setting.Setting;
import processing.core.PApplet;
import processing.core.PImage;
import Map.*;
import processing.core.PVector;

import static java.lang.Math.max;
import static java.lang.Math.min;

import Reminder.*;

/**
 * Created by Rober on 2017/5/5.
 */
public class Player {
    PApplet par;
    PImage img;

    PVector pos;

    Map map;
    Bag bag;
    Log log;

    int vx = 4;
    int vy = 4;
    boolean isMoving  = false;
    boolean isDigging = false;
    int dir;

    public Player(PApplet par, int x, int y, Map map, Log log ){
        this.par = par;
        this.map = map;
        this.log = log;

        pos = new PVector( x*Setting.BlockSize, y*Setting.BlockSize);
        img = par.loadImage("image/p3.png");

        bag = new Bag(this.par);
        bag.addItem(Setting.ToLadderId);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(isIdle()) falling();
                    try{
                       Thread.sleep(10);
                    }catch (Exception e){

                    }
                }
            }
        });
        thread.start();
    }

    public void displayMineBag(){
        bag.displayMine();
    }

    public void displayToolBag(){
        bag.displayTool();
    }

    public void display(){
        int blocksize = Setting.BlockSize;
        par.image(img, pos.x, pos.y, blocksize, blocksize );
    }

    public int getLight(){
        return bag.getLight();
    }

    public boolean move(int dir){
        if(isIdle()==false) return false;
        int gx = (int)pos.x/Setting.BlockSize + 1, gy = (int) pos.y/Setting.BlockSize + 1; // screen
        float mx = gx + map.stx , my = gy+map.sty ; // map

        int nx = (int)pos.x, ny = (int)pos.y;
        float nsty = map.sty, nstx = map.stx;
        if(dir==1){ // up
            if(gy==Setting.HeightSpaceNum && map.sty>0) nsty -= 1;
            else if(pos.y-1>0) ny -= Setting.BlockSize;
        }
        else if(dir==2){ // right
            if(gx==Setting.ScreenWidthNum-3 && map.stx+1+Setting.ScreenWidthNum<=Setting.BlockNumWidth) nstx += 1;
            else if(gx+1<Setting.ScreenWidthNum) nx += Setting.BlockSize;
        }
        else if(dir==3){ // down
            if(gy==Setting.ScreenHeightNum-3 && map.sty+1+Setting.ScreenHeightNum<=Setting.BlockNumHeight) nsty += 1;
            else if(gy+1<=Setting.ScreenHeightNum) ny += Setting.BlockSize;
        }
        else if(dir==4){ // left
            if(gx==2 && map.stx>0) nstx -= 1;
            else if(gx>=1) nx -= Setting.BlockSize;
        }

        map.stx = nstx;
        map.sty = nsty;
        pos.x = nx;
        pos.y = ny;
        extend();

//        if(nstx != map.stx || nsty != map.sty)
//            startMoveWin(nstx, nsty);
//        else
//            startMoving(nx, ny);
        return true;

    }

    void extend(){
        int gx = (int)pos.x/Setting.BlockSize + 1;
        int gy = (int)pos.y/Setting.BlockSize + 1; // screen
        int mx = gx +(int)map.stx;
        int my = gy +(int)map.sty;
        map.extend((int)mx, (int)my, bag.getLight());

    }

    public boolean tryMove(int dir)throws Reminder{
        int gx = (int)pos.x/Setting.BlockSize + 1 , gy = (int)pos.y/Setting.BlockSize + 1; // screen
        int mx = gx + (int)map.stx, my = gy+ (int)map.sty; // map
        if(dir==1) my -= 1;
        else if(dir==2) mx += 1;
        else if(dir==3) my += 1;
        else if(dir==4) mx -= 1;
        boolean result = map.canMove(mx, my);

        if(result) move(dir);

        if(map.isVectory(mx, my)) return true;
        return false;
    }

    void falling(){
        int gx = (int)pos.x/Setting.BlockSize + 1, gy = (int)pos.y/Setting.BlockSize + 1;
        int mx = gx + (int)map.stx, my = gy+ (int)map.sty;

        int mgy = my - Setting.HeightSpaceNum;

        while(map.shouldFailing(mx, my)){
            move(3);
            my += 1;
        }
    }

    void delayToMove(int time,int dir){
        if(isIdle()==false) return;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(time);
                    move(dir);
                }catch (Exception e){

                }
            }
        });
        thread.start();
    }

    boolean isIdle(){
        if(isDigging || isMoving) return false;
        return true;
    }

    void startDiggging(){
        if(isIdle()==false) return ;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    isDigging = true;
                    Thread.sleep(Setting.DiggingTime);
                    isDigging = false;
                }catch(Exception e){
                }
            }
        });
        thread.start();
    }

    void startMoveWin(float nx,float ny){
        if(isIdle()==false) return;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    isMoving = true;
                    int f = 10;
                    float dx = (nx-map.stx)/f;
                    float dy = (ny-map.sty)/f;
                    for(int i=0; i<f; i++){
                        map.stx += dx;
                        map.sty += dy;
                        Thread.sleep(Setting.MovingTime/f);
                    }
//                    map.stx = nx;
//                    map.sty = ny;
                    isMoving = false;
                    extend();
                } catch(Exception e){}
            }
        });
        thread.start();
    }

    void startMoving(int nx,int ny){
        if(isIdle()==false) return;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    isMoving = true;
                    int f = 10;
                    float dx = (nx-pos.x)/f;
                    float dy = (ny-pos.y)/f;
                    for(int i=0; i<f-1; i++){
                        pos.x += dx;
                        pos.y += dy;
                        System.out.println(pos.x + ", " + pos.y);
                        Thread.sleep(Setting.MovingTime/f);
                    }
                    pos.x = nx;
                    pos.y = ny;
                    Thread.sleep(Setting.MovingTime/f);
                    isMoving = false;
                    extend();
                } catch(Exception e){}
            }
        });
        thread.start();
    }

    void digBlock(int dir) throws Reminder{
        int gx = (int)pos.x/Setting.BlockSize + 1 , gy = (int)pos.y/Setting.BlockSize + 1; // screen
        int mx = gx + (int)map.stx, my = gy+ (int)map.sty; // map
        if(dir==1) my -= 1;
        else if(dir==2) mx += 1;
        else if(dir==3) my += 1;
        else if(dir==4) mx -= 1;

        int toolId = bag.getToolActiveId();
        if(bag.getToolUsage(toolId) <=0 ) toolId = 1;

        if(map.canDig(mx, my)==false) return;

        if(bag.canAddMine( map.getBlockId(mx, my)) ==false){
            Reminder re = new Reminder(par, pos, "Bag is full!");
            throw re;
        }

        startDiggging();
        int result = map.dig(mx, my, Setting.ItemLevel[toolId]);
        if(result == 0){
            throw new Reminder(par, pos, "You need update your tools");
        }
        else{
            bag.delToolUsage( toolId );
            if(result!=0 && dir==3) { // falling
                if(my==5) delayToMove(Setting.DiggingTime, 3);
            }
            if(result==10){

    /*player get ladder*/
                log.AddgetItem( Setting.ToLadderId );

                bag.addToolUsage(Setting.ToLadderId, 1);
                Reminder re = new Reminder(par, pos, "You got a Ladder");
                re.setDelay(1000);
                throw re;
            }
            else{
                if(par.random(0, 1)<0) return;
    /*player get mine*/
                log.AddgetMine( result );

                bag.addMine( result );
                Reminder re = new Reminder(par, pos, "You got a " + Setting.MineName[ result ]);
                re.setDelay(1000);
                throw re;
            }
        }
    }

    void putMine(int dir){
        int gx = (int)pos.x/Setting.BlockSize + 1 , gy = (int)pos.y/Setting.BlockSize + 1; // screen
        int mx = gx + (int)map.stx, my = gy+ (int)map.sty; // map
        if(dir==1) my -= 1;
        else if(dir==2) mx += 1;
        else if(dir==3) my += 1;
        else if(dir==4) mx -= 1;

        int id = bag.getMineActiveId();
        if(bag.getMineNum(id)>0 && map.putMine(mx, my, id)){
            bag.delMine(id);
        }
    }

    void putItem() throws Reminder{ // just ladder now
        int gx = (int)pos.x/Setting.BlockSize + 1 , gy = (int)pos.y/Setting.BlockSize + 1; // screen
        int mx = gx + (int)map.stx, my = gy+ (int)map.sty; // map

        if(bag.getToolUsage(Setting.ToLadderId) > 0){
            if( map.putItem(mx, my, 0) )
                bag.delToolUsage(Setting.ToLadderId);
        }
        else{
            throw new Reminder(par, pos, "no ladder Q_Q");
        }
    }

}

