package hr.algebra.karlokorbar3rp3_projekt

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import hr.algebra.karlokorbar3rp3_projekt.dao.CocktailRepository
import hr.algebra.karlokorbar3rp3_projekt.dao.getCocktailRepository
import hr.algebra.karlokorbar3rp3_projekt.model.Cocktail

private const val AUTHORITY = "hr.algebra.karlokorbar3rp3_projekt.api.provider"
private const val PATH = "items"

private const val ITEMS = 10
private const val ITEM_ID = 20

private val URI_MATCHER = with(UriMatcher(UriMatcher.NO_MATCH)) {
    addURI(AUTHORITY, PATH, ITEMS)
    addURI(AUTHORITY, "$PATH/#", ITEM_ID)
    this
}
val COCKTAIL_PROVIDER_URI = Uri.parse("content://$AUTHORITY/$PATH")

class CocktailProvider : ContentProvider() {

    private lateinit var repository: CocktailRepository

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        when (URI_MATCHER.match(uri)) {
            ITEMS -> return repository.delete(selection, selectionArgs)
            ITEM_ID -> {
                uri.lastPathSegment?.let {
                    return repository.delete("${Cocktail::_id.name}=?", arrayOf(it))
                }
            }
        }
        throw IllegalArgumentException("Wrong uri")
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        var id = repository.insert(values)
        return ContentUris.withAppendedId(COCKTAIL_PROVIDER_URI, id)
    }

    override fun onCreate(): Boolean {
        repository = getCocktailRepository(context)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? = repository.query(
        projection,
        selection,
        selectionArgs,
        sortOrder
    )

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {

        when(URI_MATCHER.match(uri)) {
            ITEMS -> return repository.update(values, selection, selectionArgs)
            ITEM_ID -> {
                uri.lastPathSegment?.let {
                    return repository.update(values, "${Cocktail::_id.name}=?", arrayOf(it))
                }
            }
        }
        throw IllegalArgumentException("Wrong uri")
    }
}