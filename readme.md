# GrassBlock

## 使い方
### 新しくBlockInfoを登録
```
GrassBlock.getInstance().getBlockManager().registerBlockInfo(block).setJson(jsonString);
```
### BlockInfoを削除
```
GrassBlock.getInstance().getBlockManager().unregisterBlockInfo(block);
```