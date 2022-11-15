package bean

data class ApexResponsePredator(
    val AP: AP,
    val RP: RP
)

data class AP(
    val PC: PC,
    val PS4: PS4,
    val SWITCH: SWITCH,
    val X1: X1
)

data class RP(
    val PC: PCX,
    val PS4: PS4X,
    val SWITCH: SWITCHX,
    val X1: X1X
)

data class PC(
    val foundRank: Int,
    val totalMastersAndPreds: Int,
    val uid: String,
    val updateTimestamp: Int,
    val `val`: Int
)

data class PS4(
    val foundRank: Int,
    val totalMastersAndPreds: Int,
    val uid: String,
    val updateTimestamp: Int,
    val `val`: Int
)

data class SWITCH(
    val foundRank: Int,
    val totalMastersAndPreds: Int,
    val uid: String,
    val updateTimestamp: Int,
    val `val`: Int
)

data class X1(
    val foundRank: Int,
    val totalMastersAndPreds: Int,
    val uid: String,
    val updateTimestamp: Int,
    val `val`: Int
)

data class PCX(
    val foundRank: Int,
    val totalMastersAndPreds: Int,
    val uid: String,
    val updateTimestamp: Int,
    val `val`: Int
)

data class PS4X(
    val foundRank: Int,
    val totalMastersAndPreds: Int,
    val uid: String,
    val updateTimestamp: Int,
    val `val`: Int
)

data class SWITCHX(
    val foundRank: Int,
    val totalMastersAndPreds: Int,
    val uid: String,
    val updateTimestamp: Int,
    val `val`: Int
)

data class X1X(
    val foundRank: Int,
    val totalMastersAndPreds: Int,
    val uid: String,
    val updateTimestamp: Int,
    val `val`: Int
)