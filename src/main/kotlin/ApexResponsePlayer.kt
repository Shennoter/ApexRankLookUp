package pers.shennoter

data class ApexResponsePlayer(
    val global: Global,
    val legends: Legends,
    val mozambiquehere_internal: MozambiquehereInternal,
    val realtime: Realtime,
    val total: Total
)

data class Global(
    val arena: Arena,
    val avatar: String,
    val badges: List<Badge>,
    val bans: Bans,
    val battlepass: Battlepass,
    val internalUpdateCount: Int,
    val level: Int,
    val name: String,
    val platform: String,
    val rank: Rank,
    val toNextLevelPercent: Int,
    val uid: Long
)

data class Legends(
    val all: All,
    val selected: Selected
)

data class MozambiquehereInternal(
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
    val shotgun_kills: ShotgunKills,
    val smoke_grenade_enemies_hit: SmokeGrenadeEnemiesHit,
    val specialEvent_damage: SpecialEventDamage,
    val specialEvent_wins: SpecialEventWins
)

data class Arena(
    val ladderPosPlatform: Int,
    val rankDiv: Int,
    val rankImg: String,
    val rankName: String,
    val rankScore: Int,
    val rankedSeason: String
)

data class Badge(
    val name: String,
    val value: Int
)

data class Bans(
    val isActive: Boolean,
    val last_banReason: String,
    val remainingSeconds: Int
)

data class Battlepass(
    val history: History,
    val level: String
)

data class Rank(
    val ladderPosPlatform: Int,
    val rankDiv: Int,
    val rankImg: String,
    val rankName: String,
    val rankScore: Int,
    val rankedSeason: String
)

data class History(
    val season1: Int,
    val season10: Int,
    val season11: Int,
    val season12: Int,
    val season2: Int,
    val season3: Int,
    val season4: Int,
    val season5: Int,
    val season6: Int,
    val season7: Int,
    val season8: Int,
    val season9: Int
)

data class All(
    val Ash: Ash,
    val Bangalore: Bangalore,
    val Bloodhound: Bloodhound,
    val Caustic: Caustic,
    val Crypto: Crypto,
    val Fuse: Fuse,
    val Gibraltar: Gibraltar,
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
    val ImgAssets: ImgAssetsXXXXXXXXXXXXXXXXXXXX,
    val LegendName: String,
    val `data`: List<DataXX>,
    val gameInfo: GameInfoX
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
    val ImgAssets: ImgAssetsXXXX
)

data class Fuse(
    val ImgAssets: ImgAssetsXXXXX
)

data class Gibraltar(
    val ImgAssets: ImgAssetsXXXXXX
)

data class Horizon(
    val ImgAssets: ImgAssetsXXXXXXX
)

data class Lifeline(
    val ImgAssets: ImgAssetsXXXXXXXX
)

data class Loba(
    val ImgAssets: ImgAssetsXXXXXXXXX
)

data class MadMaggie(
    val ImgAssets: ImgAssetsXXXXXXXXXX
)

data class Mirage(
    val ImgAssets: ImgAssetsXXXXXXXXXXX
)

data class Octane(
    val ImgAssets: ImgAssetsXXXXXXXXXXXX
)

data class Pathfinder(
    val ImgAssets: ImgAssetsXXXXXXXXXXXXX
)

data class Rampart(
    val ImgAssets: ImgAssetsXXXXXXXXXXXXXX
)

data class Revenant(
    val ImgAssets: ImgAssetsXXXXXXXXXXXXXXX
)

data class Seer(
    val ImgAssets: ImgAssetsXXXXXXXXXXXXXXXX
)

data class Valkyrie(
    val ImgAssets: ImgAssetsXXXXXXXXXXXXXXXXX,
    val `data`: List<DataX>
)

data class Wattson(
    val ImgAssets: ImgAssetsXXXXXXXXXXXXXXXXXX
)

data class Wraith(
    val ImgAssets: ImgAssetsXXXXXXXXXXXXXXXXXXX
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
    val value: Int
)

data class GameInfo(
    val badges: List<BadgeX>
)

data class RankX(
    val rankPos: Int,
    val topPercent: Double
)

data class RankPlatformSpecific(
    val rankPos: Int,
    val topPercent: Double
)

data class BadgeX(
    val name: String,
    val value: Int
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

data class DataX(
    val key: String,
    val name: String,
    val rank: RankXX,
    val rankPlatformSpecific: RankPlatformSpecificX,
    val value: Int
)

data class RankXX(
    val rankPos: Int,
    val topPercent: Double
)

data class RankPlatformSpecificX(
    val rankPos: Int,
    val topPercent: Double
)

data class ImgAssetsXXXXXXXXXXXXXXXXXX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXXXXXXXXXXXXXXXXXXX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXXXXXXXXXXXXXXXXXXXX(
    val banner: String,
    val icon: String
)

data class DataXX(
    val key: String,
    val name: String,
    val value: Int
)

data class GameInfoX(
    val badges: List<BadgeXX>,
    val frame: String,
    val frameRarity: String,
    val intro: String,
    val introRarity: String,
    val pose: String,
    val poseRarity: String,
    val skin: String,
    val skinRarity: String
)

data class BadgeXX(
    val category: String,
    val name: String,
    val value: Int
)

data class RateLimit(
    val current_req: String,
    val max_per_second: Int
)

data class Damage(
    val name: String,
    val value: Int
)

data class Kd(
    val name: String,
    val value: String
)

data class Kills(
    val name: String,
    val value: Int
)

data class ShotgunKills(
    val name: String,
    val value: Int
)

data class SmokeGrenadeEnemiesHit(
    val name: String,
    val value: Int
)

data class SpecialEventDamage(
    val name: String,
    val value: Int
)

data class SpecialEventWins(
    val name: String,
    val value: Int
)