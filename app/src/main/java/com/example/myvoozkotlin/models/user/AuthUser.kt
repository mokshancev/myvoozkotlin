package com.example.homelibrary.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName


data class AuthUser(
        @SerializedName("access_token") var accessToken:String,
        @SerializedName("photo") var photo: String,
        @SerializedName("first_name") var firstName:String,
        @SerializedName("last_name") var lastName: String,
        @SerializedName("id_rank") var idRank:String,
        @SerializedName("id") var id:Int,
        @SerializedName("id_university") var idUniversity:Int,
        @SerializedName("name_university") var nameUniversity:String,
        @SerializedName("id_group") var idGroup:Int,
        @SerializedName("name_group") var nameGroup:String,
        @SerializedName("id_group_of_user") var idGroupOfUser:Int,
        @SerializedName("error") var error:Int,
        @SerializedName("info_groups_of_user") val groupOfUser:GroupOfUser
    )