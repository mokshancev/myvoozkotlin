package com.example.myvoozkotlin.search.helpers

import com.example.myvoozkotlin.models.SearchItem
import java.util.*

interface OnSearchItemPicked {
    fun onSearchItemClick(searchItem: SearchItem)
}