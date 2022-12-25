/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codeCheck

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.codeCheck.databinding.FragmentSearchResultBinding

class SearchResultFragment : Fragment(R.layout.fragment_search_result) {

    private val args: SearchResultFragmentArgs by navArgs()

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchViewModel = SearchViewModel()
        Log.d("検索した日時", searchViewModel.lastSearchDate.toString())

        val item = args.item
        _binding = FragmentSearchResultBinding.bind(view)
        binding.ownerIconView.load(item.ownerIconUrl)
        binding.nameView.text = item.name
        binding.languageView.text = getString(R.string.written_language, item.language)
        binding.starsView.text = getString(R.string.stars_text, item.stargazersCount)
        binding.watchersView.text = getString(R.string.watchers_text, item.watchersCount)
        binding.forksView.text = getString(R.string.forks_text, item.forksCount)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
