package com.example.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nmedia.dto.Post
import com.example.nmedia.repository.InMemoryPostRepository
import com.example.nmedia.repository.PostRepository
import com.example.nmedia.repository.PostRepositorySharedPrefsImp

val empty = Post(
    0,
    "",
    "",
    "now",
    "",
    null,
    false,
    0
)

class PostViewModel (application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositorySharedPrefsImp(application)
    val data = repository.get()
    fun likeById(id: Long) = repository.likeById(id)

    fun share(id: Long) = repository.share(id)
    fun removeById(id: Long) = repository.removeById(id)

    private val edited = MutableLiveData(empty)

    fun edit(post: Post) {
        edited.value = post
    }

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun editContent(content: String) {
        edited.value?.let {
            val trimmed = content.trim()
            if (trimmed == it.content) {
                return
            }
            edited.value = it.copy(content = trimmed)
        }
    }

    fun editingClear() {
        edited.value = empty
    }

}