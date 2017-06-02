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

    int vx = 4;
    int vy = 4;
    boolean ismoving;
    int dir;

    public Player(PApplet par, int x, int y, Map map){
        this.par = par;
        pos = new PVector( x*Setting.BlockSize, y*Setting.BlockSize);
        this.map = map;
        img = par.loadImage("image/p3.png");

        bag = new Bag(this.par);
        bag.addItem(Setting.ToLadderId);
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
        int gx = (int)pos.x/Setting.BlockSize + 1, gy = (int) pos.y/Setting.BlockSize + 1; // screen
        int mx = gx + map.stx , my = gy+map.sty ; // map

        int nx = (int)pos.x, ny = (int)pos.y;
        if(dir==1){ // up
            if(gy==Setting.HeightSpaceNum && map.sty>0) map.sty -= 1;
            else if(pos.y-1>0) ny -= Setting.BlockSize;
        }
        else if(dir==2){ // right
            if(gx==Setting.ScreenWidthNum-3 && map.stx+1+Setting.ScreenWidthNum<=Setting.BlockNumWidth) map.stx += 1;
            else if(gx+1<Setting.ScreenWidthNum) nx += Setting.BlockSize;
        }
        else if(dir==3){ // down
            if(gy==Setting.ScreenHeightNum-3 && map.sty+1+Setting.ScreenHeightNum<=Setting.BlockNumHeight) map.sty += 1;
            else if(gy+1<=Setting.ScreenHeightNum) ny += Setting.BlockSize;
        }
        else if(dir==4){ // left
            if(gx==2 && map.stx>0) map.stx -= 1;
            else if(gx>=1) nx -= Setting.BlockSize;
        }
        gx = nx/Setting.BlockSize + 1; gy = ny/Setting.BlockSize + 1; // screen
        mx = gx +map.stx; my = gy + map.sty;

        map.extend(mx, my, bag.getLight());
        pos.x = nx;
        pos.y = ny;
        return true;

    }

    public boolean tryMove(int dir){
        int gx = (int)pos.x/Setting.BlockSize + 1 , gy = (int)pos.y/Setting.BlockSize + 1; // screen
        int mx = gx + map.stx, my = gy+map.sty; // map
        if(dir==1) my -= 1;
        else if(dir==2) mx += 1;
        else if(dir==3) my += 1;
        else if(dir==4) mx -= 1;
        boolean result = map.canMove(mx, my);

        if(result) move(dir);

        return result;
    }

    void digBlock(int dir) throws Reminder{
        int gx = (int)pos.x/Setting.BlockSize + 1 , gy = (int)pos.y/Setting.BlockSize + 1; // screen
        int mx = gx + map.stx, my = gy+map.sty; // map
        if(dir==1) my -= 1;
        else if(dir==2) mx += 1;
        else if(dir==3) my += 1;
        else if(dir==4) mx -= 1;

        int toolid = bag.getToolActiveId();
        if(bag.getToolUsage(toolid) <=0 ) toolid = 1;

        int result = map.Dig(mx, my, Setting.ItemLevel[toolid]);
        if(result == 0){
            throw new Reminder(par, pos, "You need update your tools");
        }
        else if(bag.canAddMine(result)){

            bag.delToolUsage( toolid );
            if(result!=0 && dir==3) { // falling
                while ( map.map[my-Setting.HeightSpaceNum][mx].isEmpty()){
                    move(3);
                    gx = (int)pos.x/Setting.BlockSize + 1;
                    gy = (int)pos.y/Setting.BlockSize + 1; // screen
                    mx = gx + map.stx;
                    my = gy + map.sty; // map
                }
                pos.y -= Setting.BlockSize;
            }
            if(result==10){
                bag.addToolUsage(Setting.ToLadderId, 1);
                throw new Reminder(par, pos, "You got a Ladder");
            }
            else{
                if(par.random(0, 1)>=0.5) return;
                bag.addMine( result );
                throw new Reminder(par, pos, "You got a " + Setting.MineName[ result ]);
            }

        }
        else{
            map.putMine(mx, my, result);
            throw new Reminder(par, pos, "Bag is full!");
        }
    }

    void putMine(int dir){
        int gx = (int)pos.x/Setting.BlockSize + 1 , gy = (int)pos.y/Setting.BlockSize + 1; // screen
        int mx = gx + map.stx, my = gy+map.sty; // map
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
        int mx = gx + map.stx, my = gy+map.sty; // map

        if(bag.getToolUsage(Setting.ToLadderId) > 0){
            if( map.putItem(mx, my, 0) )
                bag.delToolUsage(Setting.ToLadderId);
        }
        else{
            throw new Reminder(par, pos, "no ladder Q_Q");
        }
    }

}

