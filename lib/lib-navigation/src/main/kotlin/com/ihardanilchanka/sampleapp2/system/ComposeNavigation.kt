package com.ihardanilchanka.sampleapp2.system

import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import com.ihardanilchanka.sampleapp2.domain.NavigationController

class ComposeNavigation(
    private val navigationController: NavigationController,
) {

    private lateinit var androidNavController: NavHostController

    fun init(activity: ComponentActivity, navHostController: NavHostController) {
        androidNavController = navHostController

        activity.lifecycleScope.launchWhenCreated {
            navigationController.observeUpEvents().collect {
                androidNavController.navigateUp()
            }
        }

        activity.lifecycleScope.launchWhenCreated {
            navigationController.observeDestinations().collect { destination ->
                androidNavController.navigate(destination.route) {
                    launchSingleTop = destination.launchSingleTop
                    destination.popUpTo?.let {
                        popUpTo(it) { inclusive = destination.popUpToInclusive }
                    }
                }
            }
        }
    }
}