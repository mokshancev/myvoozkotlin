package com.example.myvoozkotlin.user.domain

import com.example.myvoozkotlin.helpers.Event
import kotlinx.coroutines.flow.Flow

interface ChangeIdGroupUserUseCase {
    operator fun invoke(
        accessToken: String,
        idUser: Int,
        nameGroup: String,
        idGroup: Int
    ): Flow<Event<Boolean>>
}