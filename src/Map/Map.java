package Map;

import Setting.Setting;
import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;
import java.util.Set;

import static java.lang.Math.max;

/**
 * Created by Rober on 2017/5/5.
 */
public class Map {
    PApplet par;
    PImage imgbackground;

    public int stx, sty;
    public int numW = Setting.BlockNumWidth;
    public int numH = Setting.HeightMapNum;
    int blockSize = Setting.BlockSize;

    public Block[][] map;
    public Map(PApplet par, int stx, int sty){
        this.par = par;
        this.stx = stx;
        this.sty = sty;

        map = new Block[numH+10][numW+10];

        System.out.println("numW = " + numW + ", numH = " + numH);
        System.out.println("width = " + par.width + ", height = " + par.height);
        System.out.println("stx = " + stx + ", sty = " + sty);

        // init Block image
        Block.imgSoil = par.loadImage("image/soil.jpg");
        Block.imgWall = par.loadImage("image/wall.jpg");
        Block.imgRock = par.loadImage("image/rock.jpg");
        Block.imgEmpty = par.loadImage("image/empty.jpg");
        Block.imgLadder = par.loadImage("image/ladder.png");

        imgbackground = par.loadImage("image/background.jpg");


        for(int i=1; i<=numH-1; i++){
            for(int j=2; j<=numW-1; j++){
                int id = (int)par.random(0, 5);
                if(id>=2) map[i][j] = new BlockSoil(this.par);
                else map[i][j] = new BlockRock(this.par);
            }
        }

        for(int i=1; i<=numH; i++) map[i][1] = new BlockWall(par);
        for(int i=1; i<=numW; i++) map[numH][i] = new BlockWall(par);
        for(int i=1; i<=numH; i++) map[i][numW] = new BlockWall(par);
    }

    public void display(){
        par.background(0);
        // ground
        if(sty<=Setting.HeightSpaceNum){
            par.image(imgbackground, 0, 0, Setting.GameWidth, Setting.HeightSpaceNum*Setting.BlockSize );
        }
        for(int i=1; i<=Setting.ScreenHeightNum; i++){
            for(int j=1; j<=Setting.ScreenWidthNum; j++){

                if(sty+i>Setting.HeightSpaceNum){ // bottom
                    int y =  sty+i-Setting.HeightSpaceNum;
                    int x = j + stx;
                    map[y][x].display( (j-1)*blockSize, (i-1)*blockSize, blockSize, blockSize);
                }
            }
        }
    }

    public boolean canMove(int x,int y){
        System.out.println("move to " + x + "　" + y);
        if(x<=1 || x>=Setting.BlockNumWidth) return false;
        if(y<=1 || y>=Setting.BlockNumHeight) return false;

        int mgy = y -Setting.HeightSpaceNum;
        if(mgy<0) return false;
        if(mgy==0) return true;
        if(map[mgy][x].status==BlockStatus.LADDER) return true;
        if(map[mgy+1][x].status == BlockStatus.EMPTY) return false;
        if(map[mgy][x].status==BlockStatus.NORMAL) return false;
        if(map[mgy][x].status==BlockStatus.EMPTY) return true;
        return true;
    }

    boolean OverBoard(int x,int y){
        if(x<=1 || x>=Setting.BlockNumWidth) return true;
        if(y<1 || y>=Setting.HeightMapNum) return true;
        return false;
    }

    public boolean tryDig(int x,int y){
         y -= Setting.HeightSpaceNum;
        if(OverBoard(x,y)) return false;
        if(map[y][x].status!=BlockStatus.NORMAL) return false;
        map[y][x].dig();
        return true;
    }

    public boolean putItem(int x,int y,int id){
        y -= Setting.HeightSpaceNum;
        if(OverBoard(x,y)) return false;

        if(map[y][x].status==BlockStatus.EMPTY){
            map[y][x].status = BlockStatus.LADDER;
            return true;
        }else{
            return false;
        }
    }
}

