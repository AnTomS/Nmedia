package com.example.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nmedia.dto.Post

class InMemoryPostRepository : PostRepository {
    private var posts = listOf(
        Post(
            id = 10,
            author = "Нетология. Университет интернет-профессий будущего",
            authorAvatar = "",
            published = "01 августа в 11:21",
            content = "Эмоциональный интеллект – как и зачем развивать EQ? Отличие EQ от IQ. Анастасия Высоцкая",
            video = "7GH5T3xPvGU",
            liked = false,
            likeCount = 141,
            shareCount = 97,
        ),
        Post(
            id = 9,
            author = "Нетология. Университет интернет-профессий будущего",
            authorAvatar = "",
            published = "21 марта в 11:21",
            content = "Привет, это  Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            video = null,
            liked = false,
            likeCount = 141,
            shareCount = 97,
        ),
        Post(
            id = 8,
            author = "Нетология. Университет интернет-профессий будущего",
            authorAvatar = "",
            published = "01 апреля в 12:11",
            content = "Языков программирования много, и выбрать какой-то один бывает нелегко. Собрали подборку статей, которая поможет вам начать, если вы остановили свой выбор на JavaScript.",
            video = null,
            liked = false,
            likeCount = 319,
            shareCount = 197,
        ),
        Post(
            id = 7,
            author = "Нетология. Университет интернет-профессий будущего",
            authorAvatar = "",
            published = "21 мая в 21:00",
            content = "Делиться впечатлениями о любимых фильмах легко, а что если рассказать так, чтобы все заскучали \uD83D\uDE34\n",
            video = null,
            likeCount = 1156,
            shareCount = 800,
        ),

        Post(
            id = 6,
            author = "Нетология. Университет интернет-профессий будущего",
            authorAvatar = "",
            published = "12 июля в 18:36",
            content = "Таймбоксинг — отличный способ навести порядок в своём календаре и разобраться с делами, которые долго откладывали на потом. Его главный принцип — на каждое дело заранее выделяется определённый отрезок времени. В это время вы работаете только над одной задачей, не переключаясь на другие. Собрали советы, которые помогут внедрить таймбоксинг \uD83D\uDC47\uD83C\uDFFB",
            video = null,
            liked = false,
            likeCount = 999,
            shareCount = 999_998,
        )
    )

    private val data = MutableLiveData(posts)

    override fun get(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                liked = !it.liked,
                likeCount = if (!it.liked) it.likeCount + 1 else it.likeCount - 1
            )
        }
        data.value = posts
    }


    override fun share(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(shareCount = it.shareCount + 1)
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {
        posts = if (post.id == 0L) {
            listOf(
                post.copy(
                    id = posts.firstOrNull()?.id ?: 1L + 1,
                    author = "Me",
                    published = "now",
                    liked = false,
                    likeCount = 0,
                    shareCount = 0
                )
            ) + posts
        } else {
            posts.map {
                if (it.id == post.id) it.copy(content = post.content) else it
            }
        }
        data.value = posts
    }
}

