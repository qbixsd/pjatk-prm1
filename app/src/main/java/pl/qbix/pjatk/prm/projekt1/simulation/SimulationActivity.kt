package pl.qbix.pjatk.prm.projekt1.simulation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_simulation.*
import pl.qbix.pjatk.prm.projekt1.R
import pl.qbix.pjatk.prm.projekt1.persistence.Database
import pl.qbix.pjatk.prm.projekt1.persistence.DebtInfo
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate
import kotlin.concurrent.thread

class SimulationActivity : AppCompatActivity() {
    val db by lazy {
        Database.getInstance(applicationContext).database
    }

    val timer: Timer = Timer()
    var isRunning: Boolean = false
    var totalInterest: Float = 0F
    var debtInfo: DebtInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulation)
        intent?.let {
            val debtId = it.getIntExtra("debtId", 0)
            loadData(debtId)
        }
    }

    fun loadData(id: Int) {
        thread {
            debtInfo = db.debts().findById(id)[0]
            lblDebtNameVal.text = debtInfo?.debtName
            lblAmountValue.text = debtInfo?.amount.toString()
        }
    }

    fun run(view: View) {
        if (isRunning) {
            timer.cancel()
            lblStatus.text = string(R.string.simAborted)
            isRunning = false
        } else {
            isRunning = true
            val rate = txtRate.text.toString().toFloat()
            val interest = txtInterest.text.toString().toFloat()
            lblStatus.text = string(R.string.simRunning)
            timer.scheduleAtFixedRate(0, 1000) {
                val amount = debtInfo?.amount ?: 0F
                var newAmount: Float = (amount - rate)
                if (newAmount <= 0) {
                    newAmount = 0F
                    runOnUiThread {
                        lblStatus.text = string(R.string.simEnded).format(totalInterest)
                    }
                    timer.cancel()
                } else {
                    val interests: Float = (newAmount * interest) / 100F
                    totalInterest += interests
                    newAmount += interests
                }
                debtInfo?.amount = newAmount
                runOnUiThread {
                    lblAmountValue.text = debtInfo?.amount.toString()
                }
            }
        }
    }

    fun string(id: Int): String {
        return applicationContext.resources.getString(id)
    }
}
