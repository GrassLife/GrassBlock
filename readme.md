# GrassBlock

## 使い方
### 新しくBlockInfoを登録
```
GrassBlock.getInstance().getBlockManager().registerBlockInfo(x, y, z, worldname).setJson(jsonString);
```
### BlockInfoを削除
```
GrassBlock.getInstance().getBlockManager().unregisterBlockInfo(x, y, z, worldname);
```