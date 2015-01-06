/**
 * Prototyping Java Codes
 * <br>These codes are licensed under CC0.
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */
package info.baldanders.jdbc;

import java.sql.Types;

/**
 * SQLステートメント用パラメータ情報クラス
 */
public final class DbParameter {

    /** パラメータ値 */
    private Object value;

    /** パラメータのタイプ（{@link Types}） */
    private int sqlType;

    /**
     * コンストラクタ
     */
    public DbParameter() {
        this.value = null;
        this.sqlType = Types.INTEGER;
    }

    /**
     * コンストラクタ
     *
     * @param val  : {@link Object} : パラメータ値
     * @param type : int : パラメータのタイプ（{@link Types}）
     */
    public DbParameter(Object val, int type) {
        this.value = val;
        this.sqlType = type;
    }

    /**
     * パラメータ値の取得
     *
     * @return {@link Object} ; パラメータ値
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * パラメータタイプの取得
     *
     * @return int ; パラメータのタイプ（{@link Types}）
     */
    public int getType() {
        return this.sqlType;
    }
}
