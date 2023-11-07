package com.haki.hqrecipe

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.haki.hqrecipe.ui.theme.CookHqTheme
import com.haki.hqrecipe.ui.theme.genBg
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    private val splashTime = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CookHqTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    SplashLogo()
                }
            }
        }
    }

    @Composable
    fun SplashLogo(modifier: Modifier = Modifier) {
        LaunchedEffect(Unit) {
            delay(splashTime)
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(genBg)
        ) {
            Image(
                modifier = modifier
                    .align(Alignment.Center)
                    .padding(paddingValues = PaddingValues(80.dp)),
                painter = painterResource(R.drawable.cook_hq_logo),
                contentDescription = "Splash Screen Logo"
            )
        }
    }
}