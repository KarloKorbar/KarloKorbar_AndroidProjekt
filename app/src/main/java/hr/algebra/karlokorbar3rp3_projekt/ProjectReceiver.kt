package hr.algebra.karlokorbar3rp3_projekt

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hr.algebra.karlokorbar3rp3_projekt.framework.startActivity

class ProjectReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        context.startActivity<HostActivity>()
    }
}