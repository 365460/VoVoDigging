package Map;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by Rober on 2017/5/5.
 */
public class BlockRock extends Block{

    public BlockRock(PApplet par){
        this.par = par;
        level = 10;
    }

    public void display(int x,int y,int w,int h){
        if(status==BlockStatus.NORMAL)
            par.image(imgRock, x, y, w, h);
        else
            par.image(imgEmpty, x, y, w, h);

        if(status==BlockStatus.LADDER){
            par.image(imgLadder, x, y, w, h);
        }
    }
}
