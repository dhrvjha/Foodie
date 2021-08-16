package io.github.foodie.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.github.foodie.R
import io.github.foodie.activity.RestaurantDetailsActivity
import io.github.foodie.database.Restaurants

class HomeRecyclerView(private val context: Context, private val itemList: ArrayList<Restaurants>) :
    RecyclerView.Adapter<HomeRecyclerView.HomeViewHolder>() {
    class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.txtRecyclerChildRestaurantName)
        val price = view.findViewById<TextView>(R.id.txtRecyclerChildPrice)
        val rating = view.findViewById<TextView>(R.id.txtRecyclerChildRating)
        val image = view.findViewById<ImageView>(R.id.imgRecyclerChildRestaurantIcon)
        val favouriteImage = view.findViewById<ImageButton>(R.id.imgRecyclerChildFavouriteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_child_view, parent, false)
        return HomeViewHolder(view = view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.name.text = itemList[position].name
        holder.price.text = "₹${itemList[position].price}/ person"
        holder.rating.text = itemList[position].rating
        Picasso.get().load(itemList[position].image).error(R.drawable.ic_launcher_foreground)
            .into(holder.image)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, RestaurantDetailsActivity::class.java)
            intent.putExtra("restaurant_id", itemList[position].id.toString())
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = itemList.size
}