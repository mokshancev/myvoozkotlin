package com.example.myvoozkotlin.user.data

import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.searchEmptyAuditory.model.Classroom
import com.example.myvoozkotlin.user.domain.ChangeFullNameUseCase
import com.example.myvoozkotlin.user.domain.ChangeIdGroupUserUseCase
import com.example.myvoozkotlin.user.domain.EmptyAuditoryUseCase
import com.example.myvoozkotlin.user.domain.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChangeIdGroupUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : ChangeIdGroupUserUseCase {
    override fun invoke(
        accessToken: String,
        idUser: Int,
        nameGroup: String,
        idGroup: Int
    ): Flow<Event<Boolean>> = userRepository.changeIdGroupUser(accessToken, idUser, nameGroup, idGroup)
}