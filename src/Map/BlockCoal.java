package Map;

import Setting.*;
import processing.core.PApplet;

/**
 * Created by Rober on 2017/5/23.
 */
public class BlockCoal extends Block{
    public BlockCoal(PApplet par){
        this.par = par;
        this.id  = Setting.CoalId;
        level = Setting.CoalLevel;
    }

    void showNormal(float x,float y,float w,float h){
//        par.fill(38,35,37);
        par.image(imgCoal, x, y, w, h);
    }

    public int dig(){
        int result;
        if(status==BlockStatus.LADDER) result = 10;
        else result = Setting.CoalId;

        gotoDie();
        return result;
    }
}
