import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by Rober on 2017/6/1.
 */
public class Loading {
    PApplet par;
    PImage img;
    String message;
    int progress = 0;
    boolean ok = false;

    public Loading (PApplet par){
        this.par = par;
    }

    public void display(){
        par.textSize(40);
        par.background(0);
        par.text("LOADING.....(" + progress + " %)", 50, 50);
        par.text(message,  50, 100);

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
                    while(progress<=v){
                        progress += 1;
                        Thread.sleep(100);
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
