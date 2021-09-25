package com.example.myvoozkotlin.data.di

import android.content.Context
import com.example.myvoozkotlin.data.db.DbUtils
import com.example.myvoozkotlin.data.db.MyMigration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideDatabaseInstance(@ApplicationContext appContext: Context): Realm {
        if (Realm.getDefaultConfiguration() == null) {
            Realm.init(appContext)
            val config = RealmConfiguration.Builder()
                    .name("myvooz.realm")
                    .schemaVersion(0)
                .migration(MyMigration())
                .deleteRealmIfMigrationNeeded()
                    .allowWritesOnUiThread(true)
                    .build()
            Realm.setDefaultConfiguration(config)
        }
        return Realm.getDefaultInstance()
    }

    @Singleton
    @Provides
    fun provideDbUtils(realm: Realm): DbUtils {
        return DbUtils(realm)
    }
}