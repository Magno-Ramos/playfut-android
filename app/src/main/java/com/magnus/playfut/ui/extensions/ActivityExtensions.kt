package com.magnus.playfut.ui.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat

fun AppCompatActivity.setLightStatusBar() {
    WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true
}