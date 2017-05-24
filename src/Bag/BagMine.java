package Bag;

import Setting.*;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by Rober on 2017/5/24.
 */
public class BagMine {

    PApplet par;
    PImage bg;
    PImage[] mineIcon;

    int activeId = 1;
    int[] mine;
    int limitWeight = 60;
    int currentWeight = 0;

    public BagMine(PApplet par){
        this.par = par;

        mine = new int[Setting.MineNum + 10];

        mineIcon = new PImage[ Setting.MineNum+10 ];
        for(int i=1; i<Setting.MineNum; i++){
            mineIcon[i] = par.loadImage(Setting.MIneImage[i]);
        }
    }

    public int getLimitWeight(){
        return limitWeight;
    }

    public int getNum(int id){
        return mine[id];
    }

    public void addMine(int id){
        currentWeight += Setting.MineWeight[id];
        mine[id]++;
    }

    public void delMine(int id){
        currentWeight -= Setting.MineWeight[id];
        mine[id]--;
    }

    public int getActiveId(){
        return activeId;
    }

    public void display(){
        par.translate(20, 20);
        int blockHeight = 140;
        int blockWidth = 100;
        int blocklen = 90;
        int w = 3;
        int h = 2;
        for (int i=0; i<h; i++) {
            for (int j=0; j<w; j++) {
                int id = i*3 + j + 1;
                int x = j*blockWidth;
                int y = i*blockHeight;
                if(activeId==id){
                    par.strokeWeight(3);
                    par.stroke(255,0,0);
                }
                else{
                    par.strokeWeight(1);
                    par.stroke(0);
                }

                par.fill(51,0,0);
                par.rect(x+5, y+5, blocklen, blocklen, 7,7,0,0);
                par.image(mineIcon[id], x+10, y+10, blocklen-10, blocklen-10);
                par.rect(x+5, y+blocklen+5, blocklen, 30,0,0,7,7);

                par.fill(255);
                par.textAlign(par.CENTER);
                par.textSize(16);

                par.text(Setting.MineName[id]+"  " +mine[id], x+5, y+blocklen+10, blocklen, 30);
            }
        }
        par.text(currentWeight+"/"+limitWeight, 0, 280, 300, 30);
    }

    public void keyPressed(int key){
        if(key==par.UP   && activeId>3)  activeId -= 3;
        if(key==par.DOWN && activeId<=3) activeId += 3;
        if(key==par.RIGHT&& activeId<=5) activeId += 1;
        if(key==par.LEFT && activeId>=2) activeId -= 1;
        System.out.println(activeId);
    }
}
