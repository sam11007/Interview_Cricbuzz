package com.android.cricbuzz.utils

import com.android.cricbuzz.R

fun String.getDrawable() : Int {
    val map = mutableMapOf(("one.jpg" to R.drawable.one),
        ("two.jpg" to R.drawable.two),
        ("image1.jpg" to R.drawable.image1),
        ("image2.jpg" to R.drawable.image2),
        ("image3.jpg" to R.drawable.image3),
        ("image4.jpg" to R.drawable.image4),

    )
    return map[this] ?: R.drawable.one
}