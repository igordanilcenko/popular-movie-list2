package com.ihardanilchanka.sampleapp2.koin

import com.ihardanilchanka.sampleapp2.device.NavigationControllerImpl
import com.ihardanilchanka.sampleapp2.domain.NavigateUpUseCase
import com.ihardanilchanka.sampleapp2.domain.NavigationController
import com.ihardanilchanka.sampleapp2.system.ComposeNavigation
import org.koin.dsl.module

object NavigationModule {
    fun init() = module {
        single<NavigationController> { NavigationControllerImpl() }
        factory { ComposeNavigation(get()) }

        factory { NavigateUpUseCase(get()) }
    }
}