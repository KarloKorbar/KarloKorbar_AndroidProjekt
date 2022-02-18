package hr.algebra.karlokorbar3rp3_projekt

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import hr.algebra.karlokorbar3rp3_projekt.api.CocktailFetcher
import hr.algebra.karlokorbar3rp3_projekt.framework.sendBroadcast
import hr.algebra.karlokorbar3rp3_projekt.framework.setBooleanPreference


private const val JOB_ID = 1

class ProjectService : JobIntentService() {
    override fun onHandleWork(intent: Intent) {

        CocktailFetcher(this).fetchItems()

    }

    companion object {
        fun enqueue(context: Context, intent: Intent) {
            enqueueWork(context, ProjectService::class.java, JOB_ID, intent)
        }
    }
}