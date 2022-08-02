package com.example.nmedia.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import com.example.nmedia.R
import com.example.nmedia.adapter.OnInterfuctionListener
import com.example.nmedia.adapter.PostsAdapter
import com.example.nmedia.databinding.ActivityMainBinding
import com.example.nmedia.dto.Post

import com.example.nmedia.viewmodel.PostViewModel


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()

        val newPostLauncher = registerForActivityResult(NewPostActivityContract()) { text ->
            text ?: return@registerForActivityResult
            viewModel.editContent(text)
            viewModel.save()
        }

        val newEditPostLauncher = registerForActivityResult(EditPostActivityContract()) { text ->
            text ?: return@registerForActivityResult
            viewModel.editContent(text)
            viewModel.save()

        }


        val adapter = PostsAdapter(
            object : OnInterfuctionListener {

                override fun onEdit(post: Post) {
                    Intent().apply {
                        action = Intent.ACTION_EDIT
                        putExtra(Intent.EXTRA_TEXT, post.content)
                        type = "text/plain"
                    }
                    viewModel.edit(post)
                    newEditPostLauncher.launch(post.content)
                }


                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onShare(post: Post) {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, post.content)
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(
                        intent,
                        getString(R.string.chooser_share_post)
                    )
                    startActivity(shareIntent)
                    viewModel.share(post.id)
                }

                override fun onVideo(post: Post) {
                    val webIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + post.video)
                    )
                    run {
                        startActivity(webIntent)
                    }
                }
            }

        )


        binding.list.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.create.setOnClickListener() {
            newPostLauncher.launch()
        }
    }
}

