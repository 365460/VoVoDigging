package Map;

import Setting.Setting;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by Rober on 2017/5/5.
 */
public class BlockSoil extends Block{

    public BlockSoil(PApplet par){
        this.par = par;
    }

    void showNormal(float x,float y,float w,float h){
        par.image(imgSoil, x, y, w, h);
    }

    public int dig(){
        int result;
        if(status==BlockStatus.LADDER) result = 10;
        else result = Setting.SoilId;

        gotoDie();
        return result;
    }

}
