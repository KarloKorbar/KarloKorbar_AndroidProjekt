package hr.algebra.karlokorbar3rp3_projekt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hr.algebra.karlokorbar3rp3_projekt.databinding.ActivityCocktailPagerBinding
import hr.algebra.karlokorbar3rp3_projekt.framework.fetchCocktails
import hr.algebra.karlokorbar3rp3_projekt.model.Cocktail

const val COCKTAIL_POSITION = "hr.algebra.karlokorbar3rp3.item_position"

class CocktailPagerActivity : AppCompatActivity() {

    private lateinit var cocktails: MutableList<Cocktail>
    private lateinit var binding: ActivityCocktailPagerBinding

    private var cocktailPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCocktailPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPager()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun initPager() {
        cocktails = fetchCocktails()
        cocktailPosition = intent.getIntExtra(COCKTAIL_POSITION, 0)
        binding.viewPager.adapter = CocktailPagerAdapter(this, cocktails)
        binding.viewPager.currentItem = cocktailPosition
    }
}