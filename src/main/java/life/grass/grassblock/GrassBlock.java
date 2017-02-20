package life.grass.grassblock;

import com.google.gson.Gson;
import life.grass.grassDBAccess.GrassDBAccess;
import life.grass.grassblock.block.BlockInfo;
import life.grass.grassblock.block.BlockManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        String json = GrassDBAccess.instance.selectString("block", "", "indexlist", "id=1", "json");
        Gson gson = new Gson();
        Map<String, List<Integer>> stringIntegerMap = gson.fromJson(json, Map.class);
        stringIntegerMap.forEach((s, l) -> {
            if(l.size() < 10000) {
                l.forEach(i -> {
                    blockManager.registerBlockInfo(i, s).setJson(GrassDBAccess.instance.selectString("block", "", s, "index=" + i, "json"));
                });
            }
            else {
                l.parallelStream().forEach(i -> {
                    blockManager.registerBlockInfo(i, s).setJson(GrassDBAccess.instance.selectString("block", "", s, "index=" + i, "json"));
                });
            }
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static int transIndex(int x, int y, int z){
        return (x-x_min) + (z-z_min)*(x_max-x_min) + (y-y_min)*(x_max-x_min)*(z_max-z_min);
    }
}
