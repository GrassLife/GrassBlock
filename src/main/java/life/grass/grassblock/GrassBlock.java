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
        worldNameList.forEach(s -> {
            Gson gson = new Gson();
            String json = GrassDBAccess.instance.selectString("block", "", "indexlist", "index=\'" + s + "\'", "json");
            List<Integer> l = gson.fromJson(json, List.class);
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
        Gson gson = new Gson();
        worldNameList.forEach(s -> {
            List<Integer> l1 = blockManager.getIndexListMap().get(s);
            List<BlockInfo> l2 = blockManager.getBlockListMap().get(s);
            if(l1==null)    return;
            GrassDBAccess.instance.updateString("block", "indexlist", s, gson.toJson(l1), "index=\'" + s + "\'");
            l2.forEach(b -> {
                GrassDBAccess.instance.updateString("block", s, b.getIndex(), gson.toJson(b.getJson()), "index=" + b.getIndex());
            });
        });
    }

    public static int transIndex(int x, int y, int z){
        return (x-x_min) + (z-z_min)*(x_max-x_min) + (y-y_min)*(x_max-x_min)*(z_max-z_min);
    }
}
