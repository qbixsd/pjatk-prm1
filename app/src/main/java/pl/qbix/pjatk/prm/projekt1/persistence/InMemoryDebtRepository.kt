package pl.qbix.pjatk.prm.projekt1.persistence

import pl.qbix.pjatk.prm.projekt1.DebtInfo

class InMemoryDebtRepository : DebtRepository {
    val debts: List<DebtInfo> = listOf(
        DebtInfo("Janek", 123F),
        DebtInfo("Staszek", 1.21F)
    )

    override fun findAll(): List<DebtInfo> {
        return debts
    }
}