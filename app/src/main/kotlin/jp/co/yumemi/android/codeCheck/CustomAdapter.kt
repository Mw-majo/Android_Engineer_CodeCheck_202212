package jp.co.yumemi.android.codeCheck

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


// Item間の差分を調べる
val diff_util = object : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}

/**
 * Item(レポジトリのデータ)をrecyclerViewなどで表示させるためのListAdapterにクリックリスナを追加したクラス
 */
class CustomAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<Item, CustomAdapter.ViewHolder>(diff_util) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OnItemClickListener {
        fun itemClick(item: Item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.layout_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.findViewById<TextView>(R.id.repository_name_view).text = item.name
        holder.itemView.setOnClickListener {
            itemClickListener.itemClick(item)
        }
    }
}
