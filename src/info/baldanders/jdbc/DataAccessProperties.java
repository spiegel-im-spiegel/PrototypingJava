/**
 * Prototyping Java Codes
 * <br>These codes are licensed under CC0.
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */
package info.baldanders.jdbc;

import info.baldanders.Util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * データアクセス用プロパティクラス
 * <br>プロパティファイルを読み込む
 */
public final class DataAccessProperties {

    /** プロパティファイル名 */
    private static final String propertyFileName  = "dataAccess.properties";

    /** プロパティのインスタンス */
    private static Properties properties = null;

    /**
     * プロパティ値を取得する
     * <br>プロパティファイルから取得。
     *     ファイルの読み込みは最初の1回のみ行われる。
     *
     * @param key : {@link String} : キーワード
     * @return {@link String} ; キーワードに対する値。
     *                           キーワードが {@code null} または空文字列の場合は空文字列を返す
     * @throws IllegalArgumentException プロパティファイルが存在しない場合
     * @throws IOException プロパティファイルの内容が正しくない場合
     */
    public static String get(String key) throws IllegalArgumentException, IOException {
        if (Util.isBlank(key)) {
            return "";
        } else {
            importFile(); //プロパティファイルをインポート
            return properties.getProperty(key);
        }
    }
    /**
     * プロパティファイルからプロパティ情報を取得する
     * <br>ファイルの読み込みは最初の1回のみ行われる
     *
     * @return void
     * @throws IllegalArgumentException プロパティファイルが存在しない場合
     * @throws IOException プロパティファイルの内容が正しくない場合
     */
    private synchronized static void importFile() throws IllegalArgumentException, IOException {
        if (Util.isNull(properties)) {
            InputStream inStream = null;
            properties = new Properties();
            try {
                //プロパティファイルを読み込む
                inStream = DataAccessProperties.class.getResourceAsStream(propertyFileName);
                if (Util.isNull(inStream)) {
                    throw new IllegalArgumentException("プロパティファイルの読み込みに失敗しました。" + propertyFileName + "の存在を確認してください。");
                }
                //プロパティをセットする
                properties.load(inStream);
            } catch (IOException e) {
                throw e;
            } finally {
                if (inStream != null) {
                    try {
                        inStream.close();
                    } catch (IOException e) {
                    	//close の例外は無視する
                    }
                }
            }
        }
    }
}
