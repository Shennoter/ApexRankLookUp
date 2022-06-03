<div align="center">
  
  
# ApexLookUp
![logo](https://github.com/Shennoter/ApexRankLookUp/blob/main/logo200.png)  
![satrs](https://img.shields.io/github/stars/Shennoter/ApexRankLookUp.svg?style=for-the-badge&color=yellow)
![downloads](https://shields.io/github/downloads/Shennoter/ApexRankLookUp/total.svg?style=for-the-badge)
![license](https://shields.io/github/license/Shennoter/ApexRankLookUp.svg?style=for-the-badge)
![version](https://shields.io/github/v/release/Shennoter/ApexRankLookUp?display_name=tag&style=for-the-badge&color=ff69b4)
  
</div>


> 一个基于[mirai-console](https://github.com/mamoe/mirai)的插件，可用于查询Apex英雄的
> - 地图轮换
> - 玩家信息
> - 复制器轮换
> - 猎杀门槛
> - 官方活动新闻  
>    
> 以及
> - 订阅玩家分数
> - 订阅地图轮换
## 食用前准备
- 安装[chat-command插件](https://github.com/project-mirai/chat-command) 
- 在[https://apexlegendsapi.com/](https://apexlegendsapi.com/)获取APIkey填入`config/pers.shennoter.ranklookup/config.yml`
- 安装字体“**微软雅黑**”，否则可能发生错误，也可在config.yml更改为自己想要的字体
## 指令
- 本插件权限ID为 `pers.shennoter.ranklookup:*`     
- 详见用户手册[PermissionCommand](https://github.com/mamoe/mirai/blob/dev/mirai-console/docs/BuiltInCommands.md#permissioncommand)  
- 如果输了命令没反应请在控制台输入`/permission add <想要给予权限的对象> pers.shennoter.ranklookup:<想要开启的指令>`  
- 如给予所有对象关于此插件的所有权限，请输入`/permission add * pers.shennoter.ranklookup:*`  
- **订阅功能仅对QQ群有效，对私聊无效**  

|指令 |功能|
|:---|:---|
|`/<apexhelp>`|帮助|
|`/<apexmap>`|查询当前地图轮换情况|
|`/<apexid> [Origin ID]`|查询某玩家（使用Origin ID）（空格需用@@填充）|  
|`/<apexcraft>` | 查询当前复制器轮换情况|
|`/<apexpred>` | 查询当前猎杀门槛|
|`/<apexnews> [index]` | 查询官方活动新闻（游戏大厅显示的那些），index为文章序号|
|`/<apexldb>`|查询排行榜（发一个排行榜链接）|
|`</apexadd> <id> [Origin ID] `|在该群订阅某玩家分数（当有更新时自动提醒）|
|`</apexremove> <id> [Origin ID]`|移除该群对某玩家分数的订阅|
|`</apexadd> <info>`|查看该群已订阅的ID|
|`</apexadd> <map>`|在该群订阅地图轮换（当有轮换时自动提醒）|
|`</apexremove> <map>`|移除该群对地图轮换的订阅|
|`/<apexcache>` | 立即清除产生的缓存文件，解决可能出现的缓存问题|
## 配置文件
|配置名称 |功能|缺省值|
|:---|:---|:---|
|`apiKey`|apiKey：如果没有请到 https://apexlegendsapi.com/ 获取|空|
|`extendApiKey`|额外apiKey，用于防止api过热，可不填|空|
|`mode`|回复方式： `pic`为图片，`text`为文字（制造器和新闻除外）|pic|  
|`picType` | 图片质量：`PNG`原图，更清晰；`JPG`更小，发送更快|JPG|
|`cacheExpireTime` | 缓存图片过期时间（单位：天）|30|
|`cacheAutoDel` | 是否自动清除过期缓存：`true`为是，`false`为否（开启mcl时自动清理）|true|
|`listener`|玩家分数监听：true为启用，false为关闭|false|
|`listenerInfoType`|玩家监听提醒方式：true为分数变化+玩家信息，false为仅提醒分数变化|false|
|`listenInterval`|监听时间间隔（单位：分钟）|10|
|`mapRotationReminder`|地图轮换提醒：true为启用，false为关闭|false|
|`font`|字体|微软雅黑|
|`ifProxy`|是否使用代理：true为启用，false为关闭|false|
|`proxyType`|代理类型：HTTP、SOCKS|HTTP|
|`hostName`|代理地址|127.0.0.1|
|`port`|代理端口|7890|
## 示例  
- 查询玩家  
  ![player](https://github.com/Shennoter/ApexRankLookUp/blob/main/player.png)
- 查询地图轮换  
  ![map](https://github.com/Shennoter/ApexRankLookUp/blob/main/map.png)
- 查询复制器轮换  
  ![craft](https://github.com/Shennoter/ApexRankLookUp/blob/main/craft.png)
- 查询猎杀门槛  
  ![predator](https://github.com/Shennoter/ApexRankLookUp/blob/main/predatoreg.png)
- 查询官方活动新闻  
  ![news](https://github.com/Shennoter/ApexRankLookUp/blob/main/news.png)
- 订阅玩家分数  
  ![playerListener](https://github.com/Shennoter/ApexRankLookUp/blob/main/playerListener.png)
- 订阅地图轮换  
  ![mapReminder](https://github.com/Shennoter/ApexRankLookUp/blob/main/mapReminder.png)
