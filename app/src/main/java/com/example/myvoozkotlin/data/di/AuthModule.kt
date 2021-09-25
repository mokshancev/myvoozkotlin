package com.example.myvoozkotlin.data.di

import com.example.myvoozkotlin.auth.data.AuthRepositoryImpl
import com.example.myvoozkotlin.auth.data.AuthVkUseCaseImpl
import com.example.myvoozkotlin.auth.data.AuthYaUseCaseImpl
import com.example.myvoozkotlin.auth.domain.AuthRepository
import com.example.myvoozkotlin.auth.domain.AuthVkUseCase
import com.example.myvoozkotlin.auth.domain.AuthYaUseCase
import com.example.myvoozkotlin.data.api.AuthApi
import com.example.myvoozkotlin.data.db.DbUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.realm.Realm

@Module
@InstallIn(ViewModelComponent::class)
object AuthModule {

    @Provides
    fun provideAuthVkUseCase(authRepository: AuthRepository): AuthVkUseCase{
        return AuthVkUseCaseImpl(authRepository)
    }

    @Provides
    fun provideAuthYaUseCase(authRepository: AuthRepository): AuthYaUseCase{
        return AuthYaUseCaseImpl(authRepository)
    }

    @Provides
    fun provideAuthRepository(realm: Realm, dbUtils: DbUtils, authApi: AuthApi) : AuthRepository {
        return AuthRepositoryImpl(realm, dbUtils, authApi)
    }
}