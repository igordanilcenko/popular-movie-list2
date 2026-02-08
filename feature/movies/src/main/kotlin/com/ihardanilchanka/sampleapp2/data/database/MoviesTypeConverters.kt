package com.ihardanilchanka.sampleapp2.data.database

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Date

class MoviesTypeConverters : KoinComponent {

    private val moshi: Moshi by inject()

    @TypeConverter
    fun toDate(value: Long?): Date? = value?.let(::Date)

    @TypeConverter
    fun toTimestamp(value: Date?): Long? = value?.time

    @TypeConverter
    fun toListInt(value: String?): List<Int>? = value?.let {
        moshi.adapter<List<Int>>(List::class.java).fromJson(it)
    }

    @TypeConverter
    fun toJson(value: List<Int>?): String? = value?.let {
        moshi.adapter<List<Int>>(List::class.java).toJson(it)
    }
}