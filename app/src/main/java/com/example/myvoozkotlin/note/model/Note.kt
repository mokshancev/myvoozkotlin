package com.example.myvoozkotlin.note.model

import com.example.myvoozkotlin.models.PhotoItem
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Note(
    @SerializedName("name_object") var nameObject:String,
    @SerializedName("name") var name: String,
    @SerializedName("text") var text:String,
    @SerializedName("date") var date: String,
    @SerializedName("mark_me") var markMe:Boolean,
    @SerializedName("state_completed") var stateCompleted:Boolean,
    @SerializedName("id") var id:Int,
    @SerializedName("id_user") var idUser:Int,
    @SerializedName("id_object") var idObject:Int,
    @SerializedName("images") var images:List<PhotoItem>,
    ) : Serializable