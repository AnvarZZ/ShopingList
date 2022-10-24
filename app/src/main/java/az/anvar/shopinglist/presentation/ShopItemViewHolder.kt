package az.anvar.shopinglist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import az.anvar.shopinglist.R

class ShopItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvName = itemView.findViewById<TextView>(R.id.tvName)
    val tvCount = itemView.findViewById<TextView>(R.id.tvCount)
}