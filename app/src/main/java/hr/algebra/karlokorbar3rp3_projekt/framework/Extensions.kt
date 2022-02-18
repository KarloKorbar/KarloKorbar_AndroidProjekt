package hr.algebra.karlokorbar3rp3_projekt.framework

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.getSystemService
import androidx.preference.PreferenceManager
import hr.algebra.karlokorbar3rp3_projekt.COCKTAIL_PROVIDER_URI
import hr.algebra.karlokorbar3rp3_projekt.model.Cocktail

fun View.startAnimation(animationId: Int) =
    startAnimation(AnimationUtils.loadAnimation(context, animationId))

inline fun <reified T : Activity> Context.startActivity() =
    startActivity(Intent(this, T::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    })

inline fun<reified T : Activity> Context.startActivity(key: String, value: Int)
        = startActivity(Intent(this, T::class.java).apply {
    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    putExtra(key, value)
})

inline fun <reified T : BroadcastReceiver> Context.sendBroadcast() =
    sendBroadcast(android.content.Intent(this, T::class.java))

fun Context.setBooleanPreference(key: String, value: Boolean) =
    PreferenceManager.getDefaultSharedPreferences(this)
        .edit()
        .putBoolean(key, value)
        .apply()

fun Context.getBooleanPreference(key: String) = PreferenceManager.getDefaultSharedPreferences(this)
    .getBoolean(key, false)

fun Context.isOnline(): Boolean {
    val connectivityManager = getSystemService<ConnectivityManager>();
    connectivityManager?.activeNetwork?.let { network ->
        connectivityManager.getNetworkCapabilities(network)?.let { networkCapabilities ->
            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        }
    }
    return false
}

fun callDelayed(delay: Long, function: Runnable) {
    Handler(Looper.getMainLooper()).postDelayed(
        function,
        delay
    )
}

fun Context.fetchCocktails(): MutableList<Cocktail> {
    val cocktails = mutableListOf<Cocktail>()

    val cursor = contentResolver?.query(
        COCKTAIL_PROVIDER_URI,
        null,
        null,
        null,
        null
    )

    while (cursor != null && cursor.moveToNext()) {
        cocktails.add(
            Cocktail(
                cursor.getLong(cursor.getColumnIndexOrThrow(Cocktail::_id.name)),
                cursor.getString(cursor.getColumnIndexOrThrow(Cocktail::title.name)),
                cursor.getString(cursor.getColumnIndexOrThrow(Cocktail::picturePath.name)),

                cursor.getString(cursor.getColumnIndexOrThrow(Cocktail::strAlcoholic.name)),
                cursor.getString(cursor.getColumnIndexOrThrow(Cocktail::strInstructions.name)),
                cursor.getString(cursor.getColumnIndexOrThrow(Cocktail::strIngredients.name)),
                cursor.getInt(cursor.getColumnIndexOrThrow(Cocktail::liked.name)) == 1

                //TODO don't forget about me
            )
        )
    }

    return cocktails
}