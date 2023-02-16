package ru.dezerom.interdiffer.app.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.dezerom.interdiffer.data.data_base.TheDatabase
import ru.dezerom.interdiffer.data.data_base.dao.UserSocietyRelationsDao
import ru.dezerom.interdiffer.data.data_base.dao.VkSocietiesDao
import ru.dezerom.interdiffer.data.data_base.dao.VkUsersDao

@Module
@InstallIn(SingletonComponent::class)
class DatabaseProvider {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): TheDatabase =
        Room.databaseBuilder(context, TheDatabase::class.java, "theDb")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideVkUsersDao(databaseImpl: TheDatabase): VkUsersDao =
        databaseImpl.getVkUsersDao()

    @Provides
    fun provideVkSocietiesDao(databaseImpl: TheDatabase): VkSocietiesDao =
        databaseImpl.getSocietiesDao()

    @Provides
    fun provideUserSocietyRelationsDao(databaseImpl: TheDatabase): UserSocietyRelationsDao =
        databaseImpl.getUserSocietyRelationsDao()

}
