/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codeCheck

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.codeCheck.databinding.FragmentSearchResultBinding
import java.util.*

class SearchResultFragment : Fragment(R.layout.fragment_search_result) {

    private val args: SearchResultFragmentArgs by navArgs()

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    //private val viewModel: SearchViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchViewModel = SearchViewModel(requireContext())
        Log.d("検索した日時", searchViewModel.lastSearchDate.value.toString())

        val item = args.item
        _binding = FragmentSearchResultBinding.bind(view)
        binding.ownerIconView.load(item.ownerIconUrl)
        binding.nameView.text = item.name
        binding.languageView.text = item.language
        binding.starsView.text = "${item.stargazersCount} stars"
        binding.watchersView.text = "${item.watchersCount} watchers"
        binding.forksView.text = "${item.forksCount} forks"
        binding.openIssuesView.text = "${item.openIssuesCount} open issues"
    }
}
