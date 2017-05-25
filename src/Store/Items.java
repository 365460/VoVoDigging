package Store;

import Store.Button;
import processing.core.PVector;
import processing.core.*;
import java.util.ArrayList;
/**
 * Created by chichen on 2017/5/18.
 */
public class Items {

    PApplet parent;
    PVector position;
    float width , height;
    public PImage imgPButton[] = new PImage[2];
    public PImage imgitem = new PImage();
    public ArrayList<Button> Button_arr = new ArrayList<>();
    int limit = 0,buy_w=0;

    public Items( PApplet parent, float x, float y, float width , float height, float radius, int buy_w, int limit) {
        this.parent = parent;
        position = new PVector(x, y);
        this.width  = width ;
        this.height = height;
        this.limit = limit;
        this.buy_w=buy_w;
        Button b0 = new Button(parent, x - width  / 4, y + height / 4 + radius + 5, radius);
        Button b1 = new Button(parent, x + width / 4, y + height / 4 + radius + 5, radius);
        Button_arr.add(b0);
        Button_arr.add(b1);
    }

    public void display() {

        //parent.fill(	205, 170, 125);
       // parent.stroke(255);
        parent.noFill();
        parent.rect(position.x - width / 2, position.y - height / 2, width , height);
        // parent.noStroke();
        parent.rect(position.x + width / 4, position.y - height / 2, 45, 45);
        parent.image(imgitem, position.x, position.y, width , height);
        parent.textSize(30);
        parent.fill(0);
        parent.text(buy_w, position.x + width  / 4+20, position.y - height / 4);
        for (int i = 0; i < 2; i++) {
            Button_arr.get(i).display();
        }
    }

    public boolean checkMouseClicked() {
        for (int i = 0; i < 2; i++) {
            if (Button_arr.get(i).checkMouseClicked())
            {  if (i == 0) {
//                    if(buy_w<limit)

                     buy_w ++;
                } else {
                    if (buy_w != 0) {
                      buy_w--;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public int getBuy_w() {
        return buy_w;
    }
}
