package pl.qbix.pjatk.prm.projekt1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import pl.qbix.pjatk.prm.projekt1.edit.EditActivity
import pl.qbix.pjatk.prm.projekt1.list.DebtListAdapter
import pl.qbix.pjatk.prm.projekt1.list.DeleteConfirmationDialog
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
            val debtListAdapter = DebtListAdapter(values, this::delete)
            runOnUiThread {
                recycler.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = debtListAdapter
                }
            }
        }
    }

    fun open(view: View) {
        val intent = Intent(this, EditActivity::class.java)
        this.startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            refreshList()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun refreshList() {
        thread {
            val values = db.debts().findAll()
            val debtListAdapter = DebtListAdapter(values, this::delete)
            runOnUiThread {
                recycler.swapAdapter(debtListAdapter, false)
            }
        }
    }

    fun delete(id: Int) {
        val deleteFunction = {
                db.debts().deleteById(id)
                refreshList()
        }
        DeleteConfirmationDialog(deleteFunction).apply { isCancelable = true }
            .show(supportFragmentManager, "myDialog")

        println("test")
    }
}
