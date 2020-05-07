package pl.qbix.pjatk.prm.projekt1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import pl.qbix.pjatk.prm.projekt1.edit.EditActivity
import pl.qbix.pjatk.prm.projekt1.list.DebtListAdapter
import pl.qbix.pjatk.prm.projekt1.persistence.Database
import pl.qbix.pjatk.prm.projekt1.persistence.DebtInfo
import kotlin.concurrent.thread

class MainActivity() : AppCompatActivity() {
    val debtListAdapter: DebtListAdapter = DebtListAdapter(emptyList());

    val db by lazy {
        Database.getInstance(applicationContext).database
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        thread {
            val values = db.debts().findAll()
            debtListAdapter.data = values
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
        thread {
            if (requestCode == 1) {
                val name = data?.getStringExtra("name") ?: ""
                val amount = data?.getFloatExtra("amount", 0F) ?: 0F
                db.debts().save(DebtInfo(0, name, amount))
                debtListAdapter.data = db.debts().findAll()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
