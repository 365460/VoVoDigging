package Map;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by Rober on 2017/5/5.
 */
public class BlockSoil extends Block{

    PApplet par;

    public BlockSoil(PApplet par){
        this.par = par;
    }

    public void display(int x,int y,int w,int h){
        if(status==BlockStatus.EMPTY)
            par.image(imgEmpty, x, y, w, h);
        else
            par.image(imgSoil, x, y, w, h);
    }

}
