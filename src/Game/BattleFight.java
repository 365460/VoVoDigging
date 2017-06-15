package Game;

import processing.core.PApplet;
import sun.security.krb5.internal.PAData;

/**
 * Created by Rober on 2017/6/15.
 */
public class BattleFight {
    PApplet par;
    int atk, def, luck;
    public BattleFight(PApplet par,int atk, int def,int luck){
        this.atk = atk;
        this.def = def;
        this.luck = luck;
    }

}
