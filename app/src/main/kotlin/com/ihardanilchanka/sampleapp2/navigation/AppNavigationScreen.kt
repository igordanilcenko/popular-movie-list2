package com.ihardanilchanka.sampleapp2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ihardanilchanka.sampleapp2.presentation.moviedetail.MovieDetailScreen
import com.ihardanilchanka.sampleapp2.presentation.movielist.MovieListScreen

@Composable
fun AppNavigationScreen(navHostController: NavHostController) {
    NavHost(navHostController, startDestination = AppNavigationRoute.popularMovieList) {
        composable(AppNavigationRoute.popularMovieList) { MovieListScreen() }
        composable(AppNavigationRoute.movieDetail) { MovieDetailScreen() }
    }
}