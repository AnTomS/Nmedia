package com.example.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nmedia.dto.Post
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class PostRepositorySharedPrefsImp(
    private val context: Context
    ) : PostRepository {

    private val gson = Gson()
    private val prefs = context.getSharedPreferences("rero", Context.MODE_PRIVATE)
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val filename = "posts.json"
    private val key = "post"
    private var nextId = 1L
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        val file = context.filesDir.resolve(filename)
        if (file.exists()) {
            context.openFileInput(filename).bufferedReader().use {
                posts = gson.fromJson(it, type)
                data.value = posts
            }
        } else {
            sync()
        }
    }

    private fun sync() {
        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }


    override fun get(): LiveData<List<Post>>  = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                liked = !it.liked,
                likeCount = if (!it.liked) it.likeCount + 1 else it.likeCount - 1
            )
        }
        data.value = posts
        sync()
    }

    override fun share(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(shareCount = it.shareCount + 1)
        }
        data.value = posts
        sync()
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id == id }
        data.value = posts
        sync()
    }

    override fun save(post: Post) {
        posts = if (post.id == 0L) {
            listOf(
                post.copy(
                    id = ++nextId,
                    author = "Me",
                    published = "now",
                    liked = false,
                    likeCount = 0,
                    shareCount = 0
                )
            ) + posts
        } else {
            posts.map {
                if (it.id != post.id) it else it.copy(content = post.content)
            }
        }
        data.value = posts
        sync()
    }
}


