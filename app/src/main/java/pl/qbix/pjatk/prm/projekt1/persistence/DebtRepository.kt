package pl.qbix.pjatk.prm.projekt1.persistence

import pl.qbix.pjatk.prm.projekt1.DebtInfo

interface DebtRepository {
    fun findAll(): List<DebtInfo>
}