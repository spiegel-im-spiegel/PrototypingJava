/**
 * Prototyping Java Codes
 * <br>These codes are licensed under CC0.
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */
package info.baldanders.jdbc;

import info.baldanders.Util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * SQLクエリ結果のデータセットクラス
 * <br>SQLクエリ結果を2次元配列 row に格納する。
 * 行位置は 1,2,... で指定すること（zero origin ではない）。
 */
public final class DataSet {

    /** クエリ結果（row:column の2次元配列） */
    private ArrayList<DataRow> row;

    /** カラム数 */
    private int columnCount;

    /**
     * コンストラクタ
     */
    public DataSet() {
        this.row = null;
        this.columnCount = 0;
    }

    /**
     * コンストラクタ
     *
     * @param rs : {@link ResultSet} : クエリ結果
     * @throws SQLException テーブル情報の取得に失敗した場合。または結果の取得に失敗した場合。
     */
    public DataSet(ResultSet rs) throws SQLException {
        this.row = null;
        this.columnCount = 0;
        if (!Util.isNull(rs)) {
            //テーブル情報の取得
            getMetaData(rs);
            //カラムデータを取得する
            boolean exist = false;
            while (rs.next()) {
                if (!exist) {
                    this.row = new ArrayList<DataRow>();
                    exist = true;
                }
                this.row.add(new DataRow(rs, this.columnCount));
            }
        }
    }

    /**
     * テーブル情報の取得
     *
     * @param rs : {@link ResultSet} : クエリ結果
     * @return boolean ; クエリ結果が {@code null} の場合は {@code false}。テーブル情報の取得に成功した場合は {@code true}。
     * @throws SQLException テーブル情報の取得に失敗した場合
     */
    private boolean getMetaData(ResultSet rs) throws SQLException {
        if (Util.isNull(rs)) {
            return false;

        } else {
            ResultSetMetaData metaData = rs.getMetaData();
            this.columnCount = metaData.getColumnCount(); //カラム数を取得する
            return true;
        }
    }

    /**
     * データセットをクリアする
     */
    public void clear() {
        if (!Util.isNull(this.row)) {
            for (DataRow dataRow: this.row) {
                dataRow.clear();
            }
            this.row.clear();
            this.row = null;
        }
    }

    /**
     * finalize
     * <br>データセットをクリアする
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
     * データの行数を取得する
     *
     * @return int ; データの行数。クエリ結果が {@code null} の場合は {@code 0} を返す。
     */
    public int sizeRow() {
        if (Util.isNull(this.row)) {
            return 0;
        } else {
            return this.row.size();
        }
    }

    /**
     * データのカラム数を取得する
     *
     * @return int ; データのカラム数（クエリ結果のメタデータから取得した値）
     */
    public int sizeColumn() {
        return this.columnCount;
    }

    /**
     * 指定行のカラムデータを取得する
     *
     * @param  rowNum : int : 行番号（1,2,...）
     * @return {@link DataRow} ; クエリ結果が {@code null} の場合は {@code null} を返す。
     *                            指定行番号が範囲外の場合は {@code null} を返す。
     */
    public DataRow getRow(int rowNum) {
        if (Util.isNull(this.row)) {
            return null;
        } else if (rowNum < 1 || sizeRow() < rowNum) {
            return null;
        } else {
            return this.row.get(rowNum-1);
        }
    }

    /**
     * 文字列変換<br>CSV形式に変換する
     *
     * @return {@link String} ; クエリ結果が {@code null} または空の場合は空文字列を返す。
     */
    @Override
    public String toString() {
        if (sizeRow() == 0) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            for (DataRow rowData: this.row) {
                sb.append(rowData.toString()).append(System.getProperty("line.separator"));
            }
            return sb.toString();
        }
    }
}
