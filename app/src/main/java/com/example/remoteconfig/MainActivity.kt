package com.example.remoteconfig

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.remoteconfig.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelMsgObservers()
    }

    private fun viewModelMsgObservers() {
        mainViewModel.wishMessage.observe(this) { message ->
            binding.WishMsg.text = message
        }

        mainViewModel.backgroundColor.observe(this) { color ->
            binding.main.setBackgroundColor(color)
        }

        mainViewModel.imageUrl.observe(this) { url ->
            Glide.with(this)
                .load(url)
                .error(R.drawable.chemistry)
                .into(binding.imgUrl)
        }
    }
}





