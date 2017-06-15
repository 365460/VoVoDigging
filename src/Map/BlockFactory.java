package Map;
import Setting.*;
import processing.core.PApplet;

/**
 * Created by Rober on 2017/5/23.
 */
public class BlockFactory {
    public static Block generate(PApplet par,int  id){
        if(id==0) {
            Block b = new BlockSoil(par);
            b.status = BlockStatus.EMPTY;
            return b;
        }
        switch (id){
            case Setting.SoilId:
                return new BlockSoil(par);
            case Setting.IronId:
                return new BlockRock(par);
            case Setting.GoldId:
                return new BlockGold(par);
            case Setting.WoodId:
                return new BlockWood(par);
            case Setting.CoalId:
                return new BlockCoal(par);
            case Setting.DiamondId:
                return new BlockDiamond(par);
            default:
                return new BlockWall(par);
        }
    }

    public static Block generateWithStatus(PApplet par,int id , int v){

        BlockStatus status = BlockStatus.toStatus(v);

        Block block = generate(par,id);
        block.status = status;

        return block;
    }

    public static Block generateWithStatus(PApplet par,int id){
        BlockStatus status = BlockStatus.toStatus(id/10);
        Block block = generate(par,id%10);
        block.status = status;
        if(status==BlockStatus.GATE) par.println("GEt gate");
        return block;
    }
}
