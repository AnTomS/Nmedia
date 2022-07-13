package com.example.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nmedia.dto.Post

class InMemoryPostRepository : PostRepository {

    var post = Post(
        author = "Нетология. Университет интернет-профессий будущего",
        authorAvatar = "",
        published = "21 мая в 18:36",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb"
    )

    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data

    override fun like() {
        post = post.copy(
            liked = !post.liked,
            likeCount = if (post.liked) post.likeCount - 1 else post.likeCount + 1
        )
        data.value = post
    }


    override fun share() {
        post = post.copy(shareCount = post.shareCount + 1)
        data.value = post
    }

}