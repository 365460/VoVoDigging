package Map;

import Setting.Setting;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by Rober on 2017/5/5.
 */
public class Block {
    int level;
    public BlockStatus status = BlockStatus.NORMAL;
    PApplet par;

    static PImage imgSoil;
    static PImage imgRock;
    static PImage imgCoal;
    static PImage imgWood;
    static PImage imgGold;
    static PImage imgDiamond;
    static PImage imgWall;
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

        switch (status){
            case EMPTY:
                par.image(imgEmpty, x, y, w, h);
                break;

            case NORMAL:
                showNormal(x, y, w, h);
                break;

            case LADDER:
                par.image(imgEmpty, x, y, w, h);
                par.image(imgLadder, x, y, w, h);
                break;

            case FIN:
                par.image(imgEmpty, x, y, w, h);
                if(par.frameCount%12<=3) par.image(imgflag[0], x, y, w, h);
                else if(par.frameCount%12<=7) par.image(imgflag[1], x, y, w, h);
                else par.image(imgflag[2], x, y, w, h);
                break;
        }
        if(isDigging)
            par.image(imgdig[digid], x, y, w, h);
    }

    public int dig(){ return 0;}

    public boolean canDig(int tool){
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

        isDigging = true;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 10;
                for(int i=0; i<count; i++){
                    digid = i%5;
                    try{
                        Thread.sleep(Setting.DiggingTime/count);
                    }
                    catch(Exception e){

                    }
                }
                isDigging = false;
                status = BlockStatus.EMPTY;
            }
        });
        thread.start();

    }
}

enum BlockStatus{
    NORMAL,
    LADDER,
    DIGGING,
    FIN,
    EMPTY
}
