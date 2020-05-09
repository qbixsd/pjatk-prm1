package pl.qbix.pjatk.prm.projekt1.simulation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_simulation.*
import pl.qbix.pjatk.prm.projekt1.R
import pl.qbix.pjatk.prm.projekt1.persistence.Database

class SimulationActivity : AppCompatActivity() {
    val db by lazy {
        Database.getInstance(applicationContext).database
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulation)
        intent?.let{
            val tekst = it.getIntExtra("debtId", 0)
            lblDebtName.text = tekst.toString()
        }
    }
}
