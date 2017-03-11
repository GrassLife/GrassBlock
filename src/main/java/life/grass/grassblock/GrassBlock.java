package life.grass.grassblock;

import com.google.gson.Gson;
import life.grass.grassDBAccess.GrassDBAccess;
import life.grass.grassblock.block.BlockInfo;
import life.grass.grassblock.block.BlockManager;
import life.grass.grassblock.event.BlockJsonSaveEvent;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.logging.Logger;

public final class GrassBlock extends JavaPlugin {

    public static Logger logger;

    public static final int x_min = -1000;
    public static final int x_max = 999;
    public static final int y_min = 0;
    public static final int y_max = 255;
    public static final int z_min = -1000;
    public static final int z_max = 999;

    private static GrassBlock instance;
    private List<World> worldNameList;
    private static BlockManager blockManager;
    public static GrassDBAccess grassDBAccess;

    public GrassBlock getInstance(){
        return instance;
    }

    @Override
    public void onEnable() {
        grassDBAccess = GrassDBAccess.getInstance();
        // Plugin startup logic
        logger = getLogger();
        instance = this;
        worldNameList = this.getServer().getWorlds();
        getServer().getPluginManager().registerEvents(new BlockJsonSaveEvent(), this);
        blockManager = new BlockManager();
        worldNameList.forEach(s -> {
            Gson gson = new Gson();
            String json = GrassDBAccess.getInstance().selectString("block", null, "indexlist", "index_str=\'" + s.getName() + "\'", "json_str");
//            System.out.println(json);
            Set<Double> set = gson.fromJson(json, Set.class);
            set.forEach(i -> {
                String str = grassDBAccess.selectString("block", null, s.getName(), "index_int=" + i.intValue(), "json_str");
                blockManager.registerBlockInfo(i.intValue(), s).setJson(str);
            });
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Gson gson = new Gson();
        worldNameList.forEach(s -> {
            Map<Integer, BlockInfo> m = blockManager.getBlockMap().get(s);
            if(m==null)    return;
            grassDBAccess.updateString("block", "indexlist", s.getName(), "json_str" , gson.toJson(m.keySet()), "index_str=\'" + s.getName() + "\'");
            m.forEach((i, b) -> {
                System.out.println(b.getJson());
                grassDBAccess.updateString("block", s.getName(), i, "json_str", b.getJson(), "index_int=" + i);
            });
        });
    }

    public static int transIndex(int x, int y, int z){
        return (x-x_min) + (z-z_min)*(x_max-x_min) + (y-y_min)*(x_max-x_min)*(z_max-z_min);
    }

    public static BlockManager getBlockManager(){
        return blockManager;
    }
}
