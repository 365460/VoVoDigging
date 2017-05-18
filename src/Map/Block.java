package Map;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by Rober on 2017/5/5.
 */
public class Block {
    int level;
    BlockStatus status = BlockStatus.NORMAL;
    PApplet par;

    static PImage imgRock;
    static PImage imgSoil;
    static PImage imgWall;
    static PImage imgEmpty;
    static PImage imgGold;

    static PImage imgLadder;

    public void display(int x,int y,int w,int h){
    }

    public int dig(){ return 0;}




    public boolean CanDig(){
        // TODO: compare with tools
        if(status==BlockStatus.EMPTY) return false;

        if(level==100) return false;
        else return true;
    }
}

enum BlockStatus{
    NORMAL,
    LADDER,
    EMPTY
}
