package com.data.common

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class BasePagination(
    @Json(name = "current_page")
    @SerializedName(value = "current_page")
    val currentPage: Int = 0,
    @Json(name = "per_page")
    @SerializedName(value = "per_page")
    val perPage: Int = 0,
    @Json(name = "Count")
    @SerializedName(value = "Count")
    val totalPage: Int = 0,
    @Json(name = "total_records")
    @SerializedName(value = "total_records")
    val totalRecords: Int = 0,
    @Json(name = "link_parameter")
    @SerializedName(value = "link_parameter")
    val linkParameter: Link = Link(),
    @Json(name = "links")
    @SerializedName(value = "links")
    val links: Link = Link()

) {
    data class Link(
        @Json(name = "first")
        @SerializedName(value = "first")
        val first: String = "",
        @Json(name = "last")
        @SerializedName(value = "last")
        val last: String = "",
        @Json(name = "next")
        @SerializedName(value = "next")
        val next: String = "",
        @Json(name = "previous")
        @SerializedName(value = "previous")
        val previous: String = ""
    )
}