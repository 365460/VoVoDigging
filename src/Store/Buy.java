package Store;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import Setting.*;

import java.util.Set;

/**
 * Created by chichen on 2017/5/18.
 */
public class Buy {
    PApplet parent;
    PVector position;
    float radius;
    PImage img=new PImage();
    public Buy( PApplet parent,float x, float y, float radius) {
        this.parent = parent;
        this.position = new PVector(x, y);
        this.radius =radius;
    }
    public void display() {

       parent.noStroke();
        parent.ellipse(position.x,position.y,radius*2,radius*2);
        parent.image(img,position.x,position.y,radius*2+7,radius*2+7);
    }
    //wood:1 , coal:2, iron:3 ,gold:4,diamond:5
    // ToIronId=1;ToGlodId=2;ToDiamondId=3;ToLedderId=4;
    public Integer checkMouseClicked(int[] buy_w) {
        PVector p1 = new PVector(parent.mouseX, parent.mouseY);
        if (p1.dist(position) <= radius) {
           if(buy_w[Setting.IronId]==3&&buy_w[Setting.WoodId]==1)
                return Setting.ToIronId;
            else if(buy_w[Setting.GoldId]==3&&buy_w[Setting.WoodId]==1)
                return Setting.ToGoldId;
            else if(buy_w[Setting.DiamondId]==3&&buy_w[Setting.WoodId]==1)
                return Setting.ToDiamondId;
            else if(buy_w[Setting.WoodId]==4)
                return Setting.ToLedderId;
            else
                return Setting.Nothing;
        }
        else
        {
            return Setting.Nothing;
        }
    }
}
