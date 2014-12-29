/**
 * Prototyping Java Codes
 * <br>These codes are licensed under CC0.
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */
package info.baldanders.jdbc;

import info.baldanders.Util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * データアクセスクラス（with JDBC Driver）
 * <br>簡単な singleton class として実装する
 */
public final class DataAccess {

    /** データアクセスオブジェクト */
    private static DataAccess instance = null;

    /** 接続オブジェクト */
    private Connection connection;

    /**
     * コンストラクタ（singleton なので private として実装）
     */
    private DataAccess() {}

    /**
     * DataAccess インスタンスの取得とデータベース接続
     *
     * @return {@link DbStatement} ;
     * @throws ClassNotFoundException JDBC ドライバの登録に失敗した場合
     * @throws SQLException データベースの接続に失敗した場合
     * @throws IllegalArgumentException 接続情報を記述したプロパティファイルが存在しない場合
     * @throws IOException 接続情報を記述したプロパティファイルの内容が正しくない場合
     */
    public synchronized static DataAccess getInstance() throws ClassNotFoundException, SQLException, IllegalArgumentException, IOException {
        if (instance == null) {
            instance = new DataAccess();

            //接続情報の取得
            String url = DataAccessProperties.get("url");
            String user = DataAccessProperties.get("user");
            String password = DataAccessProperties.get("password");

            //ドライバクラスを登録する
            Class.forName("org.mariadb.jdbc.Driver");

            //接続オブジェクトの取得
            instance.connection = DriverManager.getConnection(url, user, password);
            instance.connection.setAutoCommit(false); //トランザクションの開始（auto commit しない）
        }
        return instance;
    }

    /**
     * 接続先のデータベース情報を取得する
     *
     * @return {@link String} ; データベース情報（改行区切り）。
     *                           データベース未接続の場合は空文字列を返す。
     * @throws SQLException データベース情報の取得に失敗した場合
     */
    public String getDbInfo() throws SQLException {
        if (Util.isNull(this.connection)) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            DatabaseMetaData dbmd = this.connection.getMetaData();
            sb.append("    DB Server URL: ").append(dbmd.getURL()).append(System.getProperty("line.separator"));
            sb.append("DB Server Product: ").append(dbmd.getDatabaseProductVersion()).append(System.getProperty("line.separator"));
            sb.append(" DB Server Driver: ").append(dbmd.getDriverName()).append(" ").append(dbmd.getDriverVersion());
            return sb.toString();
        }
    }

    /**
     * トランザクションのコミット
     *
     * @return データベースに接続していない場合は false。コミットが実行されたら true。
     * @throws SQLException コミット時の致命的エラー
     */
    public boolean commit() throws SQLException {
        if (Util.isNull(this.connection)) {
            return false;
        } else {
            this.connection.commit();
            return true;
        }
    }

    /**
     * トランザクションのロールバック
     *
     * @return データベースに接続していない場合は false。ロールバックが実行されたら true。
     * @throws SQLException ロールバック時の致命的エラー
     */
    public boolean rollback() throws SQLException {
        if (Util.isNull(this.connection)) {
            return false;
        } else {
            this.connection.rollback();
            return true;
        }
    }

    /**
     * SQL実行ステートメントの取得
     *
     * @param sql : {@link String} : SQL文
     * @return {@link DbStatement} ; データベースに接続していない場合は {@code null}。
     * @exception SQLException ステートメントの生成に失敗した場合
     */
    public synchronized DbStatement getStatement(String sql) throws SQLException {
        return getStatement(sql, null);
    }

    /**
     * SQL実行ステートメントの取得
     *
     * @param sql        : {@link String}                         : SQL文
     * @param paramList  : {@link ArrayList}<{@link DbParameter}> : パラメータのリスト。{@code null} または空の配列の場合はパラメータをセットしない。
     * @return {@link DbStatement} ; データベースに接続していない場合は {@code null}。
     * @exception SQLException ステートメントの生成に失敗した場合
     */
    public synchronized DbStatement getStatement(String sql, ArrayList<DbParameter> paramList) throws SQLException {
        if (Util.isNull(this.connection)) {
            return null;
        } else {
            return new DbStatement(this.connection, sql, paramList);
        }
    }
}
