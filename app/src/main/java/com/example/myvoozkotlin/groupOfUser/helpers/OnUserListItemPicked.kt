package com.example.myvoozkotlin.groupOfUser.helpers

import com.example.homelibrary.model.UserShort
import com.example.myvoozkotlin.models.SearchItem
import java.util.*

interface OnUserListItemPicked {
    fun onUserListItemClick(userShort: UserShort)
}