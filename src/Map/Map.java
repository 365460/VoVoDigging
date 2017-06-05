package Map;

import Reminder.*;
import Setting.Setting;
import processing.core.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

import static java.lang.Math.max;

/**
 * Created by Rober on 2017/5/5.
 */
public class Map {
    PApplet par;
    PImage imgbackground, imgfog, imgshop, imgboard, imgboardtop, imgupgrade, imgIn;

    public float stx, sty;
    public int numW = Setting.BlockNumWidth;
    public int numH = Setting.HeightMapNum;
    int unit = Setting.BlockSize;

    public Block[][] map;
    boolean shown[][];
    public Map(PApplet par, int stx, int sty){
        this.par = par;
        this.stx = stx;
        this.sty = sty;

        map = new Block[numH+10][numW+10];
        shown = new boolean[numH+10][numW+10];

        System.out.println("numW = " + numW + ", numH = " + numH);
        System.out.println("width = " + par.width + ", height = " + par.height);
        System.out.println("stx = " + stx + ", sty = " + sty);

        // init Block image
        Block.imgSoil      = par.loadImage("image/soil.jpg");
        Block.imgRock      = par.loadImage("image/IronMap.png");
        Block.imgCoal      = par.loadImage("image/coalMap.png");
        Block.imgGold      = par.loadImage("image/goldMap.png");
        Block.imgWood      = par.loadImage("image/woodMap.png");
//        Block.imgWood      = par.loadImage("image/wood.jpg");
        Block.imgDiamond   = par.loadImage("image/dim.png");

        Block.imgWall      = par.loadImage("image/wallMap.png");
        Block.imgEmpty     = par.loadImage("image/empty.jpg");
        Block.imgLadder    = par.loadImage("image/ladder.png");

        Block.imgdig       = new PImage[5];
        for(int i=0; i<=4; i++)
            Block.imgdig[i]= par.loadImage("image/dig" + i + ".png");

        Block.imgflag = new PImage[3];
        for(int i=1; i<=3; i++)
            Block.imgflag[i-1] = par.loadImage("image/flag"+i+".png");

        imgbackground      = par.loadImage("image/background6.png");
        imgfog             = par.loadImage("image/fog.jpg");
        imgshop            = par.loadImage("image/shop.png");
        imgboard           = par.loadImage("image/board.png");
        imgboardtop        = par.loadImage("image/boardtop.png");
        imgupgrade         = par.loadImage("image/upgrade.png");
        imgIn              = par.loadImage("image/in.png");

        for(int i=1; i<=numH; i++){
            for(int j=1; j<=numW; j++){
                shown[i][j] = false;
            }
        }

        try{
            FileReader fr = new FileReader("map.data");
            BufferedReader br = new BufferedReader(fr);
            for(int i=1; i<=numH; i++){
                String tmp = br.readLine();
                String tmpArray[] = tmp.split("\\s");
                for(int j=1; j<=numW; j++){
                    int id = Integer.parseInt(tmpArray[j-1]);
                    map[i][j] = BlockFactory.generate(par, id);
                }
            }

            fr.close();
        }
        catch(Exception e){

        }

    }

    public void extend(int x,int y,int r){
        y -= Setting.HeightSpaceNum;
        for(int i=-r; i<=r; i++){
            for(int j=-r; j<=r; j++){
                if(i+y<=0 || x+j<=0) continue;;
                shown[i+y][j+x] = true;
            }
        }
    }

    public boolean atShop(PVector pos){
        float x = pos.x + stx*unit;
        if(pos.y + sty*unit!= 180) return false;
        if(x>=Setting.PosShop && x<Setting.PosShop+unit*Setting.buildWidthNum)
            return true;
        else return false;
    }

    public boolean atUpgrade(PVector pos){
        float x = pos.x + stx*unit;
        if(pos.y + sty*unit!= 180) return false;
        if(x>=Setting.PosUpgrade && x<Setting.PosUpgrade+unit*Setting.buildWidthNum)
            return true;
        else return false;
    }

