package com.example.myvoozkotlin.models

import com.google.gson.annotations.SerializedName

class SearchItem(
    val name: String,
    @SerializedName("full_name")
    val fullName: String,
    var id: Int,
    var type: Int) {
}