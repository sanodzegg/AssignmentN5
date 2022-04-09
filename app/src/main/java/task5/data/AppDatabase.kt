package task5.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDB : RoomDatabase() {
    abstract fun userDao(): UserDao
}
