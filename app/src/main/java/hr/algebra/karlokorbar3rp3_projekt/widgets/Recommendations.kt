package hr.algebra.karlokorbar3rp3_projekt.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.graphics.Bitmap
import android.widget.RemoteViews
import hr.algebra.karlokorbar3rp3_projekt.framework.fetchCocktails
import hr.algebra.karlokorbar3rp3_projekt.model.Cocktail
import java.io.File
import kotlin.random.Random


import android.graphics.BitmapFactory

import android.app.PendingIntent
import android.content.Intent
import android.content.ComponentName
import hr.algebra.karlokorbar3rp3_projekt.R.*


/**
 * Implementation of App Widget functionality.
 */

lateinit var cocktails: MutableList<Cocktail>

class Recommendations : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
            //handle defining event handlers
        }

    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
        cocktails = context.fetchCocktails()
    }

}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val views = RemoteViews(context.packageName, layout.recommendations)
    constructViews(views, cocktails, context)

    //setup the button
    val intent = Intent(context, Recommendations::class.java)

    intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
    val idArray = intArrayOf(appWidgetId)
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);

    val pendingIntent = PendingIntent.getBroadcast(
        context, appWidgetId, intent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(id.wbtnNext, pendingIntent)

    appWidgetManager.updateAppWidget(appWidgetId, views)
}

fun constructViews(views: RemoteViews, cocktails: MutableList<Cocktail>, context: Context) {
    val cocktail: Cocktail = hr.algebra.karlokorbar3rp3_projekt.widgets.cocktails[Random.nextInt(
        0,
        hr.algebra.karlokorbar3rp3_projekt.widgets.cocktails.size
    )]
    val widgetText = cocktail.title


    views.setTextViewText(id.appwidget_text, widgetText)

    //picasso ne smije biti u main thread pa sam ovako dobio bitmap :\

    val image: File = File(cocktail.picturePath)
    val bmOptions = BitmapFactory.Options()
    var bitmap = BitmapFactory.decodeFile(image.absolutePath, bmOptions)
    bitmap = Bitmap.createScaledBitmap(
        bitmap!!,
        70,
        70,
        true
    )

    views.setImageViewBitmap(id.wivItem, bitmap)
}
