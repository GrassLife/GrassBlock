package life.grass.grassblock.timer;

import com.google.gson.Gson;
//import life.grass.grassDBAccess.GrassDBAccess;
import life.grass.grassblock.GrassBlock;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by gurapomu on 2017/03/14.
 */
public class BlockDataSaveTimer implements Runnable {
    JavaPlugin plugin;
    public BlockDataSaveTimer(JavaPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public void run(){
        GrassBlock.getBlockManager().getBlockMap().forEach((w, m) -> {
//            Gson gson = new Gson();
//            GrassDBAccess.getInstance().updateString("block", "indexlist", w.getName(), "json_str" , gson.toJson(m.keySet()), "index_str=\'" + w.getName() + "\'");
//            m.forEach((i, b) -> {
//                GrassDBAccess.getInstance().updateString("block", w.getName(), i, "json_str", b.getJson(), "index_int=" + i);
//            });
        });
        plugin.getLogger().info("Finish block data save");
    }
}
