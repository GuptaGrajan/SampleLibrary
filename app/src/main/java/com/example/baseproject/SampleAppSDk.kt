package com.example.baseproject

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

import java.util.Objects

@AndroidEntryPoint
class SampleAppSDk private constructor(private val context: Context) : AppCompatActivity() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: SampleAppSDk? = null
        fun getInstance(context: Context): SampleAppSDk {
            return instance ?: synchronized(this) {
                instance ?: SampleAppSDk(context).also { instance = it }
            }
        }
    }

    fun launchSdk() {
        val intent = Intent(context,MainActivity::class.java)
        context.startActivity(intent)
        finish()
    }



}


