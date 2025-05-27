package com.magnus.playfut.ui.extensions

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat

fun AppCompatActivity.setLightStatusBar() {
    WindowCompat.getInsetsController(window, window.decorView)
        .isAppearanceLightStatusBars = true
}

val Context.activity: AppCompatActivity?
    get() = when (this) {
        is AppCompatActivity -> this
        else -> null
    }