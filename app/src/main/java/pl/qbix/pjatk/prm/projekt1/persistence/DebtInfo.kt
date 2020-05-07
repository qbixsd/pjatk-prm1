package pl.qbix.pjatk.prm.projekt1.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class DebtInfo(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var debtName: String,
    var amount: Float
)