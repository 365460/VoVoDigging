package Bag; /**
 * Created by Rober on 2017/5/18.
 */

import Setting.*;
import processing.core.PApplet;
import processing.core.PImage;

public class Bag {
    PApplet par;
    BagMine minebag;
    PImage bg;
    int activeItem = 1;
    int[] item;

    public Bag(PApplet par){
        this.par = par;
        item = new int[Setting.ItemNum + 10];
        minebag = new BagMine(par);

        bg = par.loadImage("image/bagBg.png");
    }


    public int getMineNum(int id){
        return minebag.getNum(id);
    }

    public void addMine(int id){
        minebag.addMine(id);
    }

    public void delMine(int id){
        minebag.delMine(id);
    }

    public int getMineActiveId(){
        return minebag.getActiveId();
    }

    public boolean canAddMine(int id){
        return minebag.currentWeight + Setting.MineWeight[id] <= minebag.limitWeight;
    }

    public int getItemNum(int id){
        return item[id];
    }

    public void addItem(int id){
        item[id]++;
    }

    public void delItem(int id){
        item[id]--;
    }

    public void display(){
        par.translate(256,141);
        par.image(bg, 0,0,300+20*2,300+20*2);
        minebag.display();
    }

    public void keyPressed(int key){
        minebag.keyPressed(key);
    }

}
