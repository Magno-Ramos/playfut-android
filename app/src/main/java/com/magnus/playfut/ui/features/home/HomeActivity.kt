package com.magnus.playfut.ui.features.home

import android.os.Bundle
import android.view.Window
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            SetStatusBarIconsDark(window)
            HomeScreen()
        }
    }
}

@Composable
private fun SetStatusBarIconsDark(window: Window) {
    val view = LocalView.current
    SideEffect {
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
    }
}