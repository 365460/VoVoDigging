package Upgrade;

import Bag.Bag;
import Setting.Setting;
import Store.Items;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

/**
 * Created by chichen on 2017/5/27.
 */
public class Upgrade {
    PApplet par;
    float width , height;
    Bag bag;
    PImage upgradebk=new PImage();
    PImage imgmine[]=new PImage[5];
    public ArrayList<UpItem> upItem_arr= new ArrayList<>();
    int imgnum=7,minenum=5;
    int upnum=2;
    int need[][]= new int[2][5];
    int u1_le=0,u0_le=0;
    public Upgrade (PApplet par, int height, int width, Bag bag){
        this.par = par;
        this.height = height;
        this.width = width;
        this.bag = bag;
        setup();
    }
    public void setup(){

        upgradebk=par.loadImage("res/upgradebk.png");
        for(int i=0;i<minenum;i++)
        {
            imgmine[i]=par.loadImage("res/"+i+".png");
        }

        UpItem u0=new UpItem (par,100,70,150,150);
        for(int i=0;i<imgnum;i++)
        {
            u0.imgitem[i]=par.loadImage("res/L"+(i+1)+".png");
        }
        upItem_arr.add(u0);
        u0_le=u0.getGetLevel();

        UpItem u1=new UpItem (par,100,400,150,150);
        for(int i=0;i<imgnum;i++)
        {
            u1.imgitem[i]=par.loadImage("res/L"+(i+1)+".png");
        }
        upItem_arr.add(u1);
        u1_le=u1.getGetLevel();
    }
    public void draw() {
        par.image(upgradebk,0,0,width,height);
        //u0
        par.fill(255,255,255,175);
        par.rect(50,70,750,180);
        //u1
        par.rect(50,400,750,180);

        for(int i=0;i<upnum;i++)
        {
            upItem_arr.get(i).imgchoose();
            upItem_arr.get(i).display();
        }
        par.fill(0);
        par.textSize(30);
        par.image(imgmine[1],220,180,60,60);
        par.text(bag.getMineNum(Setting.CoalId),300,230);
        par.text("/ ",335,230);
        par.text((u0_le+4)*(u0_le+4),355,230);



        par.fill(0);
        par.textSize(30);
        par.image(imgmine[1],220,510,60,60);
        par.text(bag.getMineNum(Setting.CoalId),300,550);
        par.text("/ ",335,550);
        par.text((u1_le+4)*(u1_le+4),355,550);

    }
    public void mousePressed() {
        par.println("mouse pressed");
        //TODO iterate through

        for (int i = 0; i < upnum; i++) {
            if (upItem_arr.get(i).checkMouseClicked()) {
                if(i==0) {
                    if ((u0_le + 4) * (u0_le + 4) <= bag.getMineNum(Setting.CoalId)) {
                        for (int j = 0; j< (u0_le + 4) * (u0_le + 4); j++) {
                            bag.delMine(Setting.CoalId);
                        }
                        u0_le=u0_le+1;
                        upItem_arr.get(i).setLevel(u0_le);

                    }
                }
                break;
            }
        }
    }

}
