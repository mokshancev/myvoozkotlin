package com.example.myvoozkotlin.user.di

import com.example.myvoozkotlin.user.api.UserApi
import com.example.myvoozkotlin.data.db.DbUtils
import com.example.myvoozkotlin.user.data.ChangeFullNameUseCaseImpl
import com.example.myvoozkotlin.user.data.EmptyAuditoryUseCaseImpl
import com.example.myvoozkotlin.user.data.UserRepositoryImpl
import com.example.myvoozkotlin.user.domain.ChangeFullNameUseCase
import com.example.myvoozkotlin.user.domain.EmptyAuditoryUseCase
import com.example.myvoozkotlin.user.domain.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.realm.Realm

@Module
@InstallIn(ViewModelComponent::class)
object UserModule {

    @Provides
    fun provideAuthVkUseCase(userRepository: UserRepository): ChangeFullNameUseCase{
        return ChangeFullNameUseCaseImpl(userRepository)
    }

    @Provides
    fun provideEmptyAuditoryUseCase(userRepository: UserRepository): EmptyAuditoryUseCase{
        return EmptyAuditoryUseCaseImpl(userRepository)
    }

    @Provides
    fun provideUserRepository(realm: Realm, dbUtils: DbUtils, userApi: UserApi) : UserRepository {
        return UserRepositoryImpl(realm, dbUtils, userApi)
    }
}