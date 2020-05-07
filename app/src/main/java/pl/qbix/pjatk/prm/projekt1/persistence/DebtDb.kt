package pl.qbix.pjatk.prm.projekt1.persistence

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [DebtInfo::class])
abstract class DebtDb : RoomDatabase() {
    abstract fun debts(): DebtRepository
}