package com.example.nmedia.dto

data class Post(
    val id: Long = 0,
    val author: String,
    val authorAvatar: String,
    val published: String,
    val content: String,
    val liked: Boolean = false,
    val likeCount: Long = 999,
    val shareCount: Long = 999_998,
)