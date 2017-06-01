package Map;

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

    static PImage imgLadder;

    public Block(){

    }

    public Block(PApplet par, int mineid){

    }

    public void display(int x,int y,int w,int h){

    }

    public int dig(){ return 0;}

    public boolean canDig(int tool){
        // TODO: compare with tools
        System.out.println(tool + " " + level);
        if(tool<level) return false;
        if(status==BlockStatus.EMPTY) return false;
        if(level==100) return false;
        else return true;
    }

    public boolean isEmpty(){
        return status==BlockStatus.EMPTY;
    }
}

enum BlockStatus{
    NORMAL,
    LADDER,
    EMPTY
}
