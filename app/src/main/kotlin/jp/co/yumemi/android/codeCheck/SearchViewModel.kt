/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codeCheck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

/**
 * 検索にかかわるViewModel
 */
class SearchViewModel : ViewModel() {

    // SearchDateModelからlastSearchDateを取得
    private val _searchDateModel = MutableStateFlow(SearchDateModel())
    val searchDateModel = _searchDateModel.asStateFlow()

    // SearchResultModelからレポジトリ名のリストを取得する
    private val _searchResultModel = MutableStateFlow(SearchResultModel())
    val searchResultModel = _searchResultModel.asStateFlow()

    // githubAPIサーバにリクエストを送り検索結果を得る
    fun requestSearchingRepositories(inputText: String) {

        viewModelScope.launch {
            val client = HttpClient(Android)

            val response: HttpResponse =
                client.get("https://api.github.com/search/repositories") {
                    header("Accept", "application/vnd.github.v3+json")
                    parameter("q", inputText)
                }
            val jsonBody = JSONObject(response.receive<String>())
            val jsonItems = jsonBody.optJSONArray("items") ?: JSONArray()
            val items = mutableListOf<Item>()

            for (i in 0 until jsonItems.length()) {
                val jsonItem = jsonItems.optJSONObject(i)
                val name = jsonItem.optString("full_name")
                val ownerIconUrl =
                    jsonItem.optJSONObject("owner")?.optString("avatar_url") ?: continue
                val language = jsonItem.optString("language")
                val stargazersCount = jsonItem.optLong("stargazers_count")
                val watchersCount = jsonItem.optLong("watchers_count")
                val forksCount = jsonItem.optLong("forks_count")
                val openIssuesCount = jsonItem.optLong("open_issues_count")

                items.add(
                    Item(
                        name = name,
                        ownerIconUrl = ownerIconUrl,
                        language = language,
                        stargazersCount = stargazersCount,
                        watchersCount = watchersCount,
                        forksCount = forksCount,
                        openIssuesCount = openIssuesCount
                    )
                )
            }

            // 結果を保存
            _searchResultModel.update { currentState ->
                currentState.copy(repositoryList = items)
            }

            // 現在時刻を保存
            _searchDateModel.update { currentState ->
                currentState.copy(lastSearchDate = Date())
            }
        }
    }
}