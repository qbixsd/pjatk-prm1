package pl.qbix.pjatk.prm.projekt1.persistence

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DebtRepository {
    @Query("SELECT * FROM DebtInfo")
    fun findAll(): List<DebtInfo>

    @Query("SELECT * FROM DebtInfo WHERE id = :id")
    fun findById(id: Int): List<DebtInfo>

    @Query("SELECT * FROM DebtInfo WHERE id = :id")
    fun findByIdCursor(id: Int): Cursor

    @Insert
    fun save(debtInfo: DebtInfo)

    @Query("DELETE FROM DebtInfo where id = :id")
    fun deleteById(id: Int)
}