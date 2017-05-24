package Map;

import Setting.Setting;
import processing.core.PApplet;

/**
 * Created by Rober on 2017/5/23.
 */
public class BlockDiamond extends Block{

    public BlockDiamond(PApplet par){
        this.par = par;
        level = Setting.DiamondLevel;
    }

    public void display(int x,int y,int w,int h){
        if(status==BlockStatus.NORMAL){
            par.image(imgSoil, x, y, w, h);
            par.image(imgDiamond, x, y, w, h);
        }
        else
            par.image(imgEmpty, x, y, w, h);

        if(status==BlockStatus.LADDER){
            par.image(imgLadder, x, y, w, h);
        }
    }

    public int dig(){
        int result;
        if(status==BlockStatus.LADDER) result = 10;
        else result = Setting.DiamondId;

        status = BlockStatus.EMPTY;
        return result;
    }
}
