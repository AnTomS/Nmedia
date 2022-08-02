package com.example.nmedia.activity

import android.icu.text.DecimalFormat
import java.lang.Math.round

fun formatCount(count: Long): String {
    val df = DecimalFormat("#.#")
    return when (count) {
        in 0..999 -> count.toString()
        in 1000..999_99 -> "${
            round(count.toDouble() / 1000).toLong()
        }K"
        in 100_000..999_999 -> "${(count.toDouble() / 1000).toLong()}K"
        in 1_000_000..9_000_000 -> "${
            df.format(count.toDouble() / 1000000).toString().toLong()
        }M"
        else -> "${round(count.toDouble() / 1000000).toLong()}M"
    }
}