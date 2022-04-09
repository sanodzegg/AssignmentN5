package task5.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "run_range") val metersRun: Double,
    @ColumnInfo(name = "swim_range") val metersSwum: Double,
    @ColumnInfo(name = "calorie") val caloriesBurnt: Double,
)
