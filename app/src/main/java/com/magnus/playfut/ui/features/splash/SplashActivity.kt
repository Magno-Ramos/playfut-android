package com.magnus.playfut.ui.features.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.magnus.playfut.domain.state.StateHandler
import com.magnus.playfut.ui.features.common.ErrorView
import com.magnus.playfut.ui.features.common.LoadingView
import com.magnus.playfut.ui.features.home.HomeActivity
import com.magnus.playfut.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SplashScreen() }
    }
}

@Composable
private fun SplashScreen(
    viewModel: SplashViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val authState by viewModel.authState.collectAsStateWithLifecycle()

    fun navigateHome() {
        val intent = Intent(context, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    LaunchedEffect(Unit) {
        viewModel.signInAnonymously()
    }

    AppTheme {
        StateHandler(authState) {
            success { navigateHome() }
            loading { LoadingView() }
            error { ErrorView() }
        }
    }
}