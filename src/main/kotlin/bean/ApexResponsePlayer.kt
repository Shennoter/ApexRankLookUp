data class ApexResponsePlayer(
    val ALS: ALS,
    val global: Global,
    val legends: Legends,
    val mozambiquehere_Stringernal: MozambiquehereStringernal,
    val realtime: Realtime,
    val total: Total
)

data class ALS(
    val isALSDataEnabled: Boolean
)

data class Global(
    val arena: Arena,
    val avatar: String,
    val badges: List<Badge>,
    val bans: Bans,
    val battlepass: Battlepass,
    val StringernalUpdateCount: String,
    val level: String,
    val name: String,
    val platform: String,
    val rank: Rank,
    val toNextLevelPercent: String,
    val uid: Long
)

data class Legends(
    val all: All,
    val selected: Selected
)

data class MozambiquehereStringernal(
    val APIAccessType: String,
    val ClusterID: String,
    val claimedBy: String,
    val clusterSrv: String,
    val isNewToDB: Boolean,
    val rate_limit: RateLimit
)

data class Realtime(
    val canJoin: Int,
    val currentState: String,
    val currentStateAsText: String,
    val currentStateSinceTimestamp: Int,
    val isInGame: Int,
    val isOnline: Int,
    val lobbyState: String,
    val partyFull: Int,
    val selectedLegend: String
)

data class Total(
    val damage: Damage,
    val kd: Kd,
    val kills: Kills,
    val kills_season_11: KillsSeason11,
    val kills_season_12: KillsSeason12,
    val shotgun_kills: ShotgunKills,
    val smoke_grenade_enemies_hit: SmokeGrenadeEnemiesHit,
    val specialEvent_damage: SpecialEventDamage,
    val specialEvent_wins: SpecialEventWins,
    val wins_season_11: WinsSeason11
)

data class Arena(
    val ladderPosPlatform: Int,
    val rankDiv: String,
    val rankImg: String,
    val rankName: String,
    val rankScore: String,
    val rankedSeason: String
)

data class Badge(
    val name: String,
    val value: String
)

data class Bans(
    val isActive: Boolean,
    val last_banReason: String,
    val remainingSeconds: String
)

data class Battlepass(
    val history: History,
    val level: String
)

data class Rank(
    val ladderPosPlatform: Int,
    val rankDiv: String,
    val rankImg: String,
    val rankName: String,
    val rankScore: String,
    val rankedSeason: String
)

data class History(
    val season1: String,
    val season10: String,
    val season11: String,
    val season12: String,
    val season2: String,
    val season3: String,
    val season4: String,
    val season5: String,
    val season6: String,
    val season7: String,
    val season8: String,
    val season9: String
)

data class All(
    val Ash: Ash,
    val Bangalore: Bangalore,
    val Bloodhound: Bloodhound,
    val Caustic: Caustic,
    val Crypto: Crypto,
    val Fuse: Fuse,
    val Gibraltar: Gibraltar,
    val Global: GlobalX,
    val Horizon: Horizon,
    val Lifeline: Lifeline,
    val Loba: Loba,
    val MadMaggie: MadMaggie,
    val Mirage: Mirage,
    val Octane: Octane,
    val Pathfinder: Pathfinder,
    val Rampart: Rampart,
    val Revenant: Revenant,
    val Seer: Seer,
    val Valkyrie: Valkyrie,
    val Wattson: Wattson,
    val Wraith: Wraith
)

data class Selected(
    val ImgAssets: ImgAssetsXXXXXXXXXXXXXXXXXXXXX,
    val LegendName: String,
    val `data`: List<DataXXXX>,
    val gameInfo: GameInfoXX
)

data class Ash(
    val ImgAssets: ImgAssets
)

data class Bangalore(
    val ImgAssets: ImgAssetsX,
    val `data`: List<Data>,
    val gameInfo: GameInfo
)

data class Bloodhound(
    val ImgAssets: ImgAssetsXX
)

data class Caustic(
    val ImgAssets: ImgAssetsXXX
)

data class Crypto(
    val ImgAssets: ImgAssetsXXXX,
    val `data`: List<DataX>,
    val gameInfo: GameInfoX
)

data class Fuse(
    val ImgAssets: ImgAssetsXXXXX
)

data class Gibraltar(
    val ImgAssets: ImgAssetsXXXXXX
)

data class GlobalX(
    val ImgAssets: ImgAssetsXXXXXXX
)

data class Horizon(
    val ImgAssets: ImgAssetsXXXXXXXX
)

data class Lifeline(
    val ImgAssets: ImgAssetsXXXXXXXXX
)

data class Loba(
    val ImgAssets: ImgAssetsXXXXXXXXXX
)

data class MadMaggie(
    val ImgAssets: ImgAssetsXXXXXXXXXXX
)

data class Mirage(
    val ImgAssets: ImgAssetsXXXXXXXXXXXX
)

data class Octane(
    val ImgAssets: ImgAssetsXXXXXXXXXXXXX
)

data class Pathfinder(
    val ImgAssets: ImgAssetsXXXXXXXXXXXXXX,
    val `data`: List<DataXX>
)

data class Rampart(
    val ImgAssets: ImgAssetsXXXXXXXXXXXXXXX
)

data class Revenant(
    val ImgAssets: ImgAssetsXXXXXXXXXXXXXXXX
)

data class Seer(
    val ImgAssets: ImgAssetsXXXXXXXXXXXXXXXXX
)

data class Valkyrie(
    val ImgAssets: ImgAssetsXXXXXXXXXXXXXXXXXX,
    val `data`: List<DataXXX>
)

data class Wattson(
    val ImgAssets: ImgAssetsXXXXXXXXXXXXXXXXXXX
)

data class Wraith(
    val ImgAssets: ImgAssetsXXXXXXXXXXXXXXXXXXXX
)

data class ImgAssets(
    val banner: String,
    val icon: String
)

data class ImgAssetsX(
    val banner: String,
    val icon: String
)

data class Data(
    val key: String,
    val name: String,
    val rank: RankX,
    val rankPlatformSpecific: RankPlatformSpecific,
    val value: String
)

data class GameInfo(
    val badges: List<BadgeX>
)

data class RankX(
    val rankPos: String,
    val topPercent: String
)

data class RankPlatformSpecific(
    val rankPos: String,
    val topPercent: String
)

data class BadgeX(
    val name: String,
    val value: String
)

data class ImgAssetsXX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXXX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXXXX(
    val banner: String,
    val icon: String
)

data class DataX(
    val key: String,
    val name: String,
    val rank: RankXX,
    val rankPlatformSpecific: RankPlatformSpecificX,
    val value: String
)

data class GameInfoX(
    val badges: List<BadgeXX>
)

data class RankXX(
    val rankPos: String,
    val topPercent: String
)

data class RankPlatformSpecificX(
    val rankPos: String,
    val topPercent: String
)

data class BadgeXX(
    val name: String,
    val value: String
)

data class ImgAssetsXXXXX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXXXXXX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXXXXXXX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXXXXXXXX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXXXXXXXXX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXXXXXXXXXX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXXXXXXXXXXX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXXXXXXXXXXXX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXXXXXXXXXXXXX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXXXXXXXXXXXXXX(
    val banner: String,
    val icon: String
)

data class DataXX(
    val key: String,
    val name: String,
    val rank: RankXXX,
    val rankPlatformSpecific: RankPlatformSpecificXX,
    val value: String
)

data class RankXXX(
    val rankPos: String,
    val topPercent: String
)

data class RankPlatformSpecificXX(
    val rankPos: String,
    val topPercent: String
)

data class ImgAssetsXXXXXXXXXXXXXXX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXXXXXXXXXXXXXXXX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXXXXXXXXXXXXXXXXX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXXXXXXXXXXXXXXXXXX(
    val banner: String,
    val icon: String
)

data class DataXXX(
    val key: String,
    val name: String,
    val rank: RankXXXX,
    val rankPlatformSpecific: RankPlatformSpecificXXX,
    val value: String
)

data class RankXXXX(
    val rankPos: String,
    val topPercent: String
)

data class RankPlatformSpecificXXX(
    val rankPos: String,
    val topPercent: String
)

data class ImgAssetsXXXXXXXXXXXXXXXXXXX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXXXXXXXXXXXXXXXXXXXX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXXXXXXXXXXXXXXXXXXXXX(
    val banner: String,
    val icon: String
)

data class DataXXXX(
    val global: Boolean,
    val key: String,
    val name: String,
    val value: String
)

data class GameInfoXX(
    val badges: List<BadgeXXX>,
    val frame: String,
    val frameRarity: String,
    val Stringro: String,
    val StringroRarity: String,
    val pose: String,
    val poseRarity: String,
    val skin: String,
    val skinRarity: String
)

data class BadgeXXX(
    val category: String,
    val name: String,
    val value: String
)

data class RateLimit(
    val current_req: String,
    val max_per_second: String
)

data class Damage(
    val name: String,
    val value: String
)

data class Kd(
    val name: String,
    val value: String
)

data class Kills(
    val name: String,
    val value: String
)

data class KillsSeason11(
    val name: String,
    val value: String
)

data class KillsSeason12(
    val name: String,
    val value: String
)

data class ShotgunKills(
    val name: String,
    val value: String
)

data class SmokeGrenadeEnemiesHit(
    val name: String,
    val value: String
)

data class SpecialEventDamage(
    val name: String,
    val value: String
)

data class SpecialEventWins(
    val name: String,
    val value: String
)

data class WinsSeason11(
    val name: String,
    val value: String
)
