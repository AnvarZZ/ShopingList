package az.anvar.shopinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import az.anvar.shopinglist.R
import az.anvar.shopinglist.domain.ShopItem

class ShopListAdapter : Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_DISABLED -> R.layout.item_shop_item_disabled
            VIEW_TYPE_ENABLED -> R.layout.item_shop_item_enabled
            else -> throw java.lang.RuntimeException("Unknown view type $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]
        holder.tvName.text = shopItem.name
        holder.tvCount.text = shopItem.count.toString()
        holder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        holder.itemView.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    override fun getItemViewType(position: Int): Int {
        if (shopList[position].enabled) return VIEW_TYPE_ENABLED
        return VIEW_TYPE_DISABLED

    }

    class ShopItemViewHolder(itemView: View) : ViewHolder(itemView) {
        val tvName = itemView.findViewById<TextView>(R.id.tvName)
        val tvCount = itemView.findViewById<TextView>(R.id.tvCount)
    }

    companion object {
        const val VIEW_TYPE_DISABLED = 0
        const val VIEW_TYPE_ENABLED = 1

        const val MAX_POOL_SIZE = 15
    }
}