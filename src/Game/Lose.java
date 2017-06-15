package Game;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import static java.lang.Math.abs;
/**
 * Created by chichen on 2017/6/11.
 */
public class Lose {
    Game game;
    PApplet par;
    PImage bk=new PImage();
    Log log;
    int width,height;

    public Lose(Game game, PApplet par,int width,int height){
        this.game = game;
        this.par=par;
        this.height=height;
        this.width=width;
        setup();
    }

    public void setup() {
        log=new Log(par);
        bk=par.loadImage("res/losebk.jpg");

    }

    public void draw(){

        par.imageMode(PConstants.CORNER);

        par.image(bk,0,0,par.width,par.height);

        par.textAlign(par.CENTER);
        par.fill(255, 0, 0);
        par.textSize(100);
        par.text("Lose", par.width / 2, 100);
        par.textSize(50);
        par.text("Die", par.width / 2, 160);

        log.display();
    }

}

