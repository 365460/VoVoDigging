package Game;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import static java.lang.Math.abs;

/**
 * Created by chichen on 2017/6/8.
 */

public class Victory {
    Game game;
    PApplet par;
    PImage bk=new PImage();
    Log log;
    int width,height;
    int recx=-1,recy=-1;
    int r=0,x=0,y=0,gcd=1,adx=1,ady=1;
    int h=0,v=0;
    boolean change=false;


    public Victory(Game game, PApplet par,int width,int height){
        this.game = game;
        this.par=par;
        this.height=height;
        this.width=width;
        setup();
    }

    public void setup() {
        log=new Log(par);
        bk=par.loadImage("res/victorybk.jpg");
        par.frameRate(30);

    }

    public void draw(int playerx, int playery){

        par.imageMode(PConstants.CENTER);
        if(recx==-1 && recx!=playerx)//ini
        {
            recx=playerx;
            recy=playery;
            gcd=GCD(abs(recx-width/2),abs(recy-height/2));
            adx=abs(recx-width/2)/gcd;
            ady=abs(recy-height/2)/gcd;
            if(recx>width/2)
                h=-1;//move left
            else
                h=1;//move right
            if(recy>height/2)
                v=1;//move up
            else
                v=-1;//move down

            par.image(bk, recx , recy , 30, 30 );
        }
        else if(change==false) {

           /* if(adx<10)
                adx=3*adx;
            if(ady<10)
                ady=3*ady;*/

            if (h > 0)
                x = adx ;
            else if (h < 0)
                x = -adx;

            if (v > 0) {
                y = -ady;
            }
            else if (v < 0){
                y = ady ;
            }

            r=r+40;

            if (recx + (( 40+ r)/ 2) > width || recx -((40+ r)  / 2 )< 0) {
                r = r-20;

            } else if (recy + (( 40+ r)/ 2) > height || recy -  (( 40+ r)/ 2) < 0) {
                r = r-20;

            }
            recx=recx+x;
            recy=recy+y;
            par.image(bk, recx , recy ,40+ r, 40+ r);
            if((recx-width/2)*(recx-width/2)+(recy-height/2)*(recy-height/2)<5000)
                change=true;

        }
        else {

            par.imageMode(PConstants.CORNER);

            par.image(bk, 0, 0, par.width, par.height);

            par.textAlign(par.CENTER);
            par.fill(255,0,0);
            par.textSize(100);
            par.text("Victory", par.width/2, 100);
            par.textSize(50);
            par.text("New Land", par.width/2, 160);
            log.display();
        }
        par.imageMode(PConstants.CORNER);
    }

    public int GCD(int a, int b) {
        if (b==0) return a;
        return GCD(b,a%b);
    }

}