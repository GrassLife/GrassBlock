# GrassBlock

## 使い方
### 新しくBlockInfoを登録
```
GrassBlock.getInstance().getBlockManager().registerBlockInfo(block).setJson(jsonString);
```
### 格納できるJSONの最大文字数は21842までです。

### BlockInfoを削除
```
GrassBlock.getInstance().getBlockManager().unregisterBlockInfo(block);
```