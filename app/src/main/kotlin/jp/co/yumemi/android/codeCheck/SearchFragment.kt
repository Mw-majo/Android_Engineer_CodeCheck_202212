/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codeCheck

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.co.yumemi.android.codeCheck.databinding.FragmentSearchBinding

class SearchFragment : Fragment(R.layout.fragment_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentSearchBinding = FragmentSearchBinding.bind(view)
        val searchViewModel: SearchViewModel by activityViewModels()
        val linearLayoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), linearLayoutManager.orientation)
        val customAdapter = CustomAdapter(
            object : CustomAdapter.OnItemClickListener {
                override fun itemClick(item: Item) {
                    val directions =
                        SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(item)
                    findNavController().navigate(directions)
                }
            }
        )

        // 入力を監視するリスナーを定義
        fragmentSearchBinding.queryEditField.addTextChangedListener(
            object : OnTextWatcher {
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val keyword = p1.toString()
                    searchViewModel.getSearchResults(keyword).apply {
                        customAdapter.submitList(this)
                    }
                }
            }
        )

        fragmentSearchBinding.recyclerView.also {
            it.layoutManager = linearLayoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = customAdapter
        }
    }

}
