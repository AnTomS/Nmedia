package com.example.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nmedia.databinding.ActivityEditPostBinding

class EditPostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEditPostBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.content
        val content = intent.getStringExtra(Intent.EXTRA_TEXT)
        binding.content.requestFocus()
        binding.content.setText(content)


        binding.save.setOnClickListener {
            if (binding.content.text.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED, intent)
                finish()
            }
            val result = Intent()
                .putExtra(Intent.EXTRA_TEXT, binding.content.text.toString())

            setResult(RESULT_OK, result)

            finish()
        }


    }
}

