package com.example.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class EditPostActivityContract : ActivityResultContract<String, String?>() {
    override fun createIntent(context: Context, input: String): Intent =
        Intent(context, EditPostActivity::class.java).apply {
            this.putExtra(Intent.EXTRA_TEXT, input)
        }

    override fun parseResult(resultCode: Int, intent: Intent?): String? =
        if (resultCode == Activity.RESULT_OK) {
            intent?.getStringExtra(Intent.EXTRA_TEXT)
        } else {
            null
        }
}