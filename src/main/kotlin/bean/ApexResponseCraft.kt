class ApexResponseCraft : ArrayList<ApexResponseCraftItem>()

data class ApexResponseCraftItem(
    val bundle: String,
    val bundleContent: List<BundleContent>,
    val bundleType: String,
    val end: Int,
    val endDate: String,
    val start: Int,
    val startDate: String
)

data class BundleContent(
    val cost: Int,
    val item: String,
    val itemType: ItemType
)

data class ItemType(
    val asset: String,
    val name: String,
    val rarity: String,
    val rarityHex: String
)