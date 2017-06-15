package Map;

import Setting.*;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

/**
 * Created by Rober on 2017/6/13.
 */
public class MapGround extends Map{
    PApplet par;
    PImage imgbackground, imgshop, imgupgrade, imgboard, imgboardtop, imgIn, imgGate;

    public MapGround(PApplet par,int stx,int sty){
        this.par = par;
        this.width = Setting.GameWidth;
        this.height = Setting.GameHeight;

        camera  = new PVector(stx, sty);
        cameraf = new PVector(stx, sty);
        cameraMovement =new PVector(0, 0);
        imgupgrade         = par.loadImage("image/upgrade.png");
        imgbackground      = par.loadImage("image/background6.png");
        imgshop            = par.loadImage("image/shop.png");
        imgboard           = par.loadImage("image/board.png");
        imgboardtop        = par.loadImage("image/boardtop.png");
        imgIn              = par.loadImage("image/in.png");
        imgGate            = par.loadImage("image/gate.png");
    }

    public boolean atShop(PVector pos){
        float x = pos.x + stx*unit;
        if(pos.y + sty*unit!= 180) return false;
        if(x>=Setting.PosShop && x<Setting.PosShop+unit*Setting.buildWidthNum)
            return true;
        else return false;
    }

    public boolean atUpgrade(PVector pos){
        float x = pos.x + stx*unit;
        if(pos.y + sty*unit!= 180) return false;
        if(x>=Setting.PosUpgrade && x<Setting.PosUpgrade+unit*Setting.buildWidthNum)
            return true;
        else return false;
    }

    public boolean atGate(PVector pos){
        float x = pos.x + stx*unit;
        if(pos.y + sty*unit!= 180) return false;
        if(x>=Setting.PosGate && x<Setting.PosGate+unit)
            return true;
        else return false;
    }

    public void display(PVector pos){
        update();
        par.translate(-camera.x, -camera.y);
        par.background(0);
        int HS = Setting.HeightSpaceNum*Setting.BlockSize;
        int base = HS-Setting.BlockSize;

        par.imageMode(PConstants.CORNER);
        par.image(imgbackground, 0, 0, numW*unit, Setting.HeightSpaceNum*unit);

        for(int i=0; i<50; i++){
            par.image(imgboard, i*unit, base+unit*2/4, unit, unit/4);
            par.image(imgboardtop, i*unit, base+unit*3/4, unit, unit/4);
        }

        par.image(imgshop, Setting.PosShop, 2*unit+5, unit*Setting.buildWidthNum, unit*Setting.buildWidthNum-15);
        par.image(imgupgrade, Setting.PosUpgrade, 2*unit+5, unit*Setting.buildWidthNum, unit*Setting.buildWidthNum-15);

        if(par.frameCount%20<=6)
            par.image(Block.imgGate[0], Setting.PosGate, 3*unit, unit, unit);
        else if(par.frameCount%20<=12)
            par.image(Block.imgGate[1], Setting.PosGate, 3*unit, unit, unit);
        else
            par.image(Block.imgGate[2], Setting.PosGate, 3*unit, unit, unit);

        if(atShop(pos)){
            int h = unit-10;
            if(par.frameCount%9<=2) h += 10;
            else if(par.frameCount%9<=5) h += 6;
            else  h += 0;
            par.image(imgIn, Setting.PosShop+unit/2, h, unit, unit);
        }
        if(atUpgrade(pos)){
            int h = unit-10;
            if(par.frameCount%9<=2) h += 10;
            else if(par.frameCount%9<=5) h += 6;
            else  h += 0;
            par.image(imgIn, Setting.PosUpgrade+unit/2, h, unit, unit);
        }
        par.translate(camera.x, camera.y);
    }

    public boolean canMove(int x,int y){
        if(y!=4) return false;
        return true;
    }
}
