package com.example.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

import com.example.nmedia.databinding.FragmentEditPostBinding
import com.example.nmedia.util.AndroidUtils
import com.example.nmedia.viewmodel.PostViewModel
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class EditPostFragment : Fragment() {


    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEditPostBinding.inflate(
            inflater,
            container,
            false
        )

        arguments?.textArg?.let {
            binding.content.setText(it)
        }

        binding.content.requestFocus()
        binding.save.setOnClickListener {
            if (!binding.content.text.isNullOrBlank()) {
                viewModel.editContent(binding.content.text.toString())
                viewModel.save()
            }
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
        }
        return binding.root
    }

        companion object {
            var Bundle.textArg: String? by StringArg
        }

        object StringArg : ReadWriteProperty<Bundle, String?> {
            override fun getValue(thisRef: Bundle, property: KProperty<*>): String? {
                return thisRef.getString(property.name)
            }

            override fun setValue(thisRef: Bundle, property: KProperty<*>, value: String?) {
                thisRef.putString(property.name, value)
            }
        }
    }



