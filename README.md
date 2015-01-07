# Java 関連コードのプロトタイピング

## Warning

These codes are prototypes, and are not safety.

## 概要 (Overview)

 * このコードはプロトタイプです。そのままの形で製品に組み込まないで下さい。
 * ライブラリやフレームワーク等に類似の機能がある場合はそちらを使って下さい。このコードは十分に検証されていません。

## ライセンス（License）

These codes are licensed under CC0; http://creativecommons.org/publicdomain/zero/1.0/

このコードは CC0 でライセンスしています。
どなたでも自由に複製，頒布（公衆送信を含む），翻案（改変）等を行うことができます。

 * [CC0について ― “いかなる権利も保有しない” « Science Commons – サイエンス・コモンズ翻訳プロジェクト](http://sciencecommons.jp/cc0/about)

## クラスの構成

### info.baldanders.Util クラス

雑多な処理を束ねた static 関数群。
文字列変換や `null` 判定等の基本的な処理をまとめています。
簡単な使い方は `info.baldanders.ut.TestUtil.java` を参考にどうぞ。

### info.baldanders.jdbc.* クラス群

JDBC を使った RBBMS データアクセス・クラス群。
簡単な singleton を用い，アプリケーションで単一の接続を保持します。
使い方は `info.baldanders.ut.TestJDBC.java` を参考にどうぞ。

### info.baldanders.log.* クラス群

`java.util.logging.Logger` クラスのラッパー。
システムプロパティを汚さないようにしています。
簡単な使い方は `info.baldanders.ut.TestLogger.java` を参考にどうぞ。

## 外部ライブラリについて

このコードでは JRE の標準ライブラリ以外に，以下の外部ライブラリに依存しています。

### JDBC ドライバ

`info.baldanders.jdbc.DataAccess` クラスについて，ここでは MriaDB の JDBC ドライバを用いています。
他の RDBMS およびそのドライバを用いる際は `getInstance` メソッド内の

```java
//ドライバクラスを登録する
Class.forName("org.mariadb.jdbc.Driver");
```

の部分を差し換えてください。

 * [Welcome to MariaDB! - MariaDB](https://mariadb.org/)
 * [MariaDB Java Client - MariaDB Knowledge Base](https://mariadb.com/kb/en/mariadb/client-libraries/mariadb-java-client/) （JDBC ドライバ, LGPL ライセンス）
 * [MySQLとMariaDBの違い一覧 - 技術メモ置き場](http://d.hatena.ne.jp/interdb/20130918/1379441784)

