package Upgrade;

import Bag.Bag;
import Reminder.Reminder;
import Setting.Setting;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

/**
 * Created by chichen on 2017/5/27.
 */
// light   9----->16 ,25,36,49,56
//bag     60---->80,100,120,140,160
public class Upgrade {

    PApplet par;
    float width , height;
    Bag bag;
    PImage upgradebk=new PImage();
    PImage imgmine[]=new PImage[5];
    public ArrayList<UpItem> upItem_arr= new ArrayList<>();
    int imgnum=5,minenum=5;
    int upnum=2;
    int need[][]= new int[2][5];
    int u1_le=0,u0_le=3;
    int inibagweight=60;

    public Upgrade (PApplet par, int height, int width, Bag bag){
        this.par = par;
        this.height = height;
        this.width = width;
        this.bag = bag;
        setup();
    }

    public void setup(){
        inibagweight=bag.getMineLimit();
        for(int i=0;i<5;i++) {
            need[1][i] = 8 + 8* i;
            need[0][i] = (4 + i) * (4 + i);

        }

        for(int i=0;i<5;i++)
        {
            System.out.print(need[0][i]+ "  ");
        }
        System.out.println();
        for(int i=0;i<5;i++)
        {
            System.out.print(need[1][i]+ "  ");
        }
        System.out.println();
        upgradebk=par.loadImage("res/upgradebk.png");
        for(int i=0;i<minenum;i++)
        {
            imgmine[i]=par.loadImage("res/"+i+".png");
        }
        //light
        UpItem u0=new UpItem (par,100,70,150,150);
        for(int i=0;i<imgnum;i++)
        {
            u0.imgitem[i]=par.loadImage("res/L"+(i+1)+".png");
        }
        u0.imgitem[5]=par.loadImage("res/ch.png");
        u0.imgitem[6]=par.loadImage("res/up.png");
        upItem_arr.add(u0);
        u0_le=bag.getLight();
        System.out.println( u0_le);
        //bag
        UpItem u1=new UpItem (par,100,400,150,150);
        for(int i=0;i<imgnum;i++)
        {
            u1.imgitem[i]=par.loadImage("res/B"+(i+1)+".png");
        }
        u1.imgitem[5]=par.loadImage("res/ch.png");
        u1.imgitem[6]=par.loadImage("res/up.png");
        upItem_arr.add(u1);
        System.out.println(bag.getMineLimit());
        u1_le=0;
        System.out.println(u1_le);
    }

    public void draw() {
        par.textAlign(par.LEFT, par.DOWN);

        par.image(upgradebk,0,0,width,height);
        //u0
        par.fill(255,255,255,125);
        par.rect(50,70,750,180);
        //u1
        par.rect(50,390,750,180);

        //u0
        for(int i=0;i<upnum;i++)
        {
            upItem_arr.get(i).imgchoose();
            upItem_arr.get(i).display();
        }
        par.fill(0);
        par.textSize(30);
        par.image(imgmine[1],260,180,60,60);
        String s = bag.getMineNum(Setting.CoalId) + " / " + need[0][u0_le-3];
        par.text( s, 345, 230);
//        par.text(bag.getMineNum(Setting.CoalId),345,230);
//        par.text("/ ",380,230);
//        par.text(need[0][u0_le-3],400,230);


        //u1
        par.fill(0);
        par.textSize(27);
        par.image(imgmine[3],215,510,50,50);
        s = bag.getMineNum(Setting.GoldId) + " / " + (need[1][u1_le]-2);
        par.text( s, 275, 550);

        par.fill(0);
        par.textSize(28);
        par.image(imgmine[4],365,515,50,50);
        s = bag.getMineNum(Setting.DiamondId) + " / " + (need[1][u1_le]);
        par.text( s, 425, 550);


    }

    public void mousePressed()throws Reminder {
        //TODO iterate through

        for (int i = 0; i < upnum; i++) {
            if (upItem_arr.get(i).checkMouseClicked()) {
                if(i==0) {//light
                    if ( need[0][u0_le-3]<= bag.getMineNum(Setting.CoalId)) {
                        for (int j = 0; j< need[0][u0_le-3]; j++) {
                            bag.delMine(Setting.CoalId);
                        }
                        u0_le=u0_le+1;
                        bag.setLight(u0_le);
                        upItem_arr.get(i).setLevel(u0_le-3);
                    }
                    else{
                        Reminder re = new Reminder(par, "You need more resources. QQ");
                        throw re;
                    }
                }
                else if(i==1) {//bag
                    if ((need[1][u1_le]-2) <= bag.getMineNum(Setting.GoldId)) {
                        if(need[1][u1_le] <= bag.getMineNum(Setting.DiamondId)) {
                            for (int j = 0; j < (need[1][u1_le] - 2); j++) {
                                bag.delMine(Setting.GoldId);
                            }
                            for (int j = 0; j < need[1][u1_le] ; j++) {
                                bag.delMine(Setting.DiamondId);
                            }
                            u1_le = u1_le+1;
                            bag.setMineLimit(bag.getMineLimit()+100);
                            upItem_arr.get(i).setLevel(u1_le);

                        }
                    }
                    else{
                        Reminder re = new Reminder(par, "You need more resources. QQ");
                        throw re;
                    }
                }
                break;
            }
        }
    }

}