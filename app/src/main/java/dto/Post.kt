package dto

data class Post(
    val id: Long = 0,
    val author: String,
    val authorAvatar: String,
    val published: String,
    val content: String,
    var liked: Boolean = false,
    var likeCount: Long = 999,
    var sharesNum: Long = 999_998,
)