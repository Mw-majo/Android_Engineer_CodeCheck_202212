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
import jp.co.yumemi.android.codeCheck.SearchActivity.Companion.lastSearchDate
import jp.co.yumemi.android.codeCheck.databinding.FragmentSearchResultBinding

class SearchResultFragment : Fragment(R.layout.fragment_search_result) {

    private val args: SearchResultFragmentArgs by navArgs()

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("検索した日時", lastSearchDate.toString())

        _binding = FragmentSearchResultBinding.bind(view)

        var item = args.item

        binding.ownerIconView.load(item.ownerIconUrl);
        binding.nameView.text = item.name;
        binding.languageView.text = item.language;
        binding.starsView.text = "${item.stargazersCount} stars";
        binding.watchersView.text = "${item.watchersCount} watchers";
        binding.forksView.text = "${item.forksCount} forks";
        binding.openIssuesView.text = "${item.openIssuesCount} open issues";
    }
}