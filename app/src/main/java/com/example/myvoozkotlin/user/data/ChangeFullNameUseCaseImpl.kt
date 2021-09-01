package com.example.myvoozkotlin.user.data

import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.user.domain.ChangeFullNameUseCase
import com.example.myvoozkotlin.user.domain.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChangeFullNameUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : ChangeFullNameUseCase {
    override fun invoke(accessToken: String, idUser : Int, firstName : String, secondName : String): Flow<Event<Boolean>>
    = userRepository.changeUserFullName(accessToken, idUser, firstName, secondName)
}