/**
 * Created by Rober on 2017/5/18.
 */

import Setting.*;
import processing.core.PApplet;

public class Bag {
    PApplet par;
    int activeItem = 1;
    int activeMine = 1;
    int[] mine;
    int[] item;

    public Bag(PApplet par){
        mine = new int[Setting.MineNum + 10];
        item = new int[Setting.ItemNum + 10];
        this.par = par;
    }


    public int getMineNum(int id){
        return mine[id];
    }

    public void addMine(int id){
        mine[id] += 1;
    }

    public void delMine(int id){
        mine[id] -= 1;
    }

    public int getItemNum(int id){
        return item[id];

    }

    public void addItem(int id){
        item[id]++;

    }

    public void delItem(int id){
        item[id]++;

    }

    public void display(){
//        for(int i=0; i<item.length; i++){
//            if(item[i]!=0)
//                System.out.println(Setting.ItemName[i]+ " = " + item[i]);
//        }

//        par.translate(par.mouseX, par.mouseY);
        par.translate(256,141);
//        System.out.println( par.mouseX + " " + par.mouseY);
//        par.background(250,0,0);
        par.fill(255,0,0);
        par.rect(0,0, 300,300);
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
                if(activeMine==id){
                    par.stroke(0,0,255);
                }
                else{
                    par.stroke(0);
                }
                par.fill(0);
                par.rect(x+5, y+5, blocklen, blocklen, 7,7,0,0);
//                if(i==0) par.image(par.img, x+10, y+10, blocklen-10, blocklen-10);
//                else par.image(imgrock, x+10, y+10, blocklen-10, blocklen-10);
                par.rect(x+5, y+blocklen+5, blocklen, 30,0,0,7,7);

                par.fill(255);
                par.textAlign(par.CENTER);
                par.textSize(16);

                par.text(Setting.MineName[id]+"  " +mine[id], x+5, y+blocklen+10, blocklen, 30);
            }
        }
    }

    public void keyPressed(int key){
        if(key==par.UP && activeMine>=3)   activeMine -= 3;
        if(key==par.DOWN && activeMine<=3) activeMine += 3;
        if(key==par.RIGHT && activeMine<=5)activeMine += 1;
        if(key==par.LEFT && activeMine>=2) activeMine -= 1;
        System.out.println(activeMine);
    }

}
