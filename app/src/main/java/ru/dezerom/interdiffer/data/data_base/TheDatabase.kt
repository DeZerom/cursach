package ru.dezerom.interdiffer.data.data_base

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.dezerom.interdiffer.data.data_base.dao.VkUsersDao
import ru.dezerom.interdiffer.data.data_base.type_converters.BooleanConverter
import ru.dezerom.interdiffer.data.models.SocietyDataModel
import ru.dezerom.interdiffer.data.models.VkUserDataModel

@Database(
    entities = [VkUserDataModel::class, SocietyDataModel::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(1, 2)
    ]
)
@TypeConverters(
    value = [BooleanConverter::class]
)
abstract class TheDatabase: RoomDatabase() {
    abstract fun getVkUsersDao(): VkUsersDao
}
