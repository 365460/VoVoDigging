import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by Rober on 2017/6/1.
 */
public class Loading {
    PApplet par;
    PImage img1, img2;
    PImage bg, bG;
    PImage flag[];
    String message;
    int progress = 0;
    boolean ok = false;

    public Loading (PApplet par){
        this.par = par;
        img1 = par.loadImage("image/00.png");
        img2 = par.loadImage("image/01.png");
        bg   = par.loadImage("image/load2.png");
        bG   = par.loadImage("image/loadBg.jpg");

        flag = new PImage[3];
        for(int i=1; i<=3; i++){
            flag[i-1] = par.loadImage("image/flag"+i+".png");

        }
    }

    public void display(){
        par.fill(255);
        par.textSize(40);
        par.background(0);

        int x = par.mouseX;
        int y = par.mouseY;
        par.image(bG, 0, 0, par.width, par.height);
        par.image(bg, 187, 128, 500, 200);

        par.textSize(20);
        par.text("LOADING.....(" + progress + " %)", 569, 432);
        par.text(message,  574, 461);

        int px, py;
        px = 158;
        py = 320+50;

        int now = 600*progress/100 + px - 20;
        if(par.frameCount%6>=3 ) par.image(img1 ,now, py-50, 50,50 );
        else par.image(img2,now, py-50,50,50 );

        int xx = 736, yy = 313;
        if(par.frameCount%6<=1) par.image(flag[0], xx, yy, 50, 50);
        else if(par.frameCount%6<=3) par.image(flag[1], xx, yy, 50, 50);
        else par.image(flag[2], xx, yy, 50, 50);

        par.stroke(255,0,0);
//        par.stroke(0, 12, 255);

        par.strokeWeight(3);
        par.fill(255,155,127);
//        par.fill(111, 118, 247);

        par.rect(px, py, 600, 30, 50, 50, 50, 50);
        par.fill(255, 0, 0);
//        par.fill(0, 12, 255);
        par.rect(px+5, py+5, (600-10)*progress/100, 20, 50, 50, 50, 50);

        if(progress==100) ok = true;
    }

    public void setMessage(String s){
        message = s;
    }

    public void setProgress(int v){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while(progress<v){
                        progress += 1;
                        Thread.sleep(20);
                    }
                }catch (Exception e){

                }
            }
        });
        thread.start();
    }

    public boolean isOk(){
        return ok;
    }
}
