package com.ihardanilchanka.sampleapp2.util

import java.util.Calendar
import java.util.Date

/**
 * Extracts year number from Date instance.
 */
fun Date.toYear(): Int {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal.get(Calendar.YEAR)
}
