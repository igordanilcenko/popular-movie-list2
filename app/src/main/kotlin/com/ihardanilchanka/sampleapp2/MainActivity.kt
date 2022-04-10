package com.ihardanilchanka.sampleapp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.ihardanilchanka.sampleapp2.navigation.AppNavigationScreen
import com.ihardanilchanka.sampleapp2.resource.AppTheme
import com.ihardanilchanka.sampleapp2.system.ComposeNavigation
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val navigation: ComposeNavigation by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AppTheme { AppNavigationScreen(navController) }
            navigation.init(this, navController)
        }
    }
}
