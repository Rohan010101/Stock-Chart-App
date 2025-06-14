package com.example.stockchart.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Company(
    val name: String,
    val symbol: String,
    val sector: String,
    val stockValues: List<Float>,
    val changePercent: String,
    val marketCap: String,
    val volume: String,
    val isTrending: Boolean
): Parcelable
