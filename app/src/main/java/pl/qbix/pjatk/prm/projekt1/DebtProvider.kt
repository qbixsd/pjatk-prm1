package pl.qbix.pjatk.prm.projekt1

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.sqlite.db.SupportSQLiteQueryBuilder
import pl.qbix.pjatk.prm.projekt1.persistence.Database

class DebtProvider : ContentProvider() {

    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("pl.qbix.pjatk.prm.projekt1", "debt", 1)
        addURI("pl.qbix.pjatk.prm.projekt1", "debt/#", 2)
    }

    override fun getType(uri: Uri): String? = when (sUriMatcher.match(uri)) {
        1 -> "vnd.android.cursor.dir/vnd.pl.qbix.pjatk.prm.projekt1.debt"
        2 -> "vnd.android.cursor.item/vnd.pl.qbix.pjatk.prm.projekt1.debt"
        else -> throw IllegalArgumentException()
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val db = context?.applicationContext?.let {
            Database.getInstance(it).database
        } ?: throw RuntimeException("Cannot get application context")

        return when (sUriMatcher.match(uri)) {
            1 -> db.query(
                SupportSQLiteQueryBuilder.builder("DebtInfo")
                    .columns(projection)
                    .selection(selection, selectionArgs)
                    .orderBy(sortOrder)
                    .create()
            )
            2 -> {
                val id = ContentUris.parseId(uri)
                db.debts().findByIdCursor(id.toInt())
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun onCreate(): Boolean {
        //initialisation
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw NotImplementedError()
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        throw NotImplementedError()
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw NotImplementedError()
    }
}