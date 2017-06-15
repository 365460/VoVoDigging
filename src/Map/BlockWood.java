package Map;

import Setting.Setting;
import processing.core.PApplet;

/**
 * Created by Rober on 2017/5/23.
 */
public class BlockWood extends Block{
    public BlockWood(PApplet par){
        this.par = par;
        this.id  = Setting.WoodId;
        level = Setting.WoodLevel;
    }

    void showNormal(float x,float y,float w,float h){
//        par.fill(94,66,31);
        par.image(imgWood, x, y, w, h);
    }

    public int dig(){
        int result;
        if(status==BlockStatus.LADDER) result = 10;
        else result = Setting.WoodId;

        gotoDie();
        return result;
    }
}
