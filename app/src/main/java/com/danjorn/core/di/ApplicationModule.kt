package com.danjorn.core.di

import android.app.Application
import android.content.Context
import com.danjorn.features.chat.ChatRepository
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(val application: Application) {

    @Provides @Singleton fun provideApplicationContext(): Context = application

    @Provides @Singleton fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance()

    @Provides @Singleton fun provideChatsRepository(dataSource: ChatRepository.Database): ChatRepository = dataSource
}