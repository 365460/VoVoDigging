package Bag;


import processing.core.PApplet;
import processing.core.PImage;
import Setting.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

/**
 * Created by Rober on 2017/5/31.
 */
public class BagTool {

    PApplet par;
    PImage bg;
    PImage[] itemIcon;

    int activeId = 1;
    int[] usageCount;

    public BagTool(PApplet par){
        this.par = par;

        usageCount = new int[Setting.ItemNum + 10];
        itemIcon = new PImage[ Setting.ItemNum+10 ];
        bg = par.loadImage("image/bagBg.png");

        for(int i=1; i<=Setting.ItemNum; i++){
            itemIcon[i] = par.loadImage(Setting.ToolImage[i]);
        }
    }

    public int getUsage(int id){
        return usageCount[id];
    }

    public void addToolUsage(int id,int v){
        usageCount[id] += v;
    }

    public void delToolUsage(int id){
        if(id!=1) // hand
            usageCount[id] -= 1;

        if(usageCount[id]==0)
            activeId = 1;
    }

    public void addItem(int id){
        usageCount[id] += Setting.ItemUsageCount[id];
    }

    public int getActiveId(){
        return activeId;
    }

    void displayItem(int j,int i,int id){

        int blockHeight = 140;
        int blockWidth = 100;
        int blocklen = 90;
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
        par.image(itemIcon[id], x+10, y+10, blocklen-10, blocklen-10);
        par.rect(x+5, y+blocklen+5, blocklen, 30,0,0,7,7);

        par.fill(255);
        par.textAlign(par.CENTER);
        par.textSize(16);

        String count = "oo";

        if(id!=1) count = "" +usageCount[id];
        par.text(count, x+5, y+blocklen+10, blocklen, 30);

    }

    public void display(){
        par.translate(256,141);
        int totalW = 300+20*2, totalH = 300+20*2;
        par.image(bg, 0, 0, totalW, totalH);
        par.fill(255, 0, 0);

        par.translate(20, 20);
        int w = 2;
        int h = 2;
        for (int i=0; i<h; i++) {
            for (int j=0; j<w; j++) {
                int id = i*2 + j + 1;
                displayItem(j, i, id);
            }
        }
        displayItem(2,0, Setting.ToLadderId);

        par.translate(-20,-20);

        par.translate(-256, -141);
    }

    public void keyPressed(int key){
        if(key==par.UP   && activeId>=3)  activeId -= 2;
        if(key==par.DOWN && activeId<=2) activeId += 2;
        if(key==par.RIGHT&& activeId<=3) activeId += 1;
        if(key==par.LEFT && activeId>=2) activeId -= 1;

        System.out.println("tool id = " + activeId);
    }

    public void save(String pre){
        try{
            FileWriter fw = new FileWriter(pre+"/tool.bag");
            String tmp = "";
            for(int i=1; i<=Setting.ItemNum; i++){
                tmp += usageCount[ i ] + " ";
            }
            fw.write(tmp);
            fw.close();
        }
        catch(IOException e){
        }
    }

    public void read(String pre){
        try{
            FileReader f = new FileReader(pre+"tool.bag");

            BufferedReader brMap = new BufferedReader(f);

            String tmpM       = brMap.readLine();
            String tmpArray[] = tmpM.split("\\s");

            for(int j=1; j<=Setting.ItemNum; j++) {
                usageCount[j] = Integer.parseInt(tmpArray[j-1]);
            }
            f.close();
        }catch(IOException e){}
    }
}
