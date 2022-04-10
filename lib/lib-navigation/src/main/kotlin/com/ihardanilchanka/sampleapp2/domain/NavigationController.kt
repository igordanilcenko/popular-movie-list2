package com.ihardanilchanka.sampleapp2.domain

import kotlinx.coroutines.flow.Flow

interface NavigationController {
    fun navigateUp()
    fun navigate(destinationId: NavigationDestination)
    fun observeDestinations(): Flow<NavigationDestination>
    fun observeUpEvents(): Flow<Unit>
}