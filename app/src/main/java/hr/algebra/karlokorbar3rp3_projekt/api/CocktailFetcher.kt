package hr.algebra.karlokorbar3rp3_projekt.api

import android.content.ContentValues
import android.content.Context
import android.util.Log
import hr.algebra.karlokorbar3rp3_projekt.COCKTAIL_PROVIDER_URI
import hr.algebra.karlokorbar3rp3_projekt.DATA_IMPORTED
import hr.algebra.karlokorbar3rp3_projekt.ProjectReceiver
import hr.algebra.karlokorbar3rp3_projekt.framework.sendBroadcast
import hr.algebra.karlokorbar3rp3_projekt.framework.setBooleanPreference
import hr.algebra.karlokorbar3rp3_projekt.handler.downloadImageAndStore
import hr.algebra.karlokorbar3rp3_projekt.model.Cocktail
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.function.Consumer

class CocktailFetcher(private val context: Context) {
    private var cocktailApi: CocktailApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        cocktailApi = retrofit.create(CocktailApi::class.java)
    }

    fun fetchItems() {
        val request = cocktailApi.fetchItems()

        request.enqueue(object : Callback<CocktailData> {
            override fun onResponse(call: Call<CocktailData>, response: Response<CocktailData>) {
                response.body()?.let {
                    populateItems(it)
                }
            }

            override fun onFailure(call: Call<CocktailData>, t: Throwable) {
                Log.d(javaClass.name, t.message, t)
            }
        })
    }

    private fun populateItems(cocktailData: CocktailData) {
        GlobalScope.launch {
            cocktailData.drinks.forEach {
                val picturePath =
                    downloadImageAndStore(
                        context,
                        it.strDrinkThumb,
                        it.strDrink.replace(" ", "_")
                    )

                var ingredients: List<String> = listOf<String>(
                    it.strIngredient1?: "",
                    it.strIngredient2?: "",
                    it.strIngredient3?: "",
                    it.strIngredient4?: "",
                    it.strIngredient5?: "",
                    it.strIngredient6?: "",
                    it.strIngredient7?: "",
                    it.strIngredient8?: "",
                    it.strIngredient9?: "",
                    it.strIngredient10?: "",
                    it.strIngredient11?: "",
                    it.strIngredient12?: "",
                    it.strIngredient13?: "",
                    it.strIngredient14?: "",
                    it.strIngredient15?: ""
                )

                ingredients = ingredients.filter { i -> i.isNotEmpty() }

                val values = ContentValues().apply {
                    put(Cocktail::title.name, it.strDrink)
                    put(Cocktail::picturePath.name, picturePath ?: "")

                    put(Cocktail::strAlcoholic.name, it.strAlcoholic)
                    put(Cocktail::strInstructions.name, it.strInstructions)
                    put(Cocktail::strIngredients.name, ingredients.toString().substring(1, ingredients.toString().length-1))
                    put(Cocktail::liked.name, false)
                }

                context.contentResolver.insert(COCKTAIL_PROVIDER_URI, values)
                //TODO take a good look at this, is it ok that i  am not using content provider ??
            }
            context.setBooleanPreference(DATA_IMPORTED, true)
            context.sendBroadcast<ProjectReceiver>()
        }
    }

}