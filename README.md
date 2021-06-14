# BubbleMonster
 
BubbleMonsterは跳ねるモンスターを倒すシューティングゲームです。
 
# DEMO

<img width="300" alt="bubble_img" src="https://user-images.githubusercontent.com/52380218/121849446-4bda9680-cd26-11eb-8943-7f9fe0414aeb.png">

- 操作方法
    - Spaceキー：弾をうつ
    - 方向キーの左・右：プレイヤーを左右に動かす
 
# Features
 
- 跳ねるモンスターを打つと2つに分裂する
- プレイヤーが発する弾は一直線上の弾（一番上に達すると無くなる）
 
# Requirement
 
動作確認済みの環境
 
* macOS Big Sur 11.2.1
* java 11.0.1
* javafx 11.0.2
 
# Installation

[javaFX](https://gluonhq.com/products/javafx/)をダウンロードする。
 
# Usage

こちらのGitとjavaFXをダウンロードしてコンパイルおよび実行する。

```bash
git clone https://github.com/R4nSC/BubbleMonster.git
cd BubbleMonster
mv ../javafx-sdk-11.0.2 .
javac --module-path ./javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml *.java
java --module-path ./javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml BubbleMonster
```
 
# Note
 
だいぶバグがあります。少しずつ直していきます。
 
# License
 
"BubbleMonster" is under [MIT license](https://en.wikipedia.org/wiki/MIT_License).

Thank you!