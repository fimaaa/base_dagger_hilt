package com.model.crypto.crypto

import com.google.gson.annotations.SerializedName

data class CryptoData(

    @SerializedName("Id")
    val id: Int = 0,

    @SerializedName("Name")
    var name: String = "",

    @SerializedName("FullName")
    val fullName: String = "",

    @SerializedName("Internal")
    val internalName: String = "",

    @SerializedName("ImageUrl")
    val imageUrl: String = "",

    @SerializedName("Url")
    val urlLink: String = "",

    @SerializedName("Algorithm")
    val algorithm: String = "",

    @SerializedName("ProofType")
    val proofType: String = "",

    @SerializedName("Rating")
    val rating: Rating = Rating()
) {
    data class Rating(
        @SerializedName("Weiss")
        val weissRating: RatingData = RatingData()
    ) {
        data class RatingData(
            @SerializedName("Rating")
            val ratingOverall: String = "",
            @SerializedName("TechnologyAdoptionRating")
            val ratingTech: String = "",
            @SerializedName("MarketPerformanceRating")
            val ratingMarket: String = ""
        )
    }
}