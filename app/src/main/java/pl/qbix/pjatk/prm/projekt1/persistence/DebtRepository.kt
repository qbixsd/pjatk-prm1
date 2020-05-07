package pl.qbix.pjatk.prm.projekt1.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DebtRepository {
    @Query("SELECT * FROM DebtInfo")
    fun findAll(): List<DebtInfo>

    @Insert
    fun save(debtInfo: DebtInfo)
}