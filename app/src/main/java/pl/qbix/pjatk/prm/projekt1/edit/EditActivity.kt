package pl.qbix.pjatk.prm.projekt1.edit

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.debt_list_item_view.*
import pl.qbix.pjatk.prm.projekt1.R
import pl.qbix.pjatk.prm.projekt1.persistence.Database
import pl.qbix.pjatk.prm.projekt1.persistence.DebtInfo
import kotlin.concurrent.thread

class EditActivity : AppCompatActivity() {
    val db by lazy {
        Database.getInstance(applicationContext).database
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
    }

    fun save(view: View) {
        thread {
            val name = txtName.text.toString()
            val amount = txtAmount.text.toString().toFloat()
            db.debts().save(DebtInfo(0, name, amount))
            finish()
        }
    }
}
