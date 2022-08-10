package com.example.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.launch
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.nmedia.R
import com.example.nmedia.activity.EditPostFragment.Companion.textArg
import com.example.nmedia.adapter.OnInterfuctionListener
import com.example.nmedia.adapter.PostsAdapter
import com.example.nmedia.databinding.FragmentFeedBinding
import com.example.nmedia.dto.Post

import com.example.nmedia.viewmodel.PostViewModel
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


class FeedFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentFeedBinding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )


        val adapter = PostsAdapter(
            object : OnInterfuctionListener {

                override fun onEdit(post: Post) {

                    viewModel.edit(post)
                    findNavController().navigate(
                        R.id.action_feedFragment_to_editPostFragment,
                        Bundle().apply {
                            textArg = post.content
                        }
                    )
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

                override fun onPost(post: Post) {
                    findNavController().navigate(
                        R.id.action_feedFragment_to_postDetailsFragment,
                        Bundle().apply {
                            idArg = post.id
                        }
                    )
                }
            })

        binding.list.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        binding.create.setOnClickListener() {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }


        return (binding.root)
    }

    companion object {
        var Bundle.idArg: Long by IntArg
    }

    object IntArg : ReadWriteProperty<Bundle, Long> {
        override fun getValue(thisRef: Bundle, property: KProperty<*>): Long {
            return thisRef.getLong(property.name)
        }

        override fun setValue(thisRef: Bundle, property: KProperty<*>, value: Long) {
            thisRef.putLong(property.name, value)
        }
    }
}


