package hr.algebra.karlokorbar3rp3_projekt

import android.app.AlertDialog
import android.content.ContentUris
import android.content.Context
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

class CocktailAdapter(private val context: Context, private val items: MutableList<Cocktail>) :
    RecyclerView.Adapter<CocktailAdapter.ViewHolder>(){
    class ViewHolder(cocktailView: View) : RecyclerView.ViewHolder(cocktailView){
        private val ivItem = cocktailView.findViewById<ImageView>(R.id.ivItem)
        private val tvItem = cocktailView.findViewById<TextView>(R.id.tvItem)
        fun bind(cocktail: Cocktail) {
            tvItem.text = cocktail.title
            Picasso.get()
                .load(File(cocktail.picturePath))
                .error(R.drawable.nasa)
                .transform(RoundedCornersTransformation(100, 5))
                .into(ivItem)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.cocktail, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cocktail = items[position]
        holder.itemView.setOnClickListener{
            context.startActivity<CocktailPagerActivity>(COCKTAIL_POSITION, position)
        }
        holder.itemView.setOnLongClickListener{
            AlertDialog.Builder(context).apply {
                setTitle(R.string.delete)
                setMessage(context.getString(R.string.sure) + " '${cocktail.title}'?")
                setIcon(R.drawable.delete)
                setCancelable(true)
                setNegativeButton(R.string.cancel, null)
                setPositiveButton("Ok") {_, _ -> deleteItem(position)}
                show()
            }
            true
        }
        holder.bind(items[position])
    }

    private fun deleteItem(position: Int) {
        val item = items[position]
        context.contentResolver.delete(
            ContentUris.withAppendedId(COCKTAIL_PROVIDER_URI, item._id!!),
            null,
            null
        )
        File(item.picturePath).delete()
        items.removeAt(position)
        notifyDataSetChanged() // observable kick
    }

    override fun getItemCount(): Int = items.size
}