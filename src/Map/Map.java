package Map;

import Setting.*;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Created by Rober on 2017/6/14.
 */
public class Map {

    PApplet par;

    public float stx, sty;
    public PVector camera, cameraf, cameraMovement;
    public int width , height ;

    public int numW = Setting.BlockNumWidth;
    public int numH = Setting.HeightMapNum;
    int unit = Setting.BlockSize;
    public Map(){

    }

    public Map(PApplet par, int stx, int sty){

    }

    public void moveCamera(float x, float y){
        cameraf.x = x;
        cameraf.y = y;

        cameraMovement.x = x - camera.x;
        cameraMovement.y = y - camera.y;
    }

    public void update(){ // called by draw
        float v = Setting.getMoveV(par);

        boolean check = (cameraMovement.x!=0) || (cameraMovement.y !=0);
        if(cameraMovement.x < 0){
            cameraMovement.x += v;
            camera.x -= v;
        }
        else if(cameraMovement.x > 0){
            cameraMovement.x -= v;
            camera.x += v;
        }

        if(cameraMovement.y < 0 ){
            cameraMovement.y += v;
            camera.y -= v;
        }
        else if(cameraMovement.y > 0){
            cameraMovement.y -= v;
            camera.y += v;
        }
    }

    public void extend(int gx,int gy,int r){

    }

    public boolean canMove(int x,int y){
        return true;
    }

    public boolean isVectory(int x,int y){
        return false;
    }

    public boolean shouldFalling(int x,int y){
        return false;
    }

    public boolean canDig(int x,int y){
        return false;
    }

    public int dig(int x,int y,int t){
        return 0;
    }

    public int getBlockId(int x,int y){
        return 0;
    }

    public boolean putMine(int x,int y,int id){
        return false;
    }

    public boolean putItem(int x,int y,int id){
        return false;
    }

    public void move(int dir){

    }
}
