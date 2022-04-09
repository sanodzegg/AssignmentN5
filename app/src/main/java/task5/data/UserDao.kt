package task5.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    fun insert(user: User)

    @Query("SELECT AVG(run_range) FROM user")
    fun avgRun(): Double

    @Query("SELECT AVG(swim_range) FROM user")
    fun avgSwum(): Double

    @Query("SELECT AVG(calorie) FROM user")
    fun avgCal(): Double

    @Query("SELECT SUM(run_range) FROM user")
    fun total(): Double

}
