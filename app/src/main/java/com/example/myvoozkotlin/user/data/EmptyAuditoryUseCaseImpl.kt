package com.example.myvoozkotlin.user.data

import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.searchEmptyAuditory.model.Classroom
import com.example.myvoozkotlin.user.domain.ChangeFullNameUseCase
import com.example.myvoozkotlin.user.domain.EmptyAuditoryUseCase
import com.example.myvoozkotlin.user.domain.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EmptyAuditoryUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : EmptyAuditoryUseCase {
    override fun invoke(
        date: String,
        idCorpus: Int,
        lowNumber: Int,
        upperNumber: Int,
        idUniversity: Int
    ): Flow<Event<List<List<Classroom>>>> = userRepository.getEmptyAuditory(date, idCorpus, lowNumber, upperNumber, idUniversity)
}