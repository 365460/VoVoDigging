package Map;

import Setting.Setting;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by Rober on 2017/5/5.
 */
public class Block {
    int level, id;
    public BlockStatus status = BlockStatus.NORMAL;
    PApplet par;

    static PImage imgSoil;
    static PImage imgRock;
    static PImage imgCoal;
    static PImage imgWood;
    static PImage imgGold;
    static PImage imgDiamond;
    static PImage imgWall;
    static PImage[] imgGate;
    static PImage imgEmpty;

    static PImage[] imgdig;
    static PImage[] imgflag;

    static PImage imgLadder;

    boolean isDigging = false;
    int digid = 0;

    public Block(){

    }

    public Block(PApplet par, int mineid){

    }

    void showNormal(float x,float y,float w,float h){

    }

    public void display(float x,float y,float w,float h){

        par.stroke(255);
        par.strokeWeight(2);
        switch (status){
            case EMPTY:
//                par.fill(150);
//                par.rect(x, y, w, h);
                par.image(imgEmpty, x, y, w, h);
                break;

            case NORMAL:
                showNormal(x, y, w, h);
//                par.rect(x, y, w, h);
                break;

            case LADDER:
                par.image(imgEmpty, x, y, w, h);
//                par.fill(150);
//                par.rect(x, y, w, h);
//
//                par.fill(244, 179, 66);
//                par.rect(x+20,y, 20, 60);
                par.image(imgLadder, x, y, w, h);
                break;

            case FIN:
                par.image(imgEmpty, x, y, w, h);
                if(par.frameCount%12<=3) par.image(imgflag[0], x, y, w, h);
                else if(par.frameCount%12<=7) par.image(imgflag[1], x, y, w, h);
                else par.image(imgflag[2], x, y, w, h);
                break;

            case GATE:
                par.image(imgEmpty, x, y, w, h);
                if(par.frameCount%10<=3)
                    par.image(imgGate[0], x, y, w, h);
                else if(par.frameCount%10<=6)
                    par.image(imgGate[1], x, y, w, h);
                else
                    par.image(imgGate[2], x, y, w, h);
                break;
        }

        if(isDigging) {
            if (par.frameCount % 5 ==0) par.image(imgdig[0], x, y, w, h);
            else if (par.frameCount % 5 ==1) par.image(imgdig[1], x, y, w, h);
            else if (par.frameCount % 5 ==2) par.image(imgdig[2], x, y, w, h);
            else if (par.frameCount % 5 ==3)par.image(imgdig[3], x, y, w, h);
            else par.image(imgdig[4], x, y, w, h);
//            par.image(imgdig[digid], x, y, w, h);
        }
    }

    public int dig(){
        return 0;
    }

    public int getId(){
        if(status==BlockStatus.LADDER) return 0;
        return id;
    }

    public BlockStatus getStatus(){
        return status;
    }

    public boolean canDig(int tool){
//        return true;
        if(status==BlockStatus.LADDER)return true;

        if(tool<level) return false;
        if(level==100) return false;
        else return true;
    }

    public boolean isEmpty(){
        return status==BlockStatus.EMPTY;
    }

    public boolean isDigging(){
        return isDigging;
    }

    void gotoDie(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                isDigging = true;
                try{
                    Thread.sleep(Setting.DiggingTime);
                }
                catch(Exception e){}
                isDigging = false;
                status = BlockStatus.EMPTY;
            }
        });
        thread.start();
    }
}

