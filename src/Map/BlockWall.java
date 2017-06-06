package Map;

import processing.core.PApplet;
import Setting.*;

/**
 * Created by Rober on 2017/5/5.
 */
public class BlockWall extends Block{

    public BlockWall(PApplet par){
        this.par = par;
        this.id = Setting.WallId;
        level = Setting.WallLevel;
    }

    void showNormal(float x,float y,float w,float h){
        par.image(imgWall, x, y, w, h);
    }

//    public void display(int x,int y,int w,int h){
//        System.out.println("wall shown");
//        if(status==BlockStatus.NORMAL)
//            par.image(imgWall, x, y, w, h);
//        else
//            par.image(imgEmpty, x, y, w, h);
//
//        if(status==BlockStatus.LADDER){
//            par.image(imgLadder, x, y, w, h);
//        }
//    }
    public int dig(){
        int result;
        if(status==BlockStatus.LADDER) result = 10;
        else result = 0;

        gotoDie();
        return result;
    }
}
