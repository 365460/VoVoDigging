package Map;

import Setting.Setting;
import processing.core.PApplet;

import java.util.Set;

import static java.lang.Math.max;

/**
 * Created by Rober on 2017/5/5.
 */
public class Map {
    PApplet par;

    public int stx, sty;
    public int numW = Setting.BlockNumWidth;
    public int numH = Setting.BlockNumHeight;
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

        for(int i=1; i<=numH; i++) map[i][1] = new BlockWall(par);
        for(int i=1; i<=numW; i++) map[numH][i] = new BlockWall(par);
        for(int i=1; i<=numH; i++) map[i][numW] = new BlockWall(par);

        for(int i=1; i<numH; i++){
            for(int j=2; j<numW; j++){
                int id = (int)par.random(0, 5);
                if(id>=2) map[i][j] = new BlockSoil(this.par);
                else map[i][j] = new BlockRock(this.par);
            }
        }
    }

    public void display(){
        par.background(0);
        for(int i=max(-sty,1), ii = max(sty*blockSize,0); i<=numH; i++, ii+=blockSize){
            if(ii+blockSize > par.height) break;

            for(int j=stx, jj = 0; j<=numW; j++, jj+=blockSize){
                if(jj+blockSize > par.width) break;

                                // width, height
                map[i][j].display(jj, ii, blockSize, blockSize);
            }
        }
    }
}

