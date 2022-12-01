package com.android.cricbuzz.network.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


class RestaurantModel {
@JsonClass(generateAdapter = true)
data class Response(
    @Json(name = "restaurants")
    val restaurants: List<Restaurant?>? = emptyList()
)

@JsonClass(generateAdapter = true)
data class Restaurant(
    @Json(name = "address")
    val address: String? = null,
    @Json(name = "latlng")
    val latlng: LatLng? = null,
    @Json(name = "cuisine_type")
    val cuisineType: String? = null,
    @Json(name = "id")
    var id: Int?,
    @Json(name = "name")
    val name: String?= null,
    @Json(name = "neighborhood")
    val neighborhood: String?= null,
    @Json(name = "operating_hours")
    val operatingHours: OperatingHours?= null,
    @Json(name = "photograph")
    val photograph: String?= null,
    @Json(name = "reviews")
    val reviews: List<Review?>?= emptyList(),
    var categories: List<MenuModel.Category?>? = emptyList(),
)
@JsonClass(generateAdapter = true)
data class LatLng(
    @Json(name = "lat")
    val lat: Double? = null,
    @Json(name = "lng")
    val lng: Double? = null
)


@JsonClass(generateAdapter = true)
data class OperatingHours(
    @Json(name = "Friday")
    val friday: String? = null,
    @Json(name = "Monday")
    val monday: String?= null,
    @Json(name = "Saturday")
    val saturday: String?= null,
    @Json(name = "Sunday")
    val sunday: String?= null,
    @Json(name = "Thursday")
    val thursday: String?= null,
    @Json(name = "Tuesday")
    val tuesday: String?= null,
    @Json(name = "Wednesday")
    val wednesday: String?= null
)

@JsonClass(generateAdapter = true)
data class Review(
    @Json(name = "comments")
    val comments: String?= null,
    @Json(name = "date")
    val date: String?= null,
    @Json(name = "name")
    val name: String?= null,
    @Json(name = "rating")
    val rating: Int?= null
)
}