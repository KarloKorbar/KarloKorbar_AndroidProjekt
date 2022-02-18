package hr.algebra.karlokorbar3rp3_projekt

import android.app.AlertDialog
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.karlokorbar3rp3_projekt.framework.startActivity
import hr.algebra.karlokorbar3rp3_projekt.model.Cocktail
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.io.File
import java.nio.file.Files.delete

class CocktailPagerAdapter(private val context: Context, private val items: MutableList<Cocktail>) :
    RecyclerView.Adapter<CocktailPagerAdapter.ViewHolder>() {
    class ViewHolder(cocktailView: View) : RecyclerView.ViewHolder(cocktailView) {
        private val ivItem = cocktailView.findViewById<ImageView>(R.id.ivItem)
        private val tvTitle = cocktailView.findViewById<TextView>(R.id.tvCocktail)
        val ivRead = cocktailView.findViewById<ImageView>(R.id.ivRead)
        private val tvIngredients = cocktailView.findViewById<TextView>(R.id.tvIngredients)
        private val tvInstructions = cocktailView.findViewById<TextView>(R.id.tvInstructions)

        //TODO add
        fun bind(cocktail: Cocktail) {
            tvTitle.text = cocktail.title
            ivRead.setImageResource(if (cocktail.liked) R.drawable.heart_solid else R.drawable.heart_regular)
            tvIngredients.text = cocktail.strIngredients
            tvInstructions.text = cocktail.strInstructions
            Picasso.get()
                .load(File(cocktail.picturePath))
                .error(R.drawable.nasa)
                .transform(RoundedCornersTransformation(9, 5))
                .into(ivItem)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.cocktail_pager, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.ivRead.setOnClickListener {
            item.liked = !item.liked
            val uri = ContentUris.withAppendedId(COCKTAIL_PROVIDER_URI, item._id!!)
            val values = ContentValues().apply {
                put(Cocktail::liked.name, item.liked)
            }
            context.contentResolver.update(
                uri,
                values,
                null,
                null
            )
            notifyItemChanged(position)
        }

        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size
}