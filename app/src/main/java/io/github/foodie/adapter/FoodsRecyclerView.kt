package io.github.foodie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.foodie.R
import io.github.foodie.database.Foods

class FoodsRecyclerView(private val context: Context, private val itemList: List<Foods>, private val placeOrder: Button) :
    RecyclerView.Adapter<FoodsRecyclerView.FoodsViewHolder>() {
    class FoodsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val index = view.findViewById<TextView>(R.id.txtDetailsChildViewPosition)
        val name = view.findViewById<TextView>(R.id.txtDetailsChildViewName)
        val price = view.findViewById<TextView>(R.id.txtDetailsChildViewPrice)
        val addButton = view.findViewById<Button>(R.id.btnDetailsChildAddButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.restaurant_details_child_view, parent, false)
        return FoodsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodsViewHolder, position: Int) {
        holder.index.text = (position + 1).toString()
        holder.name.text = itemList[position].name
        holder.price.text = "₹" + itemList[position].price
        holder.addButton.setOnClickListener {
            placeOrder
        }
    }

    override fun getItemCount(): Int = itemList.size
}