package com.example.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.nmedia.databinding.ActivityMainBinding
import com.example.nmedia.viewmodel.PostViewModel


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel by viewModels<PostViewModel>()
        viewModel.data.observe(this) { post ->
            with(binding) {
                content.text = post.content
                published.text = post.published
                author.text = post.author
                like.text = formatCount(post.likeCount)
                sharesNum.text = formatCount(post.shareCount)
                val likeImage = if (post.liked) {
                    (R.drawable.ic_baseline_favorite_24)
                } else {
                    R.drawable.ic_baseline_favorite_border_24
                }
                likes.setImageResource(likeImage)
            }
            binding.shares.setOnClickListener {
                viewModel.share()
            }

            binding.likes.setOnClickListener {
                viewModel.like()
            }
        }

    }

}
