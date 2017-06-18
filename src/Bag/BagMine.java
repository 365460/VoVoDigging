package Bag;

import Map.BlockFactory;
import Setting.*;
import processing.core.PApplet;
import processing.core.PImage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Rober on 2017/5/24.
 */
public class BagMine {

    PApplet par;
    PImage bg;
    PImage[] mineIcon;

    int activeId = 1;
    int[] mine;
    int limitWeight = 600;
    int currentWeight = 0;

    public BagMine(PApplet par){
        this.par = par;

        mine = new int[Setting.MineNum + 10];
        bg = par.loadImage("image/bagBg.png");

        mineIcon = new PImage[ Setting.MineNum+10 ];
        for(int i=1; i<Setting.MineNum; i++){
            mineIcon[i] = par.loadImage(Setting.MIneImage[i]);
        }



//        for(int i=1; i<Setting.MineNum; i++){
//            for(int j=0; j<40; j++)
//                addMine(i);
//        }
    }

    public int getLimitWeight(){
        return limitWeight;
    }

    public void setLimitWeight(int v){
        limitWeight = v;
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

    public void takeMine(int []require){
        for(int i = 0; i < 6; i++)
            mine[i+1] -= require[i];
    }

    public int[] getMine(){
        return mine;
    }

    public int getActiveId(){
        return activeId;
    }

    public void display(){
        par.translate(256,141);
        int totalW = 300+20*2, totalH = 300+20*2;
        par.image(bg, 0, 0, totalW, totalH);
        par.fill(255, 0, 0);


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
        par.translate(-20,-20);

        par.translate(-256, -141);
    }

    public void keyPressed(int key){
        if(key==par.UP   && activeId>3)  activeId -= 3;
        if(key==par.DOWN && activeId<=3) activeId += 3;
        if(key==par.RIGHT&& activeId<=5) activeId += 1;
        if(key==par.LEFT && activeId>=2) activeId -= 1;
        System.out.println(activeId);
    }

    public void save(String pre){
        try{
            FileWriter fw = new FileWriter(pre+"/mine.bag");
            String tmp = "";
            for(int i=1; i<=Setting.MineNum; i++){
                tmp += mine[ i ] + " ";
            }
            fw.write(tmp);
            fw.close();
        }
        catch(IOException e){
        }
    }

    public void read(String pre){
        try{
            FileReader f = new FileReader(pre+"mine.bag");

            BufferedReader brMap = new BufferedReader(f);

            String tmpM       = brMap.readLine();
            String tmpArray[] = tmpM.split("\\s");


            currentWeight = 0;
            for(int j=1; j<=Setting.MineNum; j++) {
                mine[j] = Integer.parseInt(tmpArray[j-1]);
                currentWeight +=  mine[j]*Setting.MineWeight[j];
            }
            f.close();
        }catch(IOException e){}
    }
}
