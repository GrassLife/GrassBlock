package life.grass.grassblock.block;

import javafx.collections.transformation.SortedList;
import life.grass.grassblock.GrassBlock;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.*;

/**
 * Created by gurapomu on 2017/02/20.
 */
public class BlockManager {
    private Map<World, Map<Integer, BlockInfo>> blockMap;

    public BlockManager(){
        blockMap = new HashMap<>();
    }

    public Map<World, Map<Integer, BlockInfo>> getBlockMap(){
        return blockMap;
    }

    public BlockInfo registerBlockInfo(Block block){
        return registerBlockInfo(block.getX(), block.getY(), block.getZ(), block.getWorld());
    }

    public BlockInfo registerBlockInfo(int x, int y, int z, World world){
        return registerBlockInfo(GrassBlock.transIndex(x, y, z), world);
    }

    public BlockInfo registerBlockInfo(int index, World world){
        if(blockMap.get(world) == null){
            Map<Integer, BlockInfo> indexMap = new HashMap<>();
            indexMap.put(index, new BlockInfo(index, world));
            blockMap.put(world, indexMap);
        }
        else {
            blockMap.get(world).put(index, new BlockInfo(index, world));
        }
        return blockMap.get(world).get(index);
    }

    public void unregisterBlockInfo(Block block){
        unregisterBlockInfo(block.getX(), block.getY(), block.getZ(), block.getWorld());
    }

    public void unregisterBlockInfo(int x, int y, int z, World world){
        unregisterBlockInfo(GrassBlock.transIndex(x, y, z), world);
    }

    public void unregisterBlockInfo(int index, World world){
        if(blockMap.get(world)!=null)   blockMap.get(world).remove(index);
    }
}
