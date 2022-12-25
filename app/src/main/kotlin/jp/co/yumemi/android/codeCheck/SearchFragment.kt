/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codeCheck

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.co.yumemi.android.codeCheck.databinding.FragmentSearchBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * 検索窓を提供するFragment
 * 検索結果のレポジトリ名の一覧が表示される
 * レポジトリを選択するとSearchResultFragmentに遷移し、選択したレポジトリの詳細が表示される
 */
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

        // 検索窓への入力を監視するリスナーを作成
        fragmentSearchBinding.queryEditField.addTextChangedListener(
            object : OnTextWatcher {
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val keyword = p1.toString()
                    viewLifecycleOwner.lifecycleScope.launch {
                        searchViewModel.requestSearchingRepositories(keyword)
                        searchViewModel.searchResultModel.collect {
                            customAdapter.submitList(it.repositoryList)
                        }
                    }

                }
            }
        )

        // recyclerViewを用いて、レポジトリ名の一覧を表示するbindを作成
        fragmentSearchBinding.recyclerView.also {
            it.layoutManager = linearLayoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = customAdapter
        }
    }

}
