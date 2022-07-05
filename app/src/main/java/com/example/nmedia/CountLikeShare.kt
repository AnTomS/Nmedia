package com.example.nmedia

import android.icu.text.DecimalFormat
import java.lang.Math.round

fun formatCount(count: Long): String {
    val df = DecimalFormat("#.#")
    return when (count) {
        in 0..999 -> count.toString() // до 999
        in 1000..999_99 -> "${
            round(count.toDouble() / 1000).toLong()}K" // до 99К
        in 100_000..999_999 -> "${(count.toDouble() / 1000).toLong()}K" // до 999К
        in 1_000_000..9_000_000 -> "${
            df.format(count.toDouble() / 1000000).toString().toLong()
        }M"
        else -> "${round(count.toDouble() / 1000000).toLong()}M"
    }
}