package Battle;

import processing.core.PApplet;
import processing.core.PVector;
/**
 * Created by USER on 2017/5/23.
 */
public class Shelf {
    private PApplet parent;
    private DefensiveItem item;
    private int shelfWidth;
    private int shelfHeight;
    private int itemWidth;
    private int itemHeight;
    private PVector position;// upper left position

    public Shelf(PApplet parent, DefensiveItem item, int shelfW, int shelfH, float posX, float posY) {
           this.parent = parent;
           position = new PVector(posX, posY);
           this.shelfWidth = shelfW;
           this.shelfHeight = shelfH;
           this.item = item;
           itemWidth = shelfH;
           itemHeight = shelfH;
    }

    private void showBar(float posX, float posY, float barWidth, float barHeight) {
            parent.fill(250, 250, 250, 180);
            parent.rect(posX, posY, barWidth, barHeight);
    }

    public boolean checkMouseOnShelf() {
            if(parent.mouseX < position.x || parent.mouseX > position.x + shelfWidth) return false;
            else if(parent.mouseY < position.y || parent.mouseY > position.y + shelfHeight) return false;
            else return true;
    }

    public void showValueWindos() {
           parent.fill(0, 51, 204, 150);//windows rgb
//        parent.fill(1, 80, 136, 150);//windows rgb
           float mX = parent.mouseX;
           float mY = parent.mouseY;

           if(mY + BattleSetting.windowHeight > BattleSetting.backgroundHeight) mY = mY - BattleSetting.windowHeight;
           parent.rect(mX, mY, BattleSetting.windowWidth, BattleSetting.windowHeight, BattleSetting.backgroundHeight/50);
           parent.fill(255, 255, 255);
        parent.stroke(255);
           parent.line(mX, mY + BattleSetting.windowHeight/3, mX + BattleSetting.windowWidth, mY + BattleSetting.windowHeight/3);
        parent.noStroke();
           parent.textSize(40);

           parent.textAlign(parent.CENTER);
           parent.text(item.getName(), mX + BattleSetting.windowWidth/2, mY + BattleSetting.windowHeight/6+10);

           parent.textAlign(parent.LEFT, parent.BOTTOM);
           parent.textSize(35);
           parent.text("LUK: +" + item.getLuckyValue(), mX, mY + BattleSetting.windowHeight*2/3);
           parent.text("DEF: +" + item.getDefValue(), mX, mY + BattleSetting.windowHeight);
    }

    public void display() {
           float barWidth = shelfWidth - itemWidth;
           showBar(position.x + itemWidth,                 position.y, shelfWidth - itemWidth, shelfHeight / 2);
           showBar(position.x + itemWidth, shelfHeight/2 + position.y, shelfWidth - itemWidth, shelfHeight / 2);
           item.display(position.x, position.y, barWidth, itemHeight, 0);
    }

    public boolean isValidBuy(int[] bagmine) {
           int []check = item.getRequire();
           for (int i = 0; i < 6; i++) {
               if (check[i] > bagmine[i+1]) return false;
           }
           return true;
    }

    public DefensiveItem getItem() {
           return item;
    }
}
