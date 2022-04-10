package com.ihardanilchanka.sampleapp2

import kotlinx.coroutines.delay

/**
 * Makes an artificial delay while suspend function run. Sometimes simulates an error.
 */
suspend fun randomDelay() {
    val delaySeconds = (1..4).random()
    delay(delaySeconds * 1000L)
    if (delaySeconds == 4) {
        error("Some artificial error occurred!")
    }
}