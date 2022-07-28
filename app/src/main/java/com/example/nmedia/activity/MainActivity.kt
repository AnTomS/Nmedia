package com.example.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.nmedia.R
import com.example.nmedia.adapter.OnInterfuctionListener
import com.example.nmedia.adapter.PostsAdapter
import com.example.nmedia.databinding.ActivityMainBinding
import com.example.nmedia.dto.Post
import com.example.nmedia.util.AndroidUtils
import com.example.nmedia.viewmodel.PostViewModel


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        binding.group.visibility = View.GONE

        val adapter = PostsAdapter(
            object : OnInterfuctionListener {
                override fun onEdit(post: Post) {
                    viewModel.edit(post)
                    binding.group.visibility = View.VISIBLE
                }

                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onShare(post: Post) {
                    viewModel.share(post.id)
                }
            }
        )
        viewModel.edited.observe(this) { edited ->
            if (edited.id == 0L) {
                return@observe
            }
            binding.content.setText(edited.content)
            binding.content.requestFocus()
        }


        binding.save.setOnClickListener() {
            if (binding.content.text.isNullOrBlank()) {

                Toast.makeText(it.context, getString(R.string.empty_post_error), Toast.LENGTH_SHORT)
                    .show()

                return@setOnClickListener
            }
            val text = binding.content.text.toString()

            viewModel.editContent(text)
            viewModel.save()
            binding.content.clearFocus()
            AndroidUtils.hideKeyboard(binding.content)
            binding.content.setText("")
            binding.group.visibility = View.GONE
        }

        binding.cancel.setOnClickListener {
            viewModel.editingClear()
            with(binding.content) {
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)

                binding.group.visibility = View.GONE
            }
        }

        binding.list.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
    }
}

