# ApexLookUp
![satrs](https://img.shields.io/github/stars/Shennoter/ApexRankLookUp.svg?style=for-the-badge&color=yellow)
![downloads](https://shields.io/github/downloads/Shennoter/ApexRankLookUp/total.svg?style=for-the-badge)
![license](https://shields.io/github/license/Shennoter/ApexRankLookUp.svg?style=for-the-badge)
![version](https://shields.io/github/v/release/Shennoter/ApexRankLookUp?display_name=tag&style=for-the-badge&color=ff69b4)
- 一个基于[mirai-console](https://github.com/mamoe/mirai)QQ机器人的插件，用于查询apex英雄当前地图轮换、玩家信息和复制器轮换
- 使用前请到[https://apexlegendsapi.com/](https://apexlegendsapi.com/)获取ApiKey填入config/pers.shennoter.ranklookup/config.yml
- 由于api限制，短时间内请求次数过多可能查询失败，请稍等后重试
## 指令
使用前请确认已经安装[chat-command插件](https://github.com/project-mirai/chat-command)  
本插件权限ID为 `pers.shennoter.ranklookup:*`     
详见用户手册[PermissionCommand](https://github.com/mamoe/mirai/blob/dev/mirai-console/docs/BuiltInCommands.md#permissioncommand)  
如果输了命令没反应请在控制台输入`/permission add <想要给予权限的对象> pers.shennoter.ranklookup:<想要开启的指令>`  
如给予所有对象关于此插件的所有权限，请输入`/permission add * pers.shennoter.ranklookup:*`
|指令 |功能|
|:---|:---|
|`/<apexmap 地图查询>`|查询当前地图轮换情况|
|`/<apexid 玩家查询> [Origin ID]`|查询某玩家（使用Origin ID）（空格需用@@填充）|  
|`/<apexcraft 复制器查询>` | 查询当前复制器轮换情况|
## 配置文件
|配置名称 |功能|
|:---|:---|
|`ApiKey`|ApiKey：如果没有请到 https://apexlegendsapi.com/ 获取|
|`mode`|机器人回复方式： `pic`为图片，`text`为文字(只适用于玩家信息和地图轮换)|  
|`picType` | 图片质量：`PNG`原图，更清晰；`JPG`更小，发送更快|
## 示例  
- 查询玩家  
  ![player](https://github.com/Shennoter/ApexRankLookUp/blob/main/player.png)
- 查询地图轮换  
  ![map](https://github.com/Shennoter/ApexRankLookUp/blob/main/map.png)
- 查询复制器轮换  
  ![craft](https://github.com/Shennoter/ApexRankLookUp/blob/main/craft.png)
