package pl.qbix.pjatk.prm.projekt1.list

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import pl.qbix.pjatk.prm.projekt1.R
import kotlin.concurrent.thread

class DeleteConfirmationDialog(val delete: () -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).apply {
            setMessage(R.string.deleteConfirm)
            setPositiveButton(R.string.confirm, DialogInterface.OnClickListener { dialog, which ->
                thread {
                    delete()
                }
            })
            setNegativeButton(R.string.decline, { dialog, which -> })
        }.create()
    }
}