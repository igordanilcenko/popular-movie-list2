package com.ihardanilchanka.sampleapp2

sealed class LoadingState {
    object Ready : LoadingState()
    object Loading : LoadingState()
    data class Error(val error: Throwable) : LoadingState()
}
