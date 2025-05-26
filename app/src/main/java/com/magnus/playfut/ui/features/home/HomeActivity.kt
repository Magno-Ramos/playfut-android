package com.magnus.playfut.ui.features.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.magnus.playfut.ui.features.SetStatusBarIconsDark

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            SetStatusBarIconsDark(window)
            HomeScreen()
        }
    }

    companion object {
        fun createIntent(context: Context) =
            Intent(context, HomeActivity::class.java)
    }
}