package com.ken.myapplication.di

import android.app.Application
import androidx.room.Room
import com.ken.myapplication.room.SavedUserDatabase
import com.ken.myapplication.room.SavedUserRepository
import com.ken.myapplication.room.SavedUserRepositoryImpli
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SavedUserModule {

    @Provides
    @Singleton
    fun provideSavedUserDatabase(app : Application) : SavedUserDatabase{
        return Room.databaseBuilder(
            app,
            SavedUserDatabase::class.java,
            "saved_users"
        ).build()
    }

    @Provides
    @Singleton
    fun provideSavedUserRepository(savedUserDatabase: SavedUserDatabase) : SavedUserRepository{
        return SavedUserRepositoryImpli(savedUserDatabase)
    }
}