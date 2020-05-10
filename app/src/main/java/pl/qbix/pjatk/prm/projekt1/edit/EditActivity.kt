package pl.qbix.pjatk.prm.projekt1.edit

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.debt_list_item_view.*
import pl.qbix.pjatk.prm.projekt1.R
import pl.qbix.pjatk.prm.projekt1.list.ConfirmationDialog
import pl.qbix.pjatk.prm.projekt1.persistence.Database
import pl.qbix.pjatk.prm.projekt1.persistence.DebtInfo
import pl.qbix.pjatk.prm.projekt1.simulation.SimulationActivity
import kotlin.concurrent.thread

class EditActivity : AppCompatActivity() {
    val db by lazy {
        Database.getInstance(applicationContext).database
    }

    var debtInfo: DebtInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        intent?.let {
            val id = it.getIntExtra("debtId", 0)
            loadData(id)
        }
    }

    private fun loadData(id: Int) {
        if (id == 0) {
            return;
        }
        thread {
            debtInfo = db.debts().findById(id)[0]
            runOnUiThread {
                txtName.text = debtInfo?.debtName
                txtAmount.text = debtInfo?.amount.toString()
            }
        }
    }

    fun simulate(view: View) {
        val intent = Intent(this, SimulationActivity::class.java).apply {
            putExtra("debtId", debtInfo?.id)
        }
        startActivity(intent)
    }

    fun save(view: View) {
        thread {
            val name = txtName.text.toString()
            val amount = txtAmount.text.toString().toFloat()
            db.debts().save(DebtInfo(0, name, amount))
            finish()
        }
    }

    fun cancel(view: View) {
        if (debtInfo == null) {
            finish()
        } else {
            ConfirmationDialog { finish() }
                .show(supportFragmentManager, "confirmationDialog")
        }
    }

    fun send(view: View) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            setType("text/plain")
            putExtra(Intent.EXTRA_TEXT, string(R.string.debtReminder).format(debtInfo?.amount))
        }
        startActivity(Intent.createChooser(intent, string(R.string.debtReminderTitle)));
    }

    fun string(id: Int): String {
        return applicationContext.resources.getString(id);
    }
}
