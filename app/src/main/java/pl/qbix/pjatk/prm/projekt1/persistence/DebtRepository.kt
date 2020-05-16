package pl.qbix.pjatk.prm.projekt1.persistence

import android.database.Cursor
import androidx.room.Dao
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

    fun save(debtInfo: DebtInfo) {
        if (debtInfo.id == 0) {
            persist(debtInfo)
        } else {
            update(debtInfo.debtName, debtInfo.amount, debtInfo.id)
        }
    }

    @Query("UPDATE DebtInfo SET debtName=:debtName, amount = :amount where id=:id")
    fun update(debtName: String, amount: Float, id: Int)

    @Insert
    fun persist(debtInfo: DebtInfo)

    @Query("DELETE FROM DebtInfo where id = :id")
    fun deleteById(id: Int)
}