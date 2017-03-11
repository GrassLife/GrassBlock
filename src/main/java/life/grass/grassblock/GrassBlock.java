package life.grass.grassblock;

import com.google.gson.Gson;
import life.grass.grassDBAccess.GrassDBAccess;
import life.grass.grassblock.block.BlockInfo;
import life.grass.grassblock.block.BlockManager;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;
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
    public GrassDBAccess grassDBAccess = GrassDBAccess.instance;


    public GrassBlock getInstance(){
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        logger = getLogger();
        instance = this;
        worldNameList = this.getServer().getWorlds();
        blockManager = new BlockManager();
        worldNameList.forEach(s -> {
            Gson gson = new Gson();
            String json = GrassDBAccess.instance.selectString("block", "", "indexlist", "index_int=\'" + s + "\'", "json_str");
            List<Integer> l = gson.fromJson(json, List.class);
            if(l.size() < 10000)    l.forEach(i -> blockManager.registerBlockInfo(i, s).setJson(GrassDBAccess.instance.selectString("block", "", s.toString(), "index_int=" + i, "json_str")));
            else    l.parallelStream().forEach(i -> blockManager.registerBlockInfo(i, s).setJson(GrassDBAccess.instance.selectString("block", "", s.toString(), "index=_int" + i, "json_str")));
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Gson gson = new Gson();
        worldNameList.forEach(s -> {
            Map<Integer, BlockInfo> m = blockManager.getBlockMap().get(s);
            if(m==null)    return;
            GrassDBAccess.instance.updateString("block", "indexlist", s.toString(), "json_str" , gson.toJson(m.keySet()), "world_name=\'" + s + "\'");
            m.forEach((i, b) -> GrassDBAccess.instance.updateString("block", s.toString(), i.toString(), "json_str", gson.toJson(b.getJson()), "index=_int" + i));
        });
    }

    public static int transIndex(int x, int y, int z){
        return (x-x_min) + (z-z_min)*(x_max-x_min) + (y-y_min)*(x_max-x_min)*(z_max-z_min);
    }

    public static BlockManager getBlockManager(){
        return blockManager;
    }
}
