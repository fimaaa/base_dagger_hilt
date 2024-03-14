package com.data.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeviceLocation(val latitude: Double = 0.0, val longitude: Double = 0.0) : Parcelable