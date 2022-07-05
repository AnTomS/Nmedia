package dto

data class Post(
    val id: Long = 0,
    val author: String,
    val authorAvatar: String,
    val published: String,
    val content: String,
    var liked: Boolean = false,
    var likeCount: Long = 999,
    var shares_num: Long = 999_998,
)