package com.ihardanilchanka.sampleapp2.device

import com.ihardanilchanka.sampleapp2.domain.NavigationController
import com.ihardanilchanka.sampleapp2.domain.NavigationDestination
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class NavigationControllerImpl : NavigationController {

    private val _navigateToDestination =
        MutableSharedFlow<NavigationDestination>(replay = 0, extraBufferCapacity = 1)
    private val _navigateUpEvent = MutableSharedFlow<Unit>(replay = 0, extraBufferCapacity = 1)


    override fun navigateUp() {
        _navigateUpEvent.tryEmit(Unit)
    }

    override fun navigate(destinationId: NavigationDestination) {
        _navigateToDestination.tryEmit(destinationId)
    }

    override fun observeDestinations() = _navigateToDestination.asSharedFlow()

    override fun observeUpEvents() = _navigateUpEvent.asSharedFlow()
}