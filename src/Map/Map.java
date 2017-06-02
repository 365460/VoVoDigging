package Map;

import Reminder.*;
import Setting.Setting;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

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
    PImage imgbackground, imgfog;

    public float stx, sty;
    public int numW = Setting.BlockNumWidth;
    public int numH = Setting.HeightMapNum;
    int blockSize = Setting.BlockSize;

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
        Block.imgRock      = par.loadImage("image/rock.jpg");
        Block.imgCoal      = par.loadImage("image/coal.jpg");
        Block.imgGold      = par.loadImage("image/gold.png");
        Block.imgWood      = par.loadImage("image/wood.jpg");
        Block.imgDiamond   = par.loadImage("image/dim.png");

        Block.imgWall      = par.loadImage("image/wall.jpg");
        Block.imgEmpty     = par.loadImage("image/empty.jpg");
        Block.imgLadder    = par.loadImage("image/ladder.png");

        Block.imgdig       = new PImage[5];
        for(int i=0; i<=4; i++)
            Block.imgdig[i]= par.loadImage("image/dig" + i + ".png");

        Block.imgflag = new PImage[3];
        for(int i=1; i<=3; i++)
            Block.imgflag[i-1] = par.loadImage("image/flag"+i+".png");

        imgbackground      = par.loadImage("image/background2.jpg");
        imgfog = par.loadImage("image/fog.jpg");

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

    public void display(int playerx,int playery,int light){

        int gx = playerx/Setting.BlockSize + 1, gy = playery/Setting.BlockSize + 1;
        int r = light;
        par.imageMode(PConstants.CORNER);
        par.background(0);
        // ground
        if(sty<=Setting.HeightSpaceNum){
            par.image(imgbackground, -(stx)*Setting.BlockSize, -(sty)*Setting.BlockSize, Setting.BlockNumWidth*Setting.BlockSize, (Setting.HeightSpaceNum)*Setting.BlockSize );
        }

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
                    if(shown[y][x]==false) par.image(imgfog, (j-1)*blockSize+dx*blockSize, (i-1)*blockSize+dy*blockSize, blockSize, blockSize);
                    else map[y][x].display( (j-1)*blockSize+dx*blockSize, (i-1)*blockSize+dy*blockSize, blockSize, blockSize);
                }
            }
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

        if(!map[y][x].canDig(tool)){
            return 0;
        }
        else return map[y][x].dig();

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

    public boolean putMine(int x,int y,int id){
        y -= Setting.HeightSpaceNum;
        if(OverBoard(x,y)) return false;

        if(map[y][x].status==BlockStatus.EMPTY){
            map[y][x] = BlockFactory.generate(par, id);
            return true;
        }
        else return false;
    }
}

