package com.archico.core.di

import android.content.Context
import androidx.room.Room
import com.archico.core.data.source.local.room.RecipeDao
import com.archico.core.data.source.local.room.RecipeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    private val bytes = SQLiteDatabase.getBytes("archico".toCharArray())
    private val supportFactory = SupportFactory(bytes)

    @Provides
    fun provideRecipeDao(database: RecipeDatabase): RecipeDao =
        database.recipeDao()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): RecipeDatabase =
        Room.databaseBuilder(
            context,
            RecipeDatabase::class.java, "Recipe.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(supportFactory)
            .build()
}