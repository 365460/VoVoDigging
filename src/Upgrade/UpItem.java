package Upgrade;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/**
 * Created by chichen on 2017/5/28.
 */
public class UpItem
{
    PApplet parent;
    PVector position;
    float width , height;
    int level=0;// 3

    public PImage imgitem[] = new PImage[7];
    public PImage imgupgrade = new PImage();
    public PImage img0=new PImage();
    public PImage img1=new PImage();
    public PImage img2=new PImage();


    public  UpItem( PApplet parent, float x, float y, float width , float height) {
        this.parent = parent;
        position = new PVector(x, y);
        this.width  = width ;
        this.height = height;
    }
    public void imgchoose(){
        if(level==0) {
            img1 = imgitem[0];
            img2 = imgitem[1];
        }
        else  if(level==1)
        {   img1 = imgitem[1];
            img2 = imgitem[2];
        }
        else if(level==2)
        {    img1 = imgitem[2];
            img2 = imgitem[3];

        }
        else {
            img1 = imgitem[3];
            img2 = imgitem[4];
        }
        imgupgrade=imgitem[6];
        img0=imgitem[5];
    }

    public void display() {

        parent.noStroke();
        parent.noFill();

        parent.image(img1, position.x-20, position.y, width , height);
        parent.image(img0, position.x+130, position.y+50, 250 ,60 );
        parent.image(img2, position.x+400, position.y, width , height);
        parent.rect(position.x +580, position.y +70, 100, 90);
        parent.image(imgupgrade,position.x + 580, position.y + 70, 100, 90);

    }

    public Boolean checkMouseClicked() {
        PVector p1 = new PVector(parent.mouseX, parent.mouseY);
        if(p1.x<(position.x +580+100)&&p1.x>position.x +580)
        {
            if(p1.y<(position.y+70+90)&&p1.y>position.y+70)
            {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    public int getGetLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


}
