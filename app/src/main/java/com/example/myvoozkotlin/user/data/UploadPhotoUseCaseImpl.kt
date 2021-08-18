package com.example.myvoozkotlin.user.data

import android.graphics.Bitmap
import com.example.homelibrary.model.AuthUser
import com.example.myvoozkotlin.auth.domain.AuthRepository
import com.example.myvoozkotlin.auth.domain.AuthVkUseCase
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.home.domain.NewsRepository
import com.example.myvoozkotlin.home.domain.NewsUseCase
import com.example.myvoozkotlin.user.domain.ChangeFullNameUseCase
import com.example.myvoozkotlin.user.domain.UploadPhotoUseCase
import com.example.myvoozkotlin.user.domain.UserRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class UploadPhotoUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : UploadPhotoUseCase {
    override fun invoke(
        accessToken: String,
        idUser: Int,
        image: Bitmap
    ): Flow<Event<Boolean>> = userRepository.uploadPhoto(accessToken, idUser, image)
}