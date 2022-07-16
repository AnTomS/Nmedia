package com.example.nmedia.repository

import androidx.recyclerview.widget.DiffUtil
import com.example.nmedia.dto.Post

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

    // Обсудим позже
    override fun getChangePayload(oldItem: Post, newItem: Post): Any = Unit
}