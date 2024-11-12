package com.example.remoteconfig

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.remoteconfig.databinding.ActivityMainBinding
import com.example.remoteconfig.domain.Constants
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = Constants.KEY_INTERVAL
        }
        remoteConfig.setConfigSettingsAsync(configSettings)

        //default resource
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        //fetching
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val wishMsg = remoteConfig.getString(Constants.KEY_WISH_MSG)
                    binding.WishMsg.text = wishMsg

                    val bgHome = remoteConfig.getString(Constants.KEY_BG_HOME)
                    binding.main.setBackgroundColor(Color.parseColor(bgHome))

                    val imageUrl = remoteConfig.getString(Constants.KEY_IMG_URL)
                    Glide.with(this)
                        .load(imageUrl)
                        .error(R.drawable.chemistry)
                        .into(binding.imgUrl)
                }
            }
    }
}




