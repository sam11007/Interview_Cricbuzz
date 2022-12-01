package com.android.cricbuzz.network.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


class MenuModel {
    @JsonClass(generateAdapter = true)
data class Response(
    @Json(name = "menus")
    val menus: List<Menu?>? = emptyList()
)

@JsonClass(generateAdapter = true)
data class Menu(
    @Json(name = "categories")
    val categories: List<Category?>? = emptyList(),
    @Json(name = "restaurantId")
    val restaurantId: Int? = null
)

@JsonClass(generateAdapter = true)
data class Category(
    @Json(name = "id")
    val id: String? = null,
    @Json(name = "menu-items")
    var menuItems: List<MenuItem?>? = emptyList(),
    @Json(name = "name")
    val name: String? = null
)

@JsonClass(generateAdapter = true)
data class MenuItem(
    @Json(name = "description")
    val description: String? = null,
    @Json(name = "id")
    val id: String? = null,
    @Json(name = "images")
    val images: List<String?>? = emptyList(),
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "price")
    val price: String? = null
)
}