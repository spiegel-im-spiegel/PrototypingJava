/**
 * Prototyping Java Codes
 * <br>These codes are licensed under CC0.
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */
package info.baldanders.jdbc;

import info.baldanders.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * SQLクエリ結果の行データクラス
 * <br>各カラムのデータはすべて String または null で格納する
 *
 */
public final class DataRow {

    /** カラムデータ */
    private ArrayList<String> column;

    /**
     * コンストラクタ
     *
     * @return DataRow
     */
    public DataRow() {
        this.column = null;
    }

    /**
     * コンストラクタ
     *
     * @param rs          : {@link ResultSet} : クエリ結果
     * @param columnCount : int               : カラム数
     * @return DataRow
     * @throws SQLException カラムの値の取得に失敗した場合
     */
    public DataRow(ResultSet rs, int columnCount) throws SQLException {
        this.column = null;
        if (!Util.isNull(rs)) {
            this.column = new ArrayList<String>();
            for (int i = 0; i < columnCount; i++) {
                this.column.add(rs.getString(i+1));
            }
        }
    }

    /**
     * 行データをクリアする
     */
    public void clear() {
        if (!Util.isNull(this.column)) {
            this.column.clear();
            this.column = null;
        }
    }

    /**
     * finalize <br>行データをクリアする
     */
    @Override
    protected void finalize() throws Throwable {
        try {
            super.finalize();
        } finally {
            clear(); //データベースを閉じる
        }
    }

    /**
     * データのカラム数を取得する
     *
     * @return int ; データのカラム数。カラムデータが {@code null} の場合は 0 を返す。
     */
    public int size() {
        if (Util.isNull(this.column)) {
            return 0;
        } else {
            return this.column.size();
        }
    }

    /**
     * 指定カラムのデータ（文字列）を取得する
     *
     * @param  colNum : int : カラム番号（1,2,...）
     * @return {@link String} ; カラムデータが {@code null} の場合は {@code null} を返す。
     *                           指定カラム番号が範囲外の場合は {@code null} を返す。
     */
    public String get(int colNum) {
        if (Util.isNull(this.column)) {
            return null;
        } else if (colNum < 1 || size() < colNum) {
            return null;
        } else {
            return this.column.get(colNum-1);
        }
    }

    /**
     * 指定カラムにデータ（文字列）を設定する
     *
     * @param  colNum : int            : カラム番号（1,2,...）
     * @param  data   : {@link String} : 設定データ
     * @return void
     */
    public void set(int colNum, String data) {
        if (Util.isNull(this.column)) {
            //カラムデータが null なら何もしない
        } else if (colNum < 1 || size() < colNum) {
            //指定カラム番号が範囲外なら何もしない
        } else {
            this.column.set(colNum-1, data); //値をセット
        }
    }

    /**
     * 配列に変換する
     *
     * @return {@link Object} [] ; カラムデータが {@code null} の場合は {@code null} を返す。
     */
    public Object [] toArray() {
        if (Util.isNull(this.column)) {
            return null;
        } else {
            return this.column.toArray();
        }
    }

    /**
     * 文字列変換（CSV形式）
     *
     * @return {@link String} ; カラムデータが {@code null} の場合は空文字列を返す。
     */
    @Override
    public String toString() {
        if (Util.isNull(this.column)) {
            return "";
        } else {
            return Util.stringJoin(",", toArray(), Util.Quote.TYPE_CSV);
        }
    }
}
