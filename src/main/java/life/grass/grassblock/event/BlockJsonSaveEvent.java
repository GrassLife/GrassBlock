package life.grass.grassblock.event;

import life.grass.grassDBAccess.GrassDBAccess;
import life.grass.grassblock.GrassBlock;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by gurapomu on 2017/03/12.
 */
public class BlockJsonSaveEvent implements Listener {
    @EventHandler
    public void onPlayerInteracting(PlayerInteractEvent event){
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
            Block block = event.getClickedBlock();
            GrassBlock.getBlockManager().registerBlockInfo(block).setJson("{\"test\":\"a\"}");
            System.out.println("registered index=" + GrassBlock.transIndex(block.getX(), block.getY(), block.getZ()));
        }
    }
}
