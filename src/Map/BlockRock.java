package Map;

import processing.core.PApplet;
import Setting.*;

/**
 * Created by Rober on 2017/5/5.
 */
public class BlockRock extends Block{

    public BlockRock(PApplet par){
        this.par = par;
        level = Setting.RockLevel;
    }

    void showNormal(float x,float y,float w,float h){
        par.image(imgRock, x, y, w, h);
    }

    public int dig(){
        int result;
        if(status==BlockStatus.LADDER) result = 10;
        else result = Setting.IronId;

        gotoDie();
        return result;
    }
}