    public void display(int playerx,int playery,int light,PVector pos){

        int gx = playerx/unit + 1, gy = playery/unit + 1;
        par.background(0);


        // ground


        float dx = stx - (int)stx ;
        float dy = sty - (int)sty;

        int styy = dy==0? (int)sty:(int)sty+1;
        int stxx = dx==0? (int)stx:(int)stx+1;

        for(int i=1; i<=Setting.ScreenHeightNum; i++){
            for(int j=1; j<=Setting.ScreenWidthNum; j++){

                if(styy+i>Setting.HeightSpaceNum) { // bottom
//                    if( ! ((i-r<=gy && gy<=i+r)&&(j-r<=gx && gx<=j+r)) ) continue;
                    int y =  (int)sty+i-Setting.HeightSpaceNum;
                    int x =  j + (int)stx;

                    /*  easy mode */
//                    if(shown[y][x]==false) par.image(imgfog, (j-1)*unit+dx*unit, (i-1)*unit+dy*unit, unit, unit);
//                    else map[y][x].display( (j-1)*unit+dx*unit, (i-1)*unit+dy*unit, unit, unit);
                    /* difficult mode */

                    map[y][x].display( (j-1)*unit+dx*unit, (i-1)*unit+dy*unit, unit, unit);
                }
            }
        }


        /* difficult mode */

        int r = (4+light )* Setting.BlockNumWidth;
        PGraphics mask = par.createGraphics(par.width, par.height);
        mask.beginDraw();

        mask.background(0,0,0);
        mask.fill(255,255,255);
        mask.ellipse(pos.x+unit/2, pos.y+unit/2, r, r);
        mask.endDraw();
        par.blend(mask, 0, 0, par.width, par.height, 0 ,0, par.width, par.height,  PConstants.DARKEST);


        par.imageMode(PConstants.CORNER);
        if(sty<=Setting.HeightSpaceNum){
            par.image(imgbackground, -(stx)*unit, -(sty)*unit, Setting.BlockNumWidth*unit, (Setting.HeightSpaceNum)*unit );


            for(int i=0; i<Setting.ScreenWidthNum; i++){
                par.image(imgboard, i*unit, (4-sty)*unit - Setting.BlockNumWidth/2, unit, unit/4);
                par.image(imgboardtop, i*unit, (4-sty)*unit - Setting.BlockNumWidth/4, unit, unit/4);
            }
/**/
            par.translate( -stx*unit, -sty*unit);

            par.imageMode(par.CORNER);
            par.image(imgshop, Setting.PosShop, 2*unit+5, unit*Setting.buildWidthNum, unit*Setting.buildWidthNum-15);
            par.image(imgupgrade, Setting.PosUpgrade, 2*unit+5, unit*Setting.buildWidthNum, unit*Setting.buildWidthNum-15);

            if(atShop(pos)){
                int h = unit-10;
                if(par.frameCount%9<=2) h += 10;
                else if(par.frameCount%9<=5) h += 6;
                else  h += 0;
                par.image(imgIn, Setting.PosShop+unit/2, h, unit, unit);
            }
            if(atUpgrade(pos)){
                int h = unit-10;
                if(par.frameCount%9<=2) h += 10;
                else if(par.frameCount%9<=5) h += 6;
                else  h += 0;
                par.image(imgIn, Setting.PosUpgrade+unit/2, h, unit, unit);
            }

            par.translate( stx*unit, sty*unit);

            /**/
        }

    }

    public boolean canMove(int x,int y){
        if(x<=1 || x>=Setting.BlockNumWidth) return false;
        if(y<=1 || y>=Setting.BlockNumHeight) return false;

        int mgy = y -Setting.HeightSpaceNum;
        if(mgy==0) return true;
        if(mgy<0)  return false;
        if(map[mgy][x].status==BlockStatus.FIN) return true;

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

    public boolean isEmpty(int x,int y){
        y -= Setting.HeightSpaceNum;
        if(OverBoard(x,y)) return true;

        return map[y][x].isEmpty();
    }

    public boolean isVectory(int x,int y){
        y -= Setting.HeightSpaceNum;
        if(OverBoard(x, y)) return false;
        return map[y][x].status == BlockStatus.FIN;
    }

    public boolean shouldFailing(int x,int y){
        y -= Setting.HeightSpaceNum;

        if(OverBoard(x, y)) return false;
        if(map[y][x].status == BlockStatus.LADDER)
            return false;
        if(map[y+1][x].status == BlockStatus.EMPTY)
            return true;

        return false;
    }

    public boolean canDig(int x,int y){
        y -= Setting.HeightSpaceNum;
        if(OverBoard(x, y) ) return false;
        else if(map[y][x].isDigging()) return false;
        else if(map[y][x].isEmpty()) return false;
        return true;
    }

    public int dig(int x,int y,int tool){// 0 -> fail
         y -= Setting.HeightSpaceNum;
        if(OverBoard(x,y)) return 0;

        if(!map[y][x].canDig(tool)) return 0;

        else{
            System.out.println("dig " + x + " " + y);
            return map[y][x].dig();
        }
    }

    public boolean putItem(int x,int y,int id){ // just ladder
        y -= Setting.HeightSpaceNum;
        if(OverBoard(x,y)) return false;

        if(map[y][x].status==BlockStatus.EMPTY){
            map[y][x].status = BlockStatus.LADDER;
            return true;
        }else{
            return false;
        }
    }

    public void delPutMine(int x, int y, int id,int time){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    System.out.println("start delay");
                    Thread.sleep(time + 10);
                    putMine(x, y, id);
                    System.out.println("put");
                }
                catch(Exception e){
                }
            }
        });
        thread.start();
    }

    public boolean putMine(int x,int y,int id){

        y -= Setting.HeightSpaceNum;
        if(OverBoard(x,y)) return false;

        System.out.println("put "+x+" " + y);
        System.out.println("ss = " + map[y][x].status);
        if(map[y][x].status==BlockStatus.EMPTY){
            map[y][x] = BlockFactory.generate(par, id);
            return true;
        }
        else return false;
    }

    public int getBlockId(int x,int y){
        y -= Setting.HeightSpaceNum;
        if(OverBoard(x, y)) return 0;

        return map[y][x].getId();
    }
}

