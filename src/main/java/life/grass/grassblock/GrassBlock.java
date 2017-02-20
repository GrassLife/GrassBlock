package life.grass.grassblock;

import life.grass.grassblock.block.BlockManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.IntStream;

public final class GrassBlock extends JavaPlugin {

    public static final int x_min = -1000;
    public static final int x_max = 999;
    public static final int y_min = 0;
    public static final int y_max = 255;
    public static final int z_min = -1000;
    public static final int z_max = 999;

    private static GrassBlock instance;
    public BlockManager blockManager;

    public GrassBlock getInstance(){
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        blockManager = new BlockManager();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static int transIndex(int x, int y, int z){
        return (x-x_min) + (z-z_min)*(x_max-x_min) + (y-y_min)*(x_max-x_min)*(z_max-z_min);
    }
}
