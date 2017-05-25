package Store;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/**
 * Created by chichen on 2017/5/18.
 */
public class Button {
    PApplet parent;
    PVector position;
   public PImage img;
    float  radius;


    public Button( PApplet parent, float x, float y, float radius) {
        this.parent = parent;
        this.position = new PVector(x, y);
        this.radius = radius;

    }
    public void display() {

        parent.ellipse(position.x,position.y,radius*2,radius*2);
        parent.image(img,position.x-2,position.y,radius*2+7,radius*2+7);

    }
    public boolean checkMouseClicked()
    {
        PVector p1 = new PVector(parent.mouseX, parent.mouseY);
        if(p1.dist(position)<=radius)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


}
