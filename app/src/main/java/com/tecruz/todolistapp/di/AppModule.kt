package com.tecruz.todolistapp.di

import android.app.Application
import androidx.room.Room
import com.tecruz.todolistapp.data.TodoDatabase
import com.tecruz.todolistapp.data.TodoRepository
import com.tecruz.todolistapp.data.TodoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(
        app: Application
    ): TodoDatabase {
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            "todo_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(
        db: TodoDatabase
    ): TodoRepository {
        return TodoRepositoryImpl(db.todoDao)
    }
}