package com.ihardanilchanka.sampleapp2

import java.util.*

/**
 * Extracts year number from Date instance.
 */
fun Date.toYear(): Int {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal.get(Calendar.YEAR)
}
