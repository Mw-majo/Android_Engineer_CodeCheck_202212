# 株式会社ゆめみ Android エンジニアコードチェック課題

## 概要

本プロジェクトは株式会社ゆめみ（以下弊社）が、弊社に Android エンジニアを希望する方に出す課題のベースプロジェクトです。本課題が与えられた方は、下記の概要を詳しく読んだ上で課題を取り組んでください。

## アプリ仕様

本アプリは GitHub のリポジトリを検索するアプリです。

<img src="https://user-images.githubusercontent.com/85604966/209483761-5affee87-0c01-4259-a6fe-6e45bee7e788.png" width="320">　<img src="https://user-images.githubusercontent.com/85604966/209483760-3e7597c0-d598-4a9c-b0e6-988d3f7b802a.png" width="320">　<img src="https://user-images.githubusercontent.com/85604966/209483759-1d5c35ef-17eb-44e9-a443-d51d5c3c436d.png" width="320">
<img src="https://user-images.githubusercontent.com/85604966/209483766-70ecfb06-14c5-4016-93b5-960718ead81a.png" width="320">　<img src="https://user-images.githubusercontent.com/85604966/209483765-7cdf4fb3-ad35-4f59-a125-dde0265483f5.png" width="320">　<img src="https://user-images.githubusercontent.com/85604966/209483763-ff71440b-8031-4461-bfbb-d9c8c88f2166.png" width="320">

### 環境

- IDE：Android Studio Bumblebee | 2021.1.1 Patch 3
- Kotlin：1.5.31
- Java：11
- Gradle：7.0.1
- minSdk：23
- targetSdk：31

### 動作

1. 何かしらのキーワードを入力
2. GitHub API（`search/repositories`）でリポジトリを検索し、結果一覧を概要（リポジトリ名）で表示
3. 特定の結果を選択したら、該当リポジトリの詳細（リポジトリ名、オーナーアイコン、プロジェクト言語、Star 数、Watcher 数、Fork 数、Issue 数）を表示

## 課題
* [x] #1 ソースコードの可読性の向上
* [x] #2 ソースコードの安全性の向上
* [x] #3 バグの修正
* [x] #4 Fat Fragmentの回避
* [x] #5 プログラム構造をリファクタリング
* [x] #6 アーキテクチャを適用
* [ ] #7 テストを追加
* [x] #8 UIをブラッシュアップ
* [x] #9 新機能を追加
