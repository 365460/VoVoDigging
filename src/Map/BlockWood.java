package Map;

import Setting.Setting;
import processing.core.PApplet;

/**
 * Created by Rober on 2017/5/23.
 */
public class BlockWood extends Block{
    public BlockWood(PApplet par){
        this.par = par;
        level = Setting.WoodLevel;
    }

    public void display(int x,int y,int w,int h){
        if(status==BlockStatus.NORMAL){
            par.image(imgWood, x, y, w, h);
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
        else result = Setting.WoodId;

        status = BlockStatus.EMPTY;
        return result;
    }
}
