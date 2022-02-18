package hr.algebra.karlokorbar3rp3_projekt.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import hr.algebra.karlokorbar3rp3_projekt.model.Cocktail

private const val DB_NAME = "cocktails.db"
private const val DB_VERSION = 1

private const val TABLE = "cocktails"

private val CREATE = "create table $TABLE(" +
        "${Cocktail::_id.name} integer primary key autoincrement, " +
        "${Cocktail::title.name} text not null, " +
        "${Cocktail::picturePath.name} text not null, " +
        "${Cocktail::strAlcoholic.name} text not null, " +
        "${Cocktail::strInstructions.name} text not null, " +
        "${Cocktail::strIngredients.name} text not null, " +
        "${Cocktail::liked.name} integer not null " +
        ")"
//TODO add more later

private const val DROP = "drop table $TABLE"

class CocktailSqlHelper(
    context: Context?,
) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION), CocktailRepository {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP)
        onCreate(db)
    }

    override fun delete(selection: String?, selectionArgs: Array<String>?): Int =
        writableDatabase.delete(TABLE, selection, selectionArgs)

    override fun update(
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int = writableDatabase.update(TABLE, values, selection, selectionArgs)

    override fun insert(values: ContentValues?): Long = writableDatabase.insert(TABLE, null, values)

    override fun query(
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor = readableDatabase.query(
        TABLE,
        projection,
        selection,
        selectionArgs,
        null,
        null,
        sortOrder
    )
}