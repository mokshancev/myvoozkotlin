package com.example.myvoozkotlin.user.domain

import android.graphics.Bitmap
import com.example.homelibrary.model.AuthUser
import com.example.homelibrary.model.Lesson
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface UploadPhotoUseCase {
    operator fun invoke(accessToken: String, idUser : Int, image: Bitmap): Flow<Event<Boolean>>
}