package Map;
import Setting.*;
import processing.core.PApplet;

/**
 * Created by Rober on 2017/5/23.
 */
public class BlockFactory {
    public static Block generate(PApplet par,int  id){
        switch (id){
            case Setting.SoilId:
                return new BlockSoil(par);
            case Setting.IronId:
                return new BlockRock(par);
            case Setting.GoldId:
                return new BlockGold(par);
            default:
                return new BlockWall(par);
        }
    }
}