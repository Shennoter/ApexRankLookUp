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
## 食用前准备
- 安装[chat-command插件](https://github.com/project-mirai/chat-command) 
- 在[https://apexlegendsapi.com/](https://apexlegendsapi.com/)获取ApiKey填入`config/pers.shennoter.ranklookup/config.yml`
- 安装字体“**微软雅黑**”，否则可能发生错误。不装也可以，但如果报错就得装了
## 指令
本插件权限ID为 `pers.shennoter.ranklookup:*`     
详见用户手册[PermissionCommand](https://github.com/mamoe/mirai/blob/dev/mirai-console/docs/BuiltInCommands.md#permissioncommand)  
如果输了命令没反应请在控制台输入`/permission add <想要给予权限的对象> pers.shennoter.ranklookup:<想要开启的指令>`  
如给予所有对象关于此插件的所有权限，请输入`/permission add * pers.shennoter.ranklookup:*`
|指令 |功能|
|:---|:---|
|`/<apexmap 地图查询>`|查询当前地图轮换情况|
|`/<apexid 玩家查询> [Origin ID]`|查询某玩家（使用Origin ID）（空格需用@@填充）|  
|`/<apexcraft 复制器查询>` | 查询当前复制器轮换情况|
|`/<apexpred 猎杀查询>` | 查询当前猎杀门槛|
|`/<apexnews 新闻查询> [index]` | 查询官方活动新闻（游戏大厅显示的那些），index为文章序号|
|`/<apexcache 清除缓存>` | 立即清除产生的缓存文件，解决可能出现的缓存问题|
## 配置文件
|配置名称 |功能|
|:---|:---|
|`ApiKey`|ApiKey：如果没有请到 https://apexlegendsapi.com/ 获取|
|`mode`|回复方式： `pic`为图片，`text`为文字(玩家信息、地图轮换和猎杀门槛)|  
|`picType` | 图片质量：`PNG`原图，更清晰；`JPG`更小，发送更快|
|`cacheExpireTime` | 缓存图片过期时间：默认为`30`（单位：天）|
|`cacheAutoDel` | 是否自动清除过期缓存：`true`为是，`false`为否（开启mcl时自动清理）|
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

 ## Todo
 - [ ] 分数监听
 - [ ] 轮换提醒
 - [ ] HTTP代理
