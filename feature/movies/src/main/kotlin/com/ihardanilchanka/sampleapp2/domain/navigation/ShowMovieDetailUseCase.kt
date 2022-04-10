package com.ihardanilchanka.sampleapp2.domain.navigation

import com.ihardanilchanka.sampleapp2.domain.usecase.NoArgsUseCase

class ShowMovieDetailUseCase(
    private val navigationController: MoviesNavigation,
) : NoArgsUseCase<Unit> {
    override fun invoke() {
        navigationController.goToMovieDetail()
    }
}