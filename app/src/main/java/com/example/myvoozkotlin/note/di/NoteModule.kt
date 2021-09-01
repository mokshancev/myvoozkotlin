package com.example.myvoozkotlin.note.di

import com.example.myvoozkotlin.data.api.NoteApi
import com.example.myvoozkotlin.data.api.SearchApi
import com.example.myvoozkotlin.note.data.AddNoteUseCaseImpl
import com.example.myvoozkotlin.note.data.CompletedNoteUseCaseImpl
import com.example.myvoozkotlin.note.data.NoteListUseCaseImpl
import com.example.myvoozkotlin.note.data.NoteRepositoryImpl
import com.example.myvoozkotlin.note.domain.AddNoteUseCase
import com.example.myvoozkotlin.note.domain.CompletedNoteUseCase
import com.example.myvoozkotlin.note.domain.NoteListUseCase
import com.example.myvoozkotlin.note.domain.NoteRepository
import com.example.myvoozkotlin.search.data.SearchGroupUseCaseImpl
import com.example.myvoozkotlin.search.data.SearchRepositoryImpl
import com.example.myvoozkotlin.search.data.SearchUniversityUseCaseImpl
import com.example.myvoozkotlin.search.domain.SearchGroupUseCase
import com.example.myvoozkotlin.search.domain.SearchRepository
import com.example.myvoozkotlin.search.domain.SearchUniversityUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object NoteModule {

    @Provides
    fun provideNoteListUseCase(noteRepository: NoteRepository): NoteListUseCase {
        return NoteListUseCaseImpl(noteRepository)
    }

    @Provides
    fun provideAddNoteUseCase(noteRepository: NoteRepository): AddNoteUseCase {
        return AddNoteUseCaseImpl(noteRepository)
    }

    @Provides
    fun provideCompletedNoteUseCase(noteRepository: NoteRepository): CompletedNoteUseCase {
        return CompletedNoteUseCaseImpl(noteRepository)
    }

    @Provides
    fun provideNoteRepository(noteApi: NoteApi) : NoteRepository {
        return NoteRepositoryImpl(noteApi)
    }
}