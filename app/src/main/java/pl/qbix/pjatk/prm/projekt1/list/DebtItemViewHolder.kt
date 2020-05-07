package pl.qbix.pjatk.prm.projekt1.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.debt_list_item_view.view.*
import pl.qbix.pjatk.prm.projekt1.persistence.DebtInfo

class DebtItemViewHolder(view:View): RecyclerView.ViewHolder(view) {
    fun refreshData(data: DebtInfo){
        itemView.txtName.text = data.debtName
        itemView.txtAmount.text = data.amount.toString()
    }
}