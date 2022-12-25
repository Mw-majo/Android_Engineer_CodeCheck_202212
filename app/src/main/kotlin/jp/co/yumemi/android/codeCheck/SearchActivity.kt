/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codeCheck

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

/**
 * githubのレポジトリ検索用のアクティビティ
 */
class SearchActivity : AppCompatActivity(R.layout.activity_search) {


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setupNightMode()
    }

    private fun setupNightMode() {
        val nightMode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        } else {
            AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
        }
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }
}