/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codeCheck

import androidx.lifecycle.ViewModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.util.*

/**
 * 検索にかかわるViewModel
 */
class SearchViewModel : ViewModel() {

    // TODO: lastSearchDateをモデルとして保存、およびlastSearchDateを取得する関数の定義
    private var _lastSearchDate = Date()
    val lastSearchDate get() = _lastSearchDate

    // 検索を行い、レポジトリ名のリストを取得する
    fun getSearchResults(inputText: String): List<Item> = runBlocking {

        val client = HttpClient(Android)

        return@runBlocking GlobalScope.async {

            // リクエストを行いレスポンスを得る
            val response: HttpResponse =
                client.get("https://api.github.com/search/repositories") {
                    header("Accept", "application/vnd.github.v3+json")
                    parameter("q", inputText)
                }

            // JSONからデータを取得する
            // TODO: データをモデルとして保存し、そのデータを取得する関数を作成
            val jsonBody = JSONObject(response.receive<String>())
            val jsonItems = jsonBody.optJSONArray("items") ?: return@async listOf()
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

            // 現在時刻を保存
            _lastSearchDate = Date()

            return@async items.toList()
        }.await()
    }
}