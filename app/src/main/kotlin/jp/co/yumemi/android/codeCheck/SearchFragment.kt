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
        val searchViewModel = SearchViewModel()
        val linearLayoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), linearLayoutManager.orientation)
        val customAdapter = CustomAdapter(
            object : CustomAdapter.OnItemClickListener {
                override fun itemClick(item: Item) {
                    transitionSearchResultFragment(item)
                }
            }
        )

        fragmentSearchBinding.queryEditField.setOnEditorActionListener { editText, action, _ ->
            if (action != EditorInfo.IME_ACTION_SEARCH) {
                return@setOnEditorActionListener false
            }

            editText.text.toString().let {
                searchViewModel.getSearchResults(it).apply {
                    customAdapter.submitList(this)
                }
            }
            return@setOnEditorActionListener true
        }

        fragmentSearchBinding.recyclerView.also {
            it.layoutManager = linearLayoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = customAdapter
        }
    }

    fun transitionSearchResultFragment(item: Item) {
        val directions = SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(item)
        findNavController().navigate(directions)
    }
}
