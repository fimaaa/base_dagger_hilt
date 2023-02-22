package com.data.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Devicelocation(val latitude: Double = 0.0, val longitude: Double = 0.0) : Parcelable