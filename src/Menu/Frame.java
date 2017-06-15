package Menu;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

/**
 * Created by Rober on 2017/6/6.
 */
public class Frame {
    PApplet par;
    PImage img;
    PVector pos;
    String title;

    int imglen;

    public Frame(PApplet par, PImage img, PVector pos, String title, int imglen){
        this.par = par;
        this.img = img;
        this.pos = pos;
        this.title = title;
        this.imglen = imglen;
    }

    public void display(){

        float x = pos.x, y = pos.y;
        if(isHover() && img!=null){
            y -= 70;
        }

        if(img!=null)
            par.image(img, x, y, imglen, imglen);
        else {
            par.stroke(255);
            par.noFill();
            par.rect(x ,y, imglen, imglen);

            par.textAlign(PConstants.CENTER);
            par.fill(255);
            par.text("+", x+imglen/2,y+imglen/2);
        }

        if(isHover() && img!=null){
            par.noFill();
            par.stroke(255, 0, 0);
            par.strokeWeight(5);
            par.rect(x ,y, imglen, imglen);
            par.strokeWeight(1);
        }

        par.fill(255);
        par.textAlign(PConstants.CENTER);
        par.textSize(25);
        par.stroke(5);
        par.text(title, x, y+imglen+30, imglen, 80);

    }

    public boolean isHover(){

        float x = par.mouseX, y = par.mouseY;
        if( pos.x>x || x>pos.x+imglen) return false;
        if( pos.y>y || y>pos.y+imglen) return false;

        return true;
    }
}
