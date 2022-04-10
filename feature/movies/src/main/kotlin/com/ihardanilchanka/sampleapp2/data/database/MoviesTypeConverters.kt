package com.ihardanilchanka.sampleapp2.data.database

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class MoviesTypeConverters : KoinComponent {

    private val moshi: Moshi by inject()

    @TypeConverter
    fun Long?.toDate() = this?.let(::Date)

    @TypeConverter
    fun Date?.toTimestamp() = this?.time

    @TypeConverter
    fun String.toListInt(): List<Int>? = moshi.adapter<List<Int>>(List::class.java).fromJson(this)

    @TypeConverter
    fun List<Int>.toJson(): String = moshi.adapter<List<Int>>(List::class.java).toJson(this)
}