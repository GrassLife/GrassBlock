package life.grass.grassblock.block;

import javafx.collections.transformation.SortedList;
import life.grass.grassblock.GrassBlock;
import org.bukkit.block.Block;

import java.util.*;

/**
 * Created by gurapomu on 2017/02/20.
 */
public class BlockManager {
    private Map<String, List<BlockInfo>> blockListMap;
    private Map<String, List<Integer>> indexListMap;

    public BlockManager(){
        blockListMap = new HashMap<>();
        indexListMap = new HashMap<>();
    }

    public Map<String, List<BlockInfo>> getBlockListMap(){
        return blockListMap;
    }

    public Map<String, List<Integer>> getIndexListMap(){
        return indexListMap;
    }

    public BlockInfo registerBlockInfo(Block block){
        return registerBlockInfo(block.getX(), block.getY(), block.getZ(), block.getWorld().toString());
    }

    public BlockInfo registerBlockInfo(int x, int y, int z, String worldName){
        return registerBlockInfo(GrassBlock.transIndex(x, y, z), worldName);
    }

    public BlockInfo registerBlockInfo(int index, String worldName){
        if(blockListMap.get(worldName) == null){
            List<BlockInfo> blockList = new ArrayList<>();
            List<Integer> indexList = new ArrayList<>();
            blockList.add(new BlockInfo(index, worldName));
            indexList.add(index);
            blockListMap.put(worldName, blockList);
            indexListMap.put(worldName, indexList);
        }
        else {
            blockListMap.get(worldName).add(index, new BlockInfo(index, worldName));
            indexListMap.get(worldName).add(index);
        }
        return blockListMap.get(worldName).get(index);
    }

    public void unregisterBlockInfo(Block block){
        unregisterBlockInfo(block.getX(), block.getY(), block.getZ(), block.getWorld().toString());
    }

    public void unregisterBlockInfo(int x, int y, int z, String worldName){
        unregisterBlockInfo(GrassBlock.transIndex(x, y, z), worldName);
    }

    public void unregisterBlockInfo(int index, String worldName){
        if(blockListMap.get(worldName)==null)   return;
        else{
            blockListMap.get(worldName).remove(index);
            indexListMap.get(worldName).remove((Integer)index);
        }
    }
}
