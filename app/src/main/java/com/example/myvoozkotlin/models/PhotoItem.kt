package com.example.myvoozkotlin.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PhotoItem(
    @SerializedName("path")
    val previewPath: String,
    @SerializedName("full_path")
    val fullPhoto: String,
    val id: Int,
    ) : Serializable