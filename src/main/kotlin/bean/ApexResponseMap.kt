package pers.shennoter

data class ApexResponseMap(
    val arenas: Arenas,
    val arenasRanked: ArenasRanked,
    val battle_royale: BattleRoyale,
    val control: Control,
    val ranked: Ranked
)

data class Arenas(
    val current: Current,
    val next: Next
)

data class ArenasRanked(
    val current: CurrentX,
    val next: NextX
)

data class BattleRoyale(
    val current: CurrentXX,
    val next: NextXX
)

data class Control(
    val current: CurrentXXX,
    val next: NextXXX
)

data class Ranked(
    val current: CurrentXXXX,
    val next: NextXXXX
)

data class Current(
    val DurationInMinutes: Int,
    val DurationInSecs: Int,
    val asset: String,
    val code: String,
    val end: Int,
    val map: String,
    val readableDate_end: String,
    val readableDate_start: String,
    val remainingMins: Int,
    val remainingSecs: Int,
    val remainingTimer: String,
    val start: Int
)

data class Next(
    val DurationInMinutes: Int,
    val DurationInSecs: Int,
    val code: String,
    val end: Int,
    val map: String,
    val readableDate_end: String,
    val readableDate_start: String,
    val start: Int
)

data class CurrentX(
    val DurationInMinutes: Int,
    val DurationInSecs: Int,
    val asset: String,
    val code: String,
    val end: Int,
    val map: String,
    val readableDate_end: String,
    val readableDate_start: String,
    val remainingMins: Int,
    val remainingSecs: Int,
    val remainingTimer: String,
    val start: Int
)

data class NextX(
    val DurationInMinutes: Int,
    val DurationInSecs: Int,
    val code: String,
    val end: Int,
    val map: String,
    val readableDate_end: String,
    val readableDate_start: String,
    val start: Int
)

data class CurrentXX(
    val DurationInMinutes: Int,
    val DurationInSecs: Int,
    val asset: String,
    val code: String,
    val end: Int,
    val map: String,
    val readableDate_end: String,
    val readableDate_start: String,
    val remainingMins: Int,
    val remainingSecs: Int,
    val remainingTimer: String,
    val start: Int
)

data class NextXX(
    val DurationInMinutes: Int,
    val DurationInSecs: Int,
    val code: String,
    val end: Int,
    val map: String,
    val readableDate_end: String,
    val readableDate_start: String,
    val start: Int
)

data class CurrentXXX(
    val DurationInMinutes: Int,
    val DurationInSecs: Int,
    val asset: String,
    val code: String,
    val end: Int,
    val map: String,
    val readableDate_end: String,
    val readableDate_start: String,
    val remainingMins: Int,
    val remainingSecs: Int,
    val remainingTimer: String,
    val start: Int
)

data class NextXXX(
    val DurationInMinutes: Int,
    val DurationInSecs: Int,
    val code: String,
    val end: Int,
    val map: String,
    val readableDate_end: String,
    val readableDate_start: String,
    val start: Int
)

data class CurrentXXXX(
    val code: String,
    val asset: String,
    val map: String
)

data class NextXXXX(
    val map: String
)