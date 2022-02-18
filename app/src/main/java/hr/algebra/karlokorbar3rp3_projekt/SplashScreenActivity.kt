package hr.algebra.karlokorbar3rp3_projekt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import hr.algebra.karlokorbar3rp3_projekt.databinding.ActivitySplashScreenBinding
import hr.algebra.karlokorbar3rp3_projekt.framework.*


private const val DELAY = 3000L
const val DATA_IMPORTED = "hr.algebra.KarloKorbar_projekt.data_imported"

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimations()
        redirect()
    }

    private fun startAnimations() {
        binding.tvSplash.startAnimation(R.anim.blink)
        binding.ivSplash.startAnimation(R.anim.hover)
    }

    private fun redirect() {
        //ako su podatci u bazi:
        //  redirect
        //u suprotnom:
        //  ako is online:
        //    pokreni servis za skidanje podataka
        //  ako nisi online:
        //    obavijesti korisnika i izidi iz aplikacije


        if (getBooleanPreference(DATA_IMPORTED)) {
            //redirect
            callDelayed(DELAY) { startActivity<HostActivity>() }
        } else {
            if (isOnline()) {
                //pokreni servis za skidanje podataka
                Intent(
                    this,
                    ProjectService::class.java
                ).apply { ProjectService.enqueue(this@SplashScreenActivity, this) }
            } else {
                //obavijesti korisnika i izadi iz aplikacije
                binding.tvSplash.text = getString(R.string.no_internet)
                callDelayed(DELAY) { finish() }
            }
        }
    }
}