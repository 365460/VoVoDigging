/**
 * Created by chichen on 2017/5/18.
 */
package Store;
import Store.Items;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Set;

import Setting.*;
import Bag.*;
public class Store {
    PApplet par;
    float width , height;

    int need[ ][ ] = new int[4][5];//wood:0 , coal:1, iron:2 ,gold:3,diamond:4
    int minenum=5,toolnum=4;
    Bag bag;


    PImage storebk=new PImage();
    public PImage imgmine[] = new PImage[5];
    public ArrayList<Items> Item_arr= new ArrayList<>();

    public Store(PApplet par, int height, int width,Bag bag){
        this.par = par;
        this.height = height;
        this.width = width;
        this.bag = bag;

        setup();

    }

    //@Override
    public void setup(){

        for(int i=0;i<toolnum;i++)
        {
            for(int j=0;j<minenum;j++)
            {
                need[i][j]= 0;
            }
        }
        need[0][0]=4;
        need[1][0]=1;
        need[1][2]=3;
        need[2][0]=1;
        need[2][3]=4;
        need[3][0]=1;
        need[3][4]=5;
        Items i0 = new Items(par,115,70,200,150);//,limit[0]);
        Items i1 = new Items(par,500,70,200,150);//,limit[1]);
        Items i2 = new Items(par,100,370,200,150);//,limit[2]);
        Items i3 = new Items(par,500,370,200,150);//,limit[3]);
        // Items i4 = new Items(par,650,350,200,150,25,buy_w[4]);//,limit[4]);

        Item_arr.add(i0);
        Item_arr.add(i1);
        Item_arr.add(i2);
        Item_arr.add(i3);

        for(int i=0;i<toolnum;i++)
        {
            Item_arr.get(i).imgitem=par.loadImage("res/t"+i+".png");
            Item_arr.get(i).imgbuy=par.loadImage("res/button.png");

        }
        for(int i=0;i<minenum;i++)
        {
            imgmine[i]=par.loadImage("res/"+i+".png");
        }

        storebk=par.loadImage("res/10.jpg");
    }

    public void draw(){

        par.image(storebk,0,0,width,height);

        par.textSize(30);
        par.translate(0, -20);

        //t0
        par.fill(255,255,255,130);
        par.rect(80, 60, 320 , 250);
        par.image(imgmine[0],140,230,60,60);

        par.fill(0);
        par.text(bag.getMineNum(Setting.WoodId),210,265);
        par.text("/ ",245,265);
        par.text(need[0][0],260,265);

        //t1
        par.fill(255,255,255,145);
        par.rect(485, 60, 300 , 250);

        par.image(imgmine[0],490,230,60,60);
        par.fill(0);
        par.text(bag.getMineNum(Setting.WoodId),560,265);
        par.text("/ ",595,265);
        par.text(need[1][0],610,265);

        par.image(imgmine[2],640,230,60,60);
        par.text(bag.getMineNum(Setting.IronId),710,265);
        par.text("/ ",745,265);
        par.text(need[1][2],760,265);
        //t2

        par.fill(255,255,255,145);
        par.rect(80, 360, 320 , 230);
        par.fill(0);
        par.image(imgmine[0],90,530,60,60);
        par.text(bag.getMineNum(Setting.WoodId),160,565);
        par.text("/ ",195,565);
        par.text(need[2][0],210,565);

        par.image(imgmine[3],240,530,60,60);
        par.text(bag.getMineNum(Setting.GoldId),310,565);
        par.text("/ ",345,565);
        par.text(need[2][3],360,565);
        //t3
        par.fill(255,255,255,150);
        par.rect(485, 360, 300 , 230);
        par.fill(0);
        par.image(imgmine[0],490,530,60,60);
        par.text(bag.getMineNum(Setting.WoodId),560,565);
        par.text("/ ",595,565);
        par.text(need[3][0],610,565);

        par.image(imgmine[4],640,530,60,60);
        par.text(bag.getMineNum(Setting.DiamondId),710,565);
        par.text("/ ",745,565);
        par.text(need[3][4],760,565);



        for (Items i:Item_arr) {
            i.display();
        }

        par.translate(0, 20);

    }
    // @Override
    public void mousePressed() {
        par.println("mouse pressed");
        //TODO iterate through

        for (int i = 0; i < toolnum; i++) {
            if (Item_arr.get(i).checkMouseClicked()) {
                if(i==0){
                    if(need[0][0]<=bag.getMineNum(Setting.WoodId)) {
                        bag.addItem(Setting.ToLadderId);
                        for(int k=0;k<need[0][0];k++)
                            bag.delMine(Setting.WoodId);
                    }
                }
                else if(i==1) {
                    if(need[1][0]<=bag.getMineNum(Setting.WoodId)&&need[1][2]<=bag.getMineNum(Setting.IronId)) {
                        bag.addItem(Setting.ToIronId);
                        for (int k = 0; k < need[1][0]; k++)
                            bag.delMine(Setting.WoodId);
                        for(int k=0;k<need[1][2];k++)
                            bag.delMine(Setting.IronId);
                    }
                }
                else if(i==2) {
                    if(need[2][0]<=bag.getMineNum(Setting.WoodId)&&need[2][3]<=bag.getMineNum(Setting.GoldId)) {
                        bag.addItem(Setting.ToGoldId);
                        for(int k=0;k<need[2][0];k++)
                            bag.delMine(Setting.WoodId);
                        for(int k=0;k<need[2][3];k++)
                            bag.delMine(Setting.GoldId);

                    }
                }
                else if(i==3) {
                    if(need[3][0]<=bag.getMineNum(Setting.WoodId)&&need[3][4]<=bag.getMineNum(Setting.DiamondId)) {
                        bag.addItem(Setting.ToDiamondId);
                        for(int k=0;k<need[2][0];k++)
                            bag.delMine(Setting.WoodId);
                        for(int k=0;k<need[3][4];k++)
                            bag.delMine(Setting.DiamondId);
                    }
                }
                break;
            }
        }
    }

}
