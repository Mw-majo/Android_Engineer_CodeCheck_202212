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

        // 入力を監視するリスナーを定義
        fragmentSearchBinding.queryEditField.setOnEditorActionListener { editText, action, _ ->

            val keyword = editText.text.toString()

            // 入力がないか、ボタンが押されていなければ検索を行わない
            if (action != EditorInfo.IME_ACTION_SEARCH || keyword == "") {
                return@setOnEditorActionListener false
            }

            searchViewModel.getSearchResults(keyword).apply {
                customAdapter.submitList(this)
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
