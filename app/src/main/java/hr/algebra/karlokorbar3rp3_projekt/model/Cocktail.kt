package hr.algebra.karlokorbar3rp3_projekt.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//TODO: update so it works with ROOM
data class Cocktail(
    var _id: Long?,
    val title: String,
    val picturePath: String,
    //add favorite boolean (not in the api, just for the database)
    //figure out ingredients
    //figure out instructions

    //can i update anything else?
    //TODO: add more later

    val strAlcoholic : String,
    val strInstructions : String,
    val strIngredients : String,
    var liked:Boolean
)
