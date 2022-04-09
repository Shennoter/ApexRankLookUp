# ApexLookUp

- 一个基于[mirai-console](https://github.com/mamoe/mirai)QQ机器人的插件，用于查询apex英雄当前地图轮换、玩家信息和复制器轮换
- 使用前请到[https://apexlegendsapi.com/](https://apexlegendsapi.com/)获取ApiKey填入config/pers.shennoter.ranklookup/config.yml
- 由于api限制，短时间内请求次数过多可能查询失败，请稍等后重试
## 指令
使用前请确认已经安装[chat-command插件](https://github.com/project-mirai/chat-command)  
本插件权限ID为 `pers.shennoter.ranklookup:*`     
|指令 |功能|
|:---|:---|
|`/<apexmap 地图查询>`|查询当前地图轮换情况|
|`/<apexid 玩家查询> [Origin ID]`|查询某玩家（使用Origin ID）（空格需用@@填充）|  
|`/<apexcraft 复制器查询>` | 查询当前复制器轮换情况|
## 示例  
- 查询玩家  
  ![player](https://github.com/Shennoter/ApexRankLookUp/blob/main/player.png)
- 查询地图轮换  
  ![map](https://github.com/Shennoter/ApexRankLookUp/blob/main/map.png)
- 查询复制器轮换  
  ![craft](https://github.com/Shennoter/ApexRankLookUp/blob/main/craft.png)
