
package Game;
import Bag.Bag;
import Setting.Setting;
import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.core.PImage;
import Map.*;
import processing.core.PVector;

import static Setting.Setting.BlockNumHeight;
import static Setting.Setting.BlockSize;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

import Reminder.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

/**
 * Created by Rober on 2017/5/5.
 */
public class Player {
    PApplet par;
    PImage img;
    PImage[][] imgWalkd;

    PVector pos, posf, moment;

    Map map;
    Bag bag;
    Log log;

    int vx = 4;
    int vy = 4;
    boolean isMoving  = false;
    boolean isDigging = false;
    int imgId = 0;
    int dir = 3;

    int order = -1; // 1->x, 2->y; // for update()

    Ani moveTox, moveToy;

    public Player(PApplet par, int x, int y, Map map, Log log ){
        this.par = par;
        this.map = map;
        this.log = log;

        pos    = new PVector( x* BlockSize, y* BlockSize);
        posf   = new PVector( x*BlockSize, y*BlockSize);
        moment = new PVector(0, 0);
        img = par.loadImage("image/p3.png");
        imgWalkd = new PImage[5][6];
        for(int i=1; i<=4; i++)
            for(int j=0; j<6; j++){
                imgWalkd[i][j] = par.loadImage("Image/"+i+j+".png");
                par.println("Image/"+i+j+".png");
            }

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
//        thread.start();
    }

    public void setMap(Map map){
       this.map = map;
    }

    public void setPos(int x,int y){
        pos.x = x;
        pos.y = y;
        posf.x = x;
        posf.y = y;
    }
    public void displayMineBag(){
        bag.displayMine();
    }

    public void displayToolBag(){
        bag.displayTool();
    }

    public void display(PVector camera){

        update();
        if(isIdle()) falling();

        par.translate(-camera.x, -camera.y);
        if(isMoving){
            par.image(imgWalkd[dir][imgId], pos.x, pos.y, BlockSize, BlockSize);
            imgId = (imgId+1)%imgWalkd.length;
        }
        else par.image(imgWalkd[dir][2], pos.x, pos.y, BlockSize, BlockSize );

        par.translate(camera.x, camera.y);
    }

    public int getLight(){
        return bag.getLight();
    }

    public float getX(){
        return pos.x;
    }

    public float getY(){
        return pos.y;
    }

    public void setX(float v){
        pos.x = v;
    }

    public void setY(float v){
        pos.y = v;
    }

    public boolean move(int dir){
        if(isIdle()==false) return false;

        this.dir = dir;

        float nx = posf.x, ny = posf.y;
        float nsty = map.cameraf.y, nstx = map.cameraf.x;
        if(dir==1){ // up
            if(ny<=nsty+BlockSize*4 && nsty>0) nsty -= BlockSize;
            if(ny>0) ny -= BlockSize;
        }
        else if(dir==2){ // right

            if(nx+2*BlockSize<map.width) nx += BlockSize;

            while(nstx+Setting.GameWidth <= nx+BlockSize){
                nstx += BlockSize;
            }
        }
        else if(dir==3){ // down
            if(ny+2*BlockSize<map.height) ny += BlockSize;

            while(nsty+Setting.GameHeight <= ny+BlockSize){
                nsty += BlockSize;
            }
        }
        else if(dir==4){ // left
            if(nstx>0 && nx<=nstx+BlockSize*2) nstx -= BlockSize;
            if(nx-BlockSize>=0) nx -= BlockSize;
        }

        this.dir = dir;
        posf.x = nx;
        posf.y = ny;

        moment.x = posf.x - pos.x;
        moment.y = posf.y - pos.y;

        extend();
        map.moveCamera(nstx, nsty);
        return true;

    }

    void extend(){
        int gx = (int)posf.x/ BlockSize + 1;
        int gy = (int)posf.y/ BlockSize + 1; // screen
        map.extend(gx, gy, bag.getLight());
    }

    public boolean tryMove(int dir)throws Reminder{
        int gx = (int)posf.x/ BlockSize + 1 , gy = (int)posf.y/ BlockSize + 1; // screen
        int mx = gx, my = gy; // map
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
        int gx = (int)posf.x/ BlockSize + 1, gy = (int)posf.y/ BlockSize + 1;
        int mx = gx, my = gy;

        while(map.shouldFalling(mx, my)){
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
        if(isDigging) return false;

        if(abs(moment.x)>60 || abs(moment.y)>60) return false;
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

    void update(){
//        par.println(moment);
//        par.println(pos);
        if(order == -1){
            if(moment.x !=0 ) order = 1;
            else if(moment.y !=0 ) order = 2;
        }
        if(order==-1){
            isMoving = false;
            return;
        }

        isMoving = true;

        float v = Setting.getMoveV(par);

        if(order==1){
            if(moment.x <0){
                moment.x += v;
                pos.x -= v;
            }
            else{
                moment.x -= v;
                pos.x += v;
            }
            if(moment.x == 0) order = -1;
        }
        else if(order==2){
            if(moment.y <0){
                moment.y += v;
                pos.y -= v;
            }
            else{
                moment.y -= v;
                pos.y += v;
            }
            if(moment.y == 0) order = -1;
        }

    }

    void digBlock(int dir) throws Reminder{
        if(isMoving) return;

        int gx = (int)posf.x/ BlockSize + 1 , gy = (int)posf.y/ BlockSize + 1; // screen
        int mx = gx , my = gy; // map
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
        this.dir = dir;
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
                Reminder re = new Reminder(par, pos, "You got Ladder");
                re.setDelay(1000);
                throw re;
            }
            else{
                if(par.random(0, 1)<0) return;
    /*player get mine*/
                log.AddgetMine( result );

                bag.addMine( result );
                Reminder re = new Reminder(par, pos, "You got " + Setting.MineName[ result ]);
                re.setDelay(1000);
                throw re;
            }
        }
    }

    void putMine(int dir){
        int gx = (int)posf.x/ BlockSize + 1 , gy = (int)posf.y/ BlockSize + 1; // screen
        int mx = gx , my = gy ; // map
        if(dir==1) my -= 1;
        else if(dir==2) mx += 1;
        else if(dir==3) my += 1;
        else if(dir==4) mx -= 1;

        int id = bag.getMineActiveId();
        if(bag.getMineNum(id)>0 && map.putMine(mx, my, id)){
            bag.delMine(id);
            log.AddputMine(id);
        }
    }

    void putItem() throws Reminder{ // just ladder now
        int gx = (int)posf.x/ BlockSize + 1 , gy = (int)posf.y/ BlockSize + 1; // screen
        int mx = gx  , my = gy; // map

        if(bag.getToolUsage(Setting.ToLadderId) > 0){
            if( map.putItem(mx, my, 0) )
                bag.delToolUsage(Setting.ToLadderId);
        }
        else{
            throw new Reminder(par, posf, "no ladder Q_Q");
        }
    }

    void save(String pre){
        bag.save(pre);
        try{
            FileWriter fw = new FileWriter(pre+"/player.player");
            String tmp = "";
            fw.write(tmp);
            fw.close();
        }
        catch(IOException e){
        }
    }

    void read(String pre){
        bag.read(pre);
    }
}

