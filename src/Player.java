import Setting.Setting;
import processing.core.PApplet;
import processing.core.PImage;
import Map.*;

import java.util.Set;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Created by Rober on 2017/5/5.
 */
public class Player {
    PApplet par;
    PImage img;

    int x, y;
    public Player(PApplet par, int x, int y){
        this.par = par;
        this.x = x;
        this.y = y;
        img = par.loadImage("image/player.png");
    }

    public void display(){
        int blocksize = Setting.BlockSize;
        par.image(img, (x-1)*blocksize, (y-1)*blocksize, blocksize, blocksize);
    }

    public void move(int dir, Map map){

        int w = Setting.ScreenWidthNum;
        int h = Setting.ScreenHeightNum;
        int sh = Setting.HeightSpaceNum;

        if(!tryMove(dir, map)) return;
        System.out.println("h = " + h);
        if(dir==1){ // up
            if(y==sh){
                if(map.sty+1<=sh){
                    map.sty += 1;
                }else y--;
            }
            else if(y-1>=1) y -= 1;
        }
        else if(dir==2){ // right
            if(x==w-1){
                if(map.stx+1+w-1<=map.numW){
                    map.stx+=1;
                }else{
                    x+= 1;
                }
            }
            else if(x+1<=w) x += 1;
        }
        else if(dir==3){ // down
            if(y==h-1){
                int nextY = map.sty-1;
                if(max(-nextY,1) + h-1<=map.numH){
                    map.sty -= 1;
                }else{
                    y += 1;
                }
            }else if(y+1<=h) y += 1;
        }
        else if(dir==4){ // left
            if(x==2){
                if(map.stx>1){
                    map.stx -= 1;
                }else x -= 1;
            }
            else if(x-1>=1) x -= 1;
        }
        System.out.println("x = " + x + " y = " + y);
        System.out.println("stx = " + map.stx + " sty = " + map.sty);

    }

    boolean tryMove(int dir, Map map){
        int xx, yy;

        //TODO:
        if(dir==1) yy = y-1;

        return true;
    }

    void digBlock(int dir, Map map){
        int xx = x, yy = y;

        if(dir==1) yy -= 1;
        else if(dir==2) xx += 1;
        else if(dir==3) yy += 1;
        else if(dir==4) xx -= 1;
        else return;


        xx = map.stx+xx-1;
        yy = yy - map.sty;
        System.out.println("dig xx = " + xx + " yy = " + yy);
        if( xx>=1 && yy>=1 && map.map[yy][xx].CanDig()){
            map.map[yy][xx].dig();
        }
    }

}
