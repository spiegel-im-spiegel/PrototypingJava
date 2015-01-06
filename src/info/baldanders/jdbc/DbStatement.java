/**
 * Prototyping Java Codes
 * <br>These codes are licensed under CC0.
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */
package info.baldanders.jdbc;

import info.baldanders.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * SQL実行ステートメントクラス
 * <br>{@link java.sql.PreparedStatement} を使用
 */
public final class DbStatement {

    /** SQL実行ステートメント */
    private PreparedStatement statement;

    /**
     * コンストラクタ
     */
    public DbStatement() {
        this.statement = null;
    }

    /**
     * コンストラクタ
     *
     * @param connection : {@link Connection} : DB接続オブジェクト
     * @param sql        : {@link String} : SQL文
     * @exception SQLException ステートメントの取得に失敗した場合
     */
    public DbStatement(Connection connection, String sql) throws SQLException {
        createStatement(connection, sql, null);
    }

    /**
     * コンストラクタ
     *
     * @param connection : {@link Connection} : DB接続オブジェクト
     * @param sql        : {@link String} : SQL文
     * @param paramList  : {@link ArrayList}<{@link DbParameter}> : パラメータのリスト
     * @exception SQLException ステートメントの取得に失敗した場合。またはパラメータのセットに失敗した場合
     */
    public DbStatement(Connection connection, String sql, ArrayList<DbParameter> paramList) throws SQLException {
        createStatement(connection, sql, paramList);
    }

    /**
     * ステートメントの生成
     *
     * @param connection : {@link Connection} : DB接続オブジェクト
     * @param sql        : {@link String} : SQL文
     * @param paramList  : {@link ArrayList}<{@link DbParameter}> : パラメータのリスト。{@code null} または空の配列の場合はパラメータをセットしない。
     * @return void
     * @exception SQLException ステートメントの取得に失敗した場合。またはパラメータのセットに失敗した場合
     */
    private void createStatement(Connection connection, String sql, ArrayList<DbParameter> paramList) throws SQLException {
        if (!Util.isNull(this.statement)) {
            close();
        }
        this.statement = connection.prepareStatement(sql);
        if (!Util.isNull(paramList)) {
            int index = 1;
            for (DbParameter param: paramList) {
                setParameter(index, param);
                index++;
            }
        }
    }

    /**
     * パラメータのセット
     *
     * @param index : int : ステートメント中のプレースホルダの index
     * @param param : {@link DbParameter} : パラメータ
     * @return boolean ; ステートメントが {@code null} の場合は {@code false} を返す。パラメータがセットできれば {@code true} を返す。
     * @exception SQLException パラメータのセットに失敗した場合
     */
    public boolean setParameter(int index, DbParameter param) throws SQLException {
        if (Util.isNull(this.statement)) {
            return false;
        } else {
            this.statement.setObject(index, param.getValue(), param.getType());
            return true;
        }
    }

    /**
     * ステートメントを閉じる
     */
    public void close() {
        if (!Util.isNull(this.statement)) {
            try {
                this.statement.close();
            } catch (SQLException e) {
                //例外は無視する
            }
            this.statement = null;
        }
    }

    /**
     * finalize
     * <br>ステートメントを閉じる
     */
    @Override
    protected void finalize() throws Throwable {
        try {
            super.finalize();
        } finally {
            close(); //ステートメントを閉じる
        }
    }

    /**
     * クエリを実行する
     *
     * @return {@link DataSet} ; 実行結果のデータセット。
     *                            ステートメントが {@code null} の場合は {@code null} を返す。
     * @exception SQLException クエリに失敗した場合
     */
    public DataSet select() throws SQLException {
        if (Util.isNull(this.statement)) {
            return null;
        } else {
        	ResultSet rs = this.statement.executeQuery();
            return new DataSet(rs);
        }
    }

    /**
     * SQL（DML文）を実行する
     *
     * @return boolean ; ひとつ以上の更新があれば {@code true} を返す。ステートメントが {@code null} の場合は {@code false} を返す。
     * @exception SQLException ; 実行に失敗した場合
     */
    public Boolean update() throws SQLException {
        if (Util.isNull(this.statement)) {
            return false;
        } else {
            int res = this.statement.executeUpdate();
            if (res > 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * SQL（DML文）を実行する（別名）
     *
     * @return boolean ; ひとつ以上の更新があれば {@code true} を返す。ステートメントが {@code null} の場合は {@code false} を返す。
     * @exception SQLException ; 実行に失敗した場合
     */
    public Boolean insert() throws SQLException {
        return update();
    }

    /**
     * SQL（DML文）を実行する（別名）
     *
     * @return boolean ; ひとつ以上の更新があれば {@code true} を返す。ステートメントが {@code null} の場合は {@code false} を返す。
     * @exception SQLException ; 実行に失敗した場合
     */
    public Boolean delete() throws SQLException {
        return update();
    }
}
