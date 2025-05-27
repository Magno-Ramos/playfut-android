package com.magnus.playfut.ui.features.initializer

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.magnus.playfut.ui.extensions.setLightStatusBar
import com.magnus.playfut.ui.features.home.HomeActivity

class InitializerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        setLightStatusBar()
        super.onCreate(savedInstanceState)
        setContent {
            AppInitializer {
                startActivity(HomeActivity.createIntent(this))
                finish()
            }
        }
    }
}

@Composable
private fun AppInitializer(
    onReady: () -> Unit
) {
    var hasError by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val auth = Firebase.auth
        if (auth.currentUser == null) {
            auth.signInAnonymously().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onReady()
                } else {
                    hasError = true
                }
            }
        } else {
            onReady()
        }
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (hasError.not()) {
            CircularProgressIndicator()
        } else {
            Text(text = "Desculpe, ocorreu um erro")
        }
    }
}