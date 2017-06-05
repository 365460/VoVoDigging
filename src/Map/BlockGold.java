package Map;

import processing.core.PApplet;
import Setting.*;

/**
 * Created by Rober on 2017/5/18.
 */
public class BlockGold extends Block{
    public BlockGold(PApplet par){
        this.par = par;
        this.id  = Setting.GoldId;
        level = Setting.GoldLevel;
    }

    void showNormal(float x,float y,float w,float h){
        par.image(imgSoil, x, y, w, h);
        par.image(imgGold, x, y, w, h);
    }

    public int dig(){
        int result;
        if(status==BlockStatus.LADDER) result = 10;
        else result = Setting.GoldId;

        gotoDie();
        return result;
    }
}
