package com.ihardanilchanka.sampleapp2.domain

import com.ihardanilchanka.sampleapp2.domain.usecase.NoArgsUseCase

class NavigateUpUseCase(
    private val navigationController: NavigationController,
) : NoArgsUseCase<Unit> {

    override fun invoke() {
        navigationController.navigateUp()
    }
}