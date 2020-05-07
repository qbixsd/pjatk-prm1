package pl.qbix.pjatk.prm.projekt1.edit

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.debt_list_item_view.*
import pl.qbix.pjatk.prm.projekt1.R

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
    }

    fun save(view: View) {
        setResult(0, Intent().apply {
            putExtra("name", txtName.text.toString())
            putExtra("amount", txtAmount.text.toString().toFloat())
        })
        finish()
    }
}
