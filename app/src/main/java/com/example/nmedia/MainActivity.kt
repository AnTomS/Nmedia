package com.example.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.nmedia.databinding.ActivityMainBinding
import dto.Post


class MainActivity : AppCompatActivity() {

    private lateinit var like: TextView
    // private lateinit var shares_num: TextView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            author = "Нетология. Университет интернет-профессий будущего",
            authorAvatar = "",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb"
        )
        with(binding) {
            content.text = post.content
            published.text = post.published
            author.text = post.author
            likes.setOnClickListener {
                post.liked = !post.liked
                likes.setImageResource(
                    if (post.liked) {
                        R.drawable.ic_baseline_favorite_24
                    } else {
                        R.drawable.ic_baseline_favorite_border_24
                    }
                )

                if (post.liked) {
                    post.likeCount++
                } else {
                    post.likeCount--
                }
                like.text =
                    formatCount(post.likeCount)
            }
            shares.setOnClickListener() {
                post.sharesNum++
                sharesNum.text = formatCount(post.sharesNum)

            }

        }
    }
}