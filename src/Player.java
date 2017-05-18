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

    Map map;
    PVector pos;

    int vx = 4;
    int vy = 4;
    boolean ismoving;
    int dir;

    public Player(PApplet par, int x, int y, Map map){
        this.par = par;
        pos = new PVector( x*Setting.BlockSize, y*Setting.BlockSize);
        this.map = map;
        img = par.loadImage("image/p2.png");
    }

    public void display(){
        int blocksize = Setting.BlockSize;

        par.image(img, pos.x, pos.y, blocksize, blocksize );
    }

    public void move(int dir){
        int gx = (int)pos.x/Setting.BlockSize + 1, gy = (int) pos.y/Setting.BlockSize + 1; // screen
        int mx = gx + map.stx , my = gy+map.sty ; // map

        int nx = (int)pos.x, ny = (int)pos.y;
        if(canMove(dir)==false) return;
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
        System.out.println("gx = " + gx + ",, gy = " + gy);
        System.out.println("mx = " + mx + ",, my = " + my);
        System.out.println("stx = " +map.stx + ", sty = " + map.sty + "\n");

        pos.x = nx;
        pos.y = ny;

    }

    boolean canMove(int dir){
        int gx = (int)pos.x/Setting.BlockSize + 1 , gy = (int)pos.y/Setting.BlockSize + 1; // screen
        int mx = gx + map.stx, my = gy+map.sty; // map
        if(dir==1) my -= 1;
        else if(dir==2) mx += 1;
        else if(dir==3) my += 1;
        else if(dir==4) mx -= 1;

        return map.canMove(mx, my);
    }

    void digBlock(int dir) {
        int gx = (int)pos.x/Setting.BlockSize + 1 , gy = (int)pos.y/Setting.BlockSize + 1; // screen
        int mx = gx + map.stx, my = gy+map.sty; // map
        if(dir==1) my -= 1;
        else if(dir==2) mx += 1;
        else if(dir==3) my += 1;
        else if(dir==4) mx -= 1;

        int result = map.Dig(mx, my);
        if(result!=0 && dir==3) move(3);
    }

    void putItem(){
        int gx = (int)pos.x/Setting.BlockSize + 1 , gy = (int)pos.y/Setting.BlockSize + 1; // screen
        int mx = gx + map.stx, my = gy+map.sty; // map
        map.putItem(mx, my, 0);
    }

}

