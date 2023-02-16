package ru.dezerom.interdiffer.data.data_base

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.dezerom.interdiffer.data.data_base.dao.UserSocietyRelationsDao
import ru.dezerom.interdiffer.data.data_base.dao.VkSocietiesDao
import ru.dezerom.interdiffer.data.data_base.dao.VkUsersDao
import ru.dezerom.interdiffer.data.data_base.type_converters.BooleanConverter
import ru.dezerom.interdiffer.data.models.UserSocietyRelationDataModel
import ru.dezerom.interdiffer.data.models.VkSocietyDataModel
import ru.dezerom.interdiffer.data.models.VkUserDataModel

@Database(
    entities = [
        VkUserDataModel::class,
        VkSocietyDataModel::class,
        UserSocietyRelationDataModel::class
    ],
    version = 3,
    exportSchema = true,
    autoMigrations = [
    ]
)
@TypeConverters(
    value = [BooleanConverter::class]
)
abstract class TheDatabase: RoomDatabase() {
    abstract fun getVkUsersDao(): VkUsersDao
    abstract fun getSocietiesDao(): VkSocietiesDao
    abstract fun getUserSocietyRelationsDao(): UserSocietyRelationsDao
}
