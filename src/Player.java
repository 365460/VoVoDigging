import Bag.Bag;
import Setting.Setting;
import processing.core.PApplet;
import processing.core.PImage;
import Map.*;
import processing.core.PVector;

import static java.lang.Math.max;
import static java.lang.Math.min;

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
    int tool = 100;

    public Player(PApplet par, int x, int y, Map map){
        this.par = par;
        pos = new PVector( x*Setting.BlockSize, y*Setting.BlockSize);
        this.map = map;
        img = par.loadImage("image/p2.png");

        bag = new Bag(this.par);
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

    public boolean move(int dir){
        int gx = (int)pos.x/Setting.BlockSize + 1, gy = (int) pos.y/Setting.BlockSize + 1; // screen
        int mx = gx + map.stx , my = gy+map.sty ; // map

        int nx = (int)pos.x, ny = (int)pos.y;
        if(dir==1){ // up
            if(gy==Setting.HeightSpaceNum && map.sty>0) map.sty -= 1;
            else if(pos.y-1>0) ny -= Setting.BlockSize;
        }
        else if(dir==2){ // right
            if(gx==Setting.ScreenWidthNum-1 && map.stx+1+Setting.ScreenWidthNum<=Setting.BlockNumWidth) map.stx += 1;
            else if(gx+1<Setting.ScreenWidthNum) nx += Setting.BlockSize;
        }
        else if(dir==3){ // down
            if(gy==Setting.ScreenHeightNum-1 && map.sty+1+Setting.ScreenHeightNum<=Setting.BlockNumHeight) map.sty += 1;
            else if(gy+1<=Setting.ScreenHeightNum) ny += Setting.BlockSize;
        }
        else if(dir==4){ // left
            if(gx==2 && map.stx>0) map.stx -= 1;
            else if(gx>=1) nx -= Setting.BlockSize;
        }
        gx = nx/Setting.BlockSize + 1; gy = ny/Setting.BlockSize + 1; // screen
        mx = gx +map.stx; my = gy + map.sty;
//        System.out.println("gx = " + gx + ",, gy = " + gy);
//        System.out.println("mx = " + mx + ",, my = " + my);
//        System.out.println("stx = " +map.stx + ", sty = " + map.sty + "\n");

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

    void digBlock(int dir) {
        int gx = (int)pos.x/Setting.BlockSize + 1 , gy = (int)pos.y/Setting.BlockSize + 1; // screen
        int mx = gx + map.stx, my = gy+map.sty; // map
        if(dir==1) my -= 1;
        else if(dir==2) mx += 1;
        else if(dir==3) my += 1;
        else if(dir==4) mx -= 1;

        int toolid = bag.getToolActiveId();
        if(bag.getToolUsage(toolid) <=0 ) toolid = 1;

        int result = map.Dig(mx, my, Setting.ItemLevel[toolid]);
        if(bag.canAddMine(result)){
            bag.delToolUsage( toolid );
            if(result==10){
                bag.addToolUsage(Setting.ToLadderId, 1);
            }
            else{
                bag.addMine( result );
            }
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
        }
        else{
            System.out.println("放不進去喔QQ");
            map.putMine(mx, my, result);
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

    void putItem(){ // just ladder now
        int gx = (int)pos.x/Setting.BlockSize + 1 , gy = (int)pos.y/Setting.BlockSize + 1; // screen
        int mx = gx + map.stx, my = gy+map.sty; // map

        if(bag.getToolUsage(Setting.ToLadderId) > 0){
            if( map.putItem(mx, my, 0) )
                bag.delToolUsage(Setting.ToLadderId);
        }
    }

}

