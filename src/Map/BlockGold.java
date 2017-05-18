package Map;

import processing.core.PApplet;
import Setting.*;

/**
 * Created by Rober on 2017/5/18.
 */
public class BlockGold extends Block{
    public BlockGold(PApplet par){
        this.par = par;
        level = Setting.GoldLevel;
    }

    public void display(int x,int y,int w,int h){
        if(status==BlockStatus.NORMAL){
            par.image(imgSoil, x, y, w, h);
            par.image(imgGold, x, y, w, h);
        }
        else
            par.image(imgEmpty, x, y, w, h);

        if(status==BlockStatus.LADDER){
            par.image(imgLadder, x, y, w, h);
        }
    }

    public int dig(){
        status = BlockStatus.EMPTY;
        return Setting.GoldId;
    }
}
