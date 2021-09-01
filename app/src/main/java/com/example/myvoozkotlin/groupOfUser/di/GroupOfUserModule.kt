package com.example.myvoozkotlin.groupOfUser.di

import com.example.myvoozkotlin.groupOfUser.api.GroupOfUserApi
import com.example.myvoozkotlin.data.db.DbUtils
import com.example.myvoozkotlin.groupOfUser.data.*
import com.example.myvoozkotlin.groupOfUser.domain.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object GroupOfUserModule {

    @Provides
    fun provideCreateGroupOfUserUseCase(groupOfUserRepository: GroupOfUserRepository): CreateGroupOfUserUseCase {
        return CreateGroupOfUserUseCaseImpl(groupOfUserRepository)
    }

    @Provides
    fun provideLogoutGroupOfUserUseCase(groupOfUserRepository: GroupOfUserRepository): LogoutGroupOfUserUseCase {
        return LogoutGroupOfUserUseCaseImpl(groupOfUserRepository)
    }

    @Provides
    fun provideInviteGroupOfUserUseCase(groupOfUserRepository: GroupOfUserRepository): InviteGroupOfUserUseCase {
        return InviteGroupOfUserUseCaseImpl(groupOfUserRepository)
    }

    @Provides
    fun provideNameGroupOfUserUseCase(groupOfUserRepository: GroupOfUserRepository): ChangeNameGroupOfUserUseCase {
        return ChangeNameGroupOfUserUseCaseImpl(groupOfUserRepository)
    }

    @Provides
    fun provideUserListGroupOfUserUseCase(groupOfUserRepository: GroupOfUserRepository): UserListGroupOfUserUseCase {
        return UserListGroupOfUserUseCaseImpl(groupOfUserRepository)
    }

    @Provides
    fun provideGetEntryLinkGroupOfUserUseCase(groupOfUserRepository: GroupOfUserRepository): GetEntryLinkGroupOfUserUseCase {
        return GetEntryLinkGroupOfUserUseCaseImpl(groupOfUserRepository)
    }

    @Provides
    fun provideUpdateEntryLinkGroupOfUserUseCase(groupOfUserRepository: GroupOfUserRepository): UpdateEntryLinkGroupOfUserUseCase {
        return UpdateEntryLinkGroupOfUserUseCaseImpl(groupOfUserRepository)
    }

    @Provides
    fun provideLockEntryLinkGroupOfUserUseCase(groupOfUserRepository: GroupOfUserRepository): LockEntryLinkGroupOfUserUseCase {
        return LockEntryLinkGroupOfUserUseCaseImpl(groupOfUserRepository)
    }

    @Provides
    fun provideMakeHeadGroupOfUserUseCase(groupOfUserRepository: GroupOfUserRepository): MakeHeadGroupOfUserUseCase {
        return MakeHeadGroupOfUserUseCaseImpl(groupOfUserRepository)
    }

    @Provides
    fun provideRemoveUserGroupOfUserUseCase(groupOfUserRepository: GroupOfUserRepository): RemoveUserGroupOfUserUseCase {
        return RemoveUserGroupOfUserUseCaseImpl(groupOfUserRepository)
    }

    @Provides
    fun provideChangeIdGroupGroupOfUserUseCase(groupOfUserRepository: GroupOfUserRepository): ChangeIdGroupGroupOfUserUseCase {
        return ChangeIdGroupGroupOfUserUseCaseImpl(groupOfUserRepository)
    }

    @Provides
    fun provideGetGroupOfUserUseCase(groupOfUserRepository: GroupOfUserRepository): GetGroupOfUserUseCase {
        return GetGroupOfUserUseCaseImpl(groupOfUserRepository)
    }

    @Provides
    fun provideGroupOfUserRepository(groupOfUserApi: GroupOfUserApi, dbUtils: DbUtils) : GroupOfUserRepository {
        return GroupOfUserRepositoryImpl(groupOfUserApi, dbUtils)
    }
}