package pl.qbix.pjatk.prm.projekt1.persistence

import android.content.Context
import androidx.room.Room

class Database private constructor(val context: Context) {
    companion object {
        private var database: Database? = null
        fun getInstance(context: Context): Database {
            return database ?: Database(context).also { database = it }
        }
    }

    val database by lazy {
        Room.databaseBuilder(context, DebtDb::class.java, "debt.db").build()
    }
}