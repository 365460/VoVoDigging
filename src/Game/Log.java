package Game;
/**
 * Created by Rober on 2017/6/4.
 */
import Setting.*;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import sun.security.krb5.internal.PAData;

public class Log {
    PApplet par;

    PImage[] img;
    int[] getMine, getItem;
    int[] putMine, putItem;

    public Log(PApplet par){

        this.par = par;

        getMine = new int[Setting.MineNum+10];
        getItem = new int[Setting.ItemNum+10];

        putMine = new int[Setting.MineNum+10];
        putItem = new int[Setting.ItemNum+10];

        img     = new PImage[Setting.MineNum+10];
        for(int i=1; i<Setting.MineNum; i++){
            img[i] = par.loadImage(Setting.MIneImage[i]);
        }
    }


    public void AddgetMine(int id){
        getMine[id] += 1;
    }

    public void AddputMine(int id){
        putMine[id] += 1;
    }

    public void AddgetItem(int id){
        getItem[id] += 1;
    }

    public void AddputItem(int id){
        putItem[id] += 1;
    }

    public void display(){

        int stx = 100, sty = 100;
        par.translate(stx, sty);
        par.textAlign(PConstants.CENTER);

        int imglen = 50;

        for(int i=1; i<Setting.MineNum; i++){
            par.image(img[i], 0, (i-1)*imglen, imglen, imglen);
        }

        par.translate(-stx, -sty);
    }
}
