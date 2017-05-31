package Store;

import processing.core.PVector;
import processing.core.*;

/**
 * Created by chichen on 2017/5/18.
 */
public class Items {

    PApplet parent;
    PVector position;
    float width , height;

    public PImage imgitem = new PImage();
    public PImage imgbuy = new PImage();

    public Items( PApplet parent, float x, float y, float width , float height) {
        this.parent = parent;
        position = new PVector(x, y);
        this.width  = width ;
        this.height = height;
    }

    public void display() {

        parent.noStroke();
        parent.noFill();

        parent.image(imgitem, position.x, position.y, width , height);
        parent.rect(position.x + width+10, position.y + height-80, 80, 80);
        parent.image(imgbuy,position.x + width+10, position.y + height-80, 80, 80);

    }

    public Boolean checkMouseClicked() {
        PVector p1 = new PVector(parent.mouseX, parent.mouseY);
        if(p1.x<(position.x+width+80)&&p1.x>position.x + width+20)
        {
            if(p1.y<(position.y+height)&&p1.y>position.y+height-80)
            {
                return true;
            }
			else {
                return false;
            }
         }
		return false;
    }

}
