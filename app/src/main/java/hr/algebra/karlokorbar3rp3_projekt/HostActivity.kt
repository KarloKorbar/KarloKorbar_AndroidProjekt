package hr.algebra.karlokorbar3rp3_projekt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import hr.algebra.karlokorbar3rp3_projekt.databinding.ActivityHostBinding

class HostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
        binding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initNavigation()
    }

    private fun initNavigation() {
        NavigationUI.setupWithNavController(
            binding.bottomNavigationView,
            Navigation.findNavController(this, R.id.navHostFragment),
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuExit -> {
                exitApp()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }


    private fun exitApp() {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.exit)
            setMessage(getString(R.string.really))
            setIcon(R.drawable.circle_info_solid)
            setCancelable(true)
            setNegativeButton(getString(R.string.cancel), null)
            setPositiveButton(getString(R.string.confirm)) { _, _ -> finish() }
            show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.host_menu, menu)
        return true
    }
}