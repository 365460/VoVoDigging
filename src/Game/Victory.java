package Game;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;


/**
 * Created by chichen on 2017/6/8.
 */

public class Victory {
    Game game;
    PApplet par;
    PImage bk=new PImage();
    Log log;
    int width,height;



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


}