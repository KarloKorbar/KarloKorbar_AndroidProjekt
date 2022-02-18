package hr.algebra.karlokorbar3rp3_projekt.api

import com.google.gson.annotations.SerializedName

data class CocktailData(
    @SerializedName("drinks") val drinks : List<CocktailItem>
)
