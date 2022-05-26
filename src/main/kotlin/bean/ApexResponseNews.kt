class ApexResponseNews : ArrayList<ApexResponseNewsItem>()

data class ApexResponseNewsItem(
    val img: String,
    val link: String,
    val short_desc: String,
    val title: String
)