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

    void showNormal(float x,float y,float w,float h){
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
