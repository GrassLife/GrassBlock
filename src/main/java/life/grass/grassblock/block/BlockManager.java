package life.grass.grassblock.block;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gurapomu on 2017/02/20.
 */
public class BlockManager {
    private List<BlockInfo> blockList;

    public BlockManager(){
        blockList = new ArrayList<>();
    }

    public List<BlockInfo> getBlockList(){
        return blockList;
    }
}
