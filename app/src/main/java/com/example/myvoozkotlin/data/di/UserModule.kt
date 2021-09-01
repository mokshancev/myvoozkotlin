package com.example.myvoozkotlin.data.di

import com.example.myvoozkotlin.auth.data.AuthRepositoryImpl
import com.example.myvoozkotlin.auth.data.AuthVkUseCaseImpl
import com.example.myvoozkotlin.auth.domain.AuthRepository
import com.example.myvoozkotlin.auth.domain.AuthVkUseCase
import com.example.myvoozkotlin.data.api.AuthApi
import com.example.myvoozkotlin.data.api.NewsApi
import com.example.myvoozkotlin.data.api.ScheduleApi
import com.example.myvoozkotlin.data.api.UserApi
import com.example.myvoozkotlin.data.db.DbUtils
import com.example.myvoozkotlin.home.data.NewsRepositoryImpl
import com.example.myvoozkotlin.home.data.NewsUseCaseImpl
import com.example.myvoozkotlin.home.data.ScheduleDayRepositoryImpl
import com.example.myvoozkotlin.home.data.ScheduleDayUseCaseImpl
import com.example.myvoozkotlin.home.domain.NewsRepository
import com.example.myvoozkotlin.home.domain.NewsUseCase
import com.example.myvoozkotlin.home.domain.ScheduleDayRepository
import com.example.myvoozkotlin.home.domain.ScheduleDayUseCase
import com.example.myvoozkotlin.user.data.ChangeFullNameUseCaseImpl
import com.example.myvoozkotlin.user.data.UserRepositoryImpl
import com.example.myvoozkotlin.user.domain.ChangeFullNameUseCase
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
    fun provideUserRepository(realm: Realm, dbUtils: DbUtils, userApi: UserApi) : UserRepository {
        return UserRepositoryImpl(realm, dbUtils, userApi)
    }
}