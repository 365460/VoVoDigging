package Battle;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
/**
 * Created by USER on 2017/5/18.
 */
public class DefensiveItem {
    public static PImage[] mine;
    private int luckyValue;
    private int defValue;
    private int defID;
    private PApplet parent;
    private PImage img;
    private String name;
    private int [] require;

    public DefensiveItem(PApplet parent, int luckyValue, int defValue, int id, int[] req, String name){
        this.name = name;
        this.parent = parent;
        this.defValue = defValue;
        this.luckyValue = luckyValue;
        this.defID = id;
        require = new int[6];
        img = parent.loadImage("img/img0"+ defID + ".png");
        for(int i = 0; i < 6; i++)
            require[i] = req[i];
    }
    public int []getRequire() {
        return require;
    }

    public String getName() {
           return name;
    }

    public int getLuckyValue() {
        return luckyValue;
    }
    public int getDefValue() {
        return defValue;
    }

    public void display (float posX, float posY, float barW, float itemH, int tmp) {
        //item background
        parent.fill(66, 134, 234, 134);
        parent.rect(posX, posY, itemH, itemH);
        parent.image(img, posX, posY, itemH, itemH);
        parent.fill(0, 0, 0);
        showName(posX + itemH, posY + itemH / 2, itemH/2);
        showRequirement(posX + itemH, posY + itemH / 2, barW, itemH / 2);
    }

    public void showRequirement (float posX, float posY, float barW, float barH) {
        float boxW = barW / 6;
        float curX = posX;
        float y_interval = barH / 10;
        parent.textSize(barH - y_interval*2);
        for(int i = 0; i < 6; i++){
            if (require[i] > 0) {
                parent.image(DefensiveItem.mine[i], curX , posY + y_interval, boxW/2, y_interval*8);
                curX += boxW/2;
                parent.textAlign(parent.LEFT, parent.BOTTOM);
                parent.text(require[i], curX, posY + y_interval * 9);
                curX += boxW/2;
            }
        }
    }

    public void showName(float x, float y, float barH) {
        float y_interval = barH / 10;
        float textSz = y_interval*8;
        parent.textSize(textSz);
        parent.textAlign(parent.LEFT, parent.BOTTOM);
        parent.text(name, x + 10, y - (barH - textSz)/2);//抬升字的高度
    }

}
