package jp.co.yumemi.android.codeCheck

/**
 * 検索結果を保存するデータクラス
 */
data class SearchResultModel(
    val repositoryList: List<Item> = listOf<Item>(),
)