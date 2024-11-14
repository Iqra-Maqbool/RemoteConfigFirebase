package com.example.remoteconfig

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.remoteconfig.domain.Constants
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings

class MainActivityViewModel : ViewModel() {

    private val remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    private val _wishMessage = MutableLiveData<String>()
    val wishMessage: LiveData<String> = _wishMessage

    private val _backgroundColor = MutableLiveData<Int>()
    val backgroundColor: LiveData<Int> = _backgroundColor

    private val _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String> = _imageUrl

    private val _fetchSuccess = MutableLiveData<Boolean>()
    val fetchSuccess: LiveData<Boolean> = _fetchSuccess

    init {
        setupRemoteConfig()
        fetchRemoteConfigValues()
    }

    private fun setupRemoteConfig() {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = Constants.KEY_INTERVAL
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
    }

    private fun fetchRemoteConfigValues() {
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _fetchSuccess.value = true
                _wishMessage.value = remoteConfig.getString(Constants.KEY_WISH_MSG)
                _backgroundColor.value =
                    Color.parseColor(remoteConfig.getString(Constants.KEY_BG_HOME))
                _imageUrl.value = remoteConfig.getString(Constants.KEY_IMG_URL)
            } else {
                _fetchSuccess.value = false
            }
        }
    }
}
