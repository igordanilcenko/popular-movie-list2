package com.ihardanilchanka.sampleapp2.domain

data class NavigationDestination(
    val route: String,
    val launchSingleTop: Boolean = false,
    val popUpTo: String? = null,
    val popUpToInclusive: Boolean = false,
)