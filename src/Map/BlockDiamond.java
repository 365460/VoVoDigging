package Map;

import Setting.Setting;
import processing.core.PApplet;

/**
 * Created by Rober on 2017/5/23.
 */
public class BlockDiamond extends Block{

    public BlockDiamond(PApplet par){
        this.par = par;
        this.id  = Setting.DiamondId;
        level = Setting.DiamondLevel;
    }

    void showNormal(float x,float y,float w,float h){
        par.image(imgSoil, x, y, w, h);
        par.image(imgDiamond, x, y, w, h);
    }

    public int dig(){
        int result;
        if(status==BlockStatus.LADDER) result = 10;
        else result = Setting.DiamondId;

        gotoDie();
        return result;
    }
}
