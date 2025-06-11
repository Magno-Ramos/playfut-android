package com.magnus.playfut.extensions

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

val Context.activity: AppCompatActivity?
    get() = when (this) {
        is AppCompatActivity -> this
        else -> null
    }