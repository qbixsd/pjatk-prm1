package pl.qbix.pjatk.prm.projekt1

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import pl.qbix.pjatk.prm.projekt1.edit.EditActivity
import pl.qbix.pjatk.prm.projekt1.list.ConfirmationDialog
import pl.qbix.pjatk.prm.projekt1.list.DebtListAdapter
import pl.qbix.pjatk.prm.projekt1.persistence.Database
import kotlin.concurrent.thread

class MainActivity() : AppCompatActivity() {

    val db by lazy {
        Database.getInstance(applicationContext).database
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        thread {
            val values = db.debts().findAll()
            val totalDebt = values
                .map { value -> value.amount }
                .sum()
            val debtListAdapter = DebtListAdapter(values, this::delete, this::edit)
            runOnUiThread {
                recycler.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = debtListAdapter
                }
                lblTotalAmount.text = string(R.string.totalAmount).format(totalDebt)
            }
        }
    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        refreshList()
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    private fun refreshList() {
        thread {
            val values = db.debts().findAll()
            val totalDebt = values
                .map { value -> value.amount }
                .sum()
            val debtListAdapter = DebtListAdapter(values, this::delete, this::edit)
            runOnUiThread {
                recycler.swapAdapter(debtListAdapter, false)
                lblTotalAmount.text = string(R.string.totalAmount).format(totalDebt)
            }
        }
    }

    fun newDebt(view: View) {
        val intent = Intent(this, EditActivity::class.java)
        startActivity(intent)
    }

    fun edit(id: Int) {
        val intent = Intent(this, EditActivity::class.java)
            .apply { putExtra("debtId", id) }
        startActivity(intent)
    }

    fun delete(id: Int): Boolean {
        val deleteFunction = {
            db.debts().deleteById(id)
            refreshList()
        }
        ConfirmationDialog(deleteFunction).apply { isCancelable = true }
            .show(supportFragmentManager, "myDialog")
        return true;
    }

    fun string(id: Int): String {
        return applicationContext.resources.getString(id)
    }
}
