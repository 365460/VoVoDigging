package Map;

import Setting.*;
import processing.core.PApplet;

/**
 * Created by Rober on 2017/5/23.
 */
public class BlockCoal extends Block{
    public BlockCoal(PApplet par){
        this.par = par;
        level = Setting.CoalLevel;
    }

    public void display(int x,int y,int w,int h){
        if(status==BlockStatus.NORMAL){
            par.image(imgCoal, x, y, w, h);
        }
        else
            par.image(imgEmpty, x, y, w, h);

        if(status==BlockStatus.LADDER){
            par.image(imgLadder, x, y, w, h);
        }
    }

    public int dig(){
        status = BlockStatus.EMPTY;
        return Setting.CoalId;
    }
}
