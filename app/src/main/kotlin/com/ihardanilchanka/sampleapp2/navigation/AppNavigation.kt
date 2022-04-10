package com.ihardanilchanka.sampleapp2.navigation

import com.ihardanilchanka.sampleapp2.domain.NavigationController
import com.ihardanilchanka.sampleapp2.domain.NavigationDestination
import com.ihardanilchanka.sampleapp2.domain.navigation.MoviesNavigation

class AppNavigation(
    private val navigationController: NavigationController,
) : MoviesNavigation {
    override fun goBack() {
        navigationController.navigateUp()
    }

    override fun goToMovieDetail() {
        navigationController.navigate(NavigationDestination(route = AppNavigationRoute.movieDetail))
    }
}