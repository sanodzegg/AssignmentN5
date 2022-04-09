package task5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import ge.edu.btu.task5.R
import task5.data.AppDB
import task5.data.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var metersRun: EditText
    private lateinit var metersSwum: EditText
    private lateinit var caloriesBurnt: EditText
    private lateinit var stats: TextView
    private lateinit var save: Button
    private lateinit var database: AppDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        metersRun = findViewById(R.id.metersRun)
        metersSwum = findViewById(R.id.metersSwum)
        caloriesBurnt = findViewById(R.id.caloriesBurnt)
        stats = findViewById(R.id.stats)
        save = findViewById(R.id.save)

        database = Room.databaseBuilder(
            applicationContext,
            AppDB::class.java,
            "user_db"
        ).build()

        CoroutineScope(Dispatchers.IO).launch {
            setData()
        }

        save.setOnClickListener {
            if (validation()) {
                Toast.makeText(this, "Every field needs to be filled", Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    database.userDao().insert(
                        User(
                            metersRun = metersRun.toDouble(),
                            metersSwum = metersSwum.toDouble(),
                            caloriesBurnt = caloriesBurnt.toDouble()
                        )
                    )
                    clear()
                    setData()
                }
            }
        }
    }

    private suspend fun setData() {
        val userDao = database.userDao()
        val totalDistance = userDao.total()

        val avgRun = userDao.avgRun().toString()
        val avgSwim = userDao.avgSwum().toString()
        val avgcaloriesBurnt = userDao.avgCal().toString()

        withContext(Dispatchers.Main) {
            stats.text = getString(
                R.string.average_stats,
                avgRun,
                avgSwim,
                avgcaloriesBurnt,
                totalDistance.toString()
            )
        }
    }

    private fun clear() {
        metersRun.setText("")
        metersSwum.setText("")
        caloriesBurnt.setText("")
    }

    private fun EditText.toDouble(): Double {
        return this.text.toString().toDouble()
    }

    private fun validation(): Boolean {
        return metersRun.text.toString().isEmpty() || metersSwum.text.toString()
            .isEmpty() || caloriesBurnt.text.toString().isEmpty()
    }
}
