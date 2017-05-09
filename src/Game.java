import com.sun.deploy.net.proxy.pac.PACFunctions;
import processing.core.PApplet;
import Map.*;
import Setting.Setting;

/**
 * Created by Rober on 2017/5/5.
 */
public class Game {
    PApplet par;
    Map map;
    Player player;

    int height, width;
    public Game(PApplet par, int height, int width){
        this.par = par;
        this.height = height;
        this.width = width;

        map = new Map(this.par, 8, Setting.HeightSpaceNum);
        player = new Player(this.par, 5, 5);
    }

    public void draw(){
        map.display();
        player.display();

        //TODO: mask
    }

    public void handleKey(){
        int dir = 0;
        if(par.keyCode==par.UP || par.key=='w') dir = 1;
        else if(par.keyCode==par.RIGHT || par.key=='d') dir = 2;
        else if(par.keyCode==par.DOWN || par.key=='s') dir = 3;
        else if(par.keyCode==par.LEFT || par.key=='a') dir = 4;

        if(par.key == par.CODED)
            player.move(dir, map);
        else
            player.digBlock(dir, map);

    }
}
