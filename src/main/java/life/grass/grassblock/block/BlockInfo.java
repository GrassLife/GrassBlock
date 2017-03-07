package life.grass.grassblock.block;

import life.grass.grassblock.GrassBlock;

/**
 * Created by gurapomu on 2017/02/20.
 */
public class BlockInfo {

    private int index;
    private String worldName;
    private String json = "{}";

    public BlockInfo(int x, int y, int z, String worldName){
        this.index = GrassBlock.transIndex(x, y, z);
        this.worldName = worldName;
    }

    public BlockInfo(int index, String worldName){
        this.index = index;
        this.worldName = worldName;
    }

    public int getIndex(){
        return index;
    }

    public String getJson(){
        return json;
    }
    public void setJson(String json){
        this.json = json;
    }

    public int getX(){
        return index%(GrassBlock.x_max-GrassBlock.x_min) + GrassBlock.x_min;
    }

    public int getY(){
        return index/((GrassBlock.x_max-GrassBlock.x_min)*(GrassBlock.z_max-GrassBlock.z_min)) + GrassBlock.y_min;
    }

    public int getZ(){
        return (index%((GrassBlock.x_max-GrassBlock.x_min)*(GrassBlock.z_max-GrassBlock.z_min)))/(GrassBlock.x_max-GrassBlock.x_min) + GrassBlock.z_min;
    }
}
