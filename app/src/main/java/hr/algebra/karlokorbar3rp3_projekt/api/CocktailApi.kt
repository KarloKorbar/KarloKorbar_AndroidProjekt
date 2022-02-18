package hr.algebra.karlokorbar3rp3_projekt.api

import retrofit2.Call
import retrofit2.http.GET
//https://www.thecocktaildb.com/api/json/v1/1/search.php?s=margarita
const val API_URL = "https://www.thecocktaildb.com/api/json/v1/1/"
interface CocktailApi {
    @GET("search.php?s=margarita")
    fun fetchItems(): Call<CocktailData>
}