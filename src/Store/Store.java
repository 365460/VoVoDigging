/**
 * Created by chichen on 2017/5/18.
 */
package Store;
import Store.Items;
import oracle.jrockit.jfr.openmbean.EventSettingType;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Set;

import Setting.*;
import Bag.*;
public class Store {
    PApplet par;
    float width , height;
    int[] limit = new int[Setting.MineNum];
    int[] buy_w = new int[Setting.MineNum];//wood:1 , coal:2, iron:3 ,gold:4,diamond:5
    int num;
    Buy buy;
    Bag bag;
    Button i,b;
    PImage informbk=new PImage();
    PImage storebk=new PImage();
    int state=0;//store:0 , information:1;
    public ArrayList<Items> Item_arr= new ArrayList<>();

    public Store(PApplet par, int height, int width,Bag bag){
        this.par = par;
        this.height = height;
        this.width = width;
        this.num= Setting.MineNum;
        this.bag = bag;
        this.limit=limit;


        buy =new Buy(par,110,500,50);
        i = new Button(par, 780, 60, 30);
        b = new Button(par, 780, 550, 30);
        setup();

    }


    //@Override
    public void setup(){
        num=5;
        for(int i=0;i<6;i++)
        {
            buy_w[i] = 0;
        }
        Items i0 = new Items(par,250,150,200,150,25,buy_w[0],limit[0]);
        Items i1 = new Items(par,550,150,200,150,25,buy_w[1],limit[1]);
        Items i2 = new Items(par,150,350,200,150,25,buy_w[2],limit[2]);
        Items i3 = new Items(par,400,350,200,150,25,buy_w[3],limit[3]);
        Items i4 = new Items(par,650,350,200,150,25,buy_w[4],limit[4]);

        Item_arr.add(i0);
        Item_arr.add(i1);
        Item_arr.add(i2);
        Item_arr.add(i3);
        Item_arr.add(i4);
        //ini item,button image
        for(int i=0;i<num;i++)
        {
            Item_arr.get(i).imgitem=par.loadImage("res/"+i+".png");
            Item_arr.get(i).Button_arr.get(0).img=par.loadImage("res/add.png");
            Item_arr.get(i).Button_arr.get(1).img=par.loadImage("res/sub.png");
        }
//if(buy==null) System.out.println("buy is null");
        buy.img=par.loadImage("res/button.png");
        i.img=par.loadImage("res/i.png");
        b.img=par.loadImage("res/back.png");
        informbk=par.loadImage("res/inform.png");
        storebk=par.loadImage("res/bk.png");
    }
    public void draw(){
        if(state==0) {
            par.imageMode(par.CENTER);
            par.image(storebk,width/2,height/2,width,height);
           // par.background(64, 224, 208);

            for (Items i : Item_arr) {
                i.display();

            }
            buy.display();
            i.display();
        }
        else
        {
            par.background(0);
            b.display();
            par.image(informbk,360,300,720,600);
        }
    }
   // @Override
    public void mousePressed() {
        par.println("mouse pressed");
        //TODO iterate through
        if(state==0) {
            for (int i = 0; i < num; i++) {
                if (Item_arr.get(i).checkMouseClicked()) {
                    buy_w[i + 1] = Item_arr.get(i).getBuy_w();
                    break;
                }
            }
            int Tool = buy.checkMouseClicked(buy_w);
            if( i.checkMouseClicked() )
                state=1;
        }
        else
        {
            if(b.checkMouseClicked())
                state=0;
        }
    }


}



