package Bag; /**
 * Created by Rober on 2017/5/18.
 */

import Setting.*;
import javafx.scene.control.Tab;
import processing.core.PApplet;
import processing.core.PImage;

public class Bag {
    PApplet par;
    BagMine minebag;
    BagTool toolbag;
    int light;

    public Bag(PApplet par){
        this.par = par;
        light = 3;

        minebag = new BagMine(par);
        toolbag = new BagTool(par);

    }

    public BagMine getBagMine(){
        return minebag;
    }

    public void setLight(int v){
        light = v;
    }

    public int getLight(){
        return light;
    }

    public void setMineLimit(int v){
        minebag.setLimitWeight(v);
    }

    public int getMineLimit(){
        return minebag.getLimitWeight();
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
        if(id==10) return true;
        return minebag.currentWeight + Setting.MineWeight[id] <= minebag.limitWeight;
    }

    public int getToolActiveId(){
        return toolbag.getActiveId();
    }

    public int getToolUsage(int id){
        return toolbag.getUsage(id);
    }

    public void addToolUsage(int id,int v){
        toolbag.addToolUsage(id, v);
    }

    public void delToolUsage(int id){
        toolbag.delToolUsage(id);
    }

    public void addItem(int id){
        toolbag.addItem(id);
    }

    /*      display  */
    public void displayTool(){
        toolbag.display();
    }

    public void displayMine(){
        minebag.display();
    }

    public void keyPressed(int key,int which){
        if(which==0) minebag.keyPressed(key);
        else toolbag.keyPressed(key);
    }


    public void save(String pre){
        minebag.save(pre);
        toolbag.save(pre);
    }

    public void read(String pre){
        minebag.read(pre);
        toolbag.read(pre);
    }

}
