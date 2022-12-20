/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codeCheck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import jp.co.yumemi.android.codeCheck.databinding.FragmentSearchBinding

class SearchFragment : Fragment(R.layout.fragment_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentSearchBinding = FragmentSearchBinding.bind(view)

        val searchViewModel = SearchViewModel(context!!)

        val linearLayoutManager = LinearLayoutManager(context!!)
        val dividerItemDecoration =
            DividerItemDecoration(context!!, linearLayoutManager.orientation)
        val customAdapter = CustomAdapter(object : CustomAdapter.OnItemClickListener {
            override fun itemClick(item: item) {
                gotoRepositoryFragment(item)
            }
        })

        fragmentSearchBinding.queryEditField.setOnEditorActionListener { editText, action, _ ->
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    editText.text.toString().let {
                        searchViewModel.searchResults(it).apply {
                            customAdapter.submitList(this)
                        }
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        fragmentSearchBinding.recyclerView.also {
            it.layoutManager = linearLayoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = customAdapter
        }
    }

    fun gotoRepositoryFragment(item: item) {
        val directions =
            SearchFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(item = item)
        findNavController().navigate(directions)
    }
}

val diff_util = object : DiffUtil.ItemCallback<item>() {
    override fun areItemsTheSame(oldItem: item, newItem: item): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: item, newItem: item): Boolean {
        return oldItem == newItem
    }

}

class CustomAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<item, CustomAdapter.ViewHolder>(diff_util) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OnItemClickListener {
        fun itemClick(item: item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.layout_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        (holder.itemView.findViewById<View>(R.id.repository_name_view) as TextView).text = item.name

        holder.itemView.setOnClickListener {
            itemClickListener.itemClick(item)
        }
    }
}
