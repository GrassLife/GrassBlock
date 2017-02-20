package life.grass.grassblock;

import life.grass.grassDBAccess.GrassDBAccess;
import life.grass.grassblock.block.BlockInfo;
import life.grass.grassblock.block.BlockManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public final class GrassBlock extends JavaPlugin {

    public static Logger logger;

    public static final int x_min = -1000;
    public static final int x_max = 999;
    public static final int y_min = 0;
    public static final int y_max = 255;
    public static final int z_min = -1000;
    public static final int z_max = 999;

    private static GrassBlock instance;
    private List<String> worldNameList;
    public BlockManager blockManager;
    public GrassDBAccess grassDBAccess = GrassDBAccess.instance;


    public GrassBlock getInstance(){
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        logger = getLogger();
        instance = this;
        worldNameList = new ArrayList<>();
        worldNameList.add("overworld");
        blockManager = new BlockManager();
        int ret = grassDBAccess.selectInt("block", null, "info", "index=1", "json");
        if(ret == -1){
            IntStream.range(0, (x_max-x_min)*(y_max-y_min)*(z_max-z_min)).parallel().forEach(i -> {
                worldNameList.forEach(worldName -> {
                    blockManager.getBlockList().add(i, new BlockInfo(i, worldName));
                    grassDBAccess.insertInitialInt("block", "info", i);
                });
            });
        }
        else if(ret == -2){
            logger.warning("DB読み込みで異常が発生しました");
        }
        else {
            IntStream.range(0, (x_max-x_min)*(y_max-y_min)*(z_max-z_min)).parallel().forEach(i -> {
                worldNameList.forEach(worldName -> {
                    blockManager.getBlockList().add(i, new BlockInfo(i, worldName));
                    blockManager.getBlockList().get(i).setJson(grassDBAccess.selectString("block", null, "info", "index=" + i, "json"));
                });
            });
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static int transIndex(int x, int y, int z){
        return (x-x_min) + (z-z_min)*(x_max-x_min) + (y-y_min)*(x_max-x_min)*(z_max-z_min);
    }
}
