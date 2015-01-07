/**
 * Prototyping Java Codes
 * <br>These codes are licensed under CC0.
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */
package info.baldanders.log;

import info.baldanders.Util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.LogManager;

/**
 * Logger プロパティ取得クラス
 */
public final class LoggerProperties {

    /** プロパティファイル名 */
    private static final String propertyFileName  = "logger.properties";

    /** プロパティのインスタンス */
    private static Properties properties = null;

    /**
     * プロパティ値を取得する
     * <br>プロパティファイルから取得。
     *     ファイルの読み込みは最初の1回のみ行われる。
     *     プロパティファイルがない（または読み込みに失敗した）場合は、デフォルト値を返す。
     *
     * @param key : {@link String} : キーワード
     * @return {@link String} ; キーワードに対する値。
     *                           キーワードが {@code null} または空文字列の場合は空文字列を返す
     */
    public static String get(String key) {
        if (Util.isBlank(key)) {
            return "";
        } else {
            //デフォルトの Logger 設定を読み込む
            LogManager defaults = LogManager.getLogManager();
            //プロパティファイルをインポート
            try {
                importFile();
                return properties.getProperty(key, defaults.getProperty(key));
            } catch (IllegalArgumentException e) {
                return defaults.getProperty(key); //デフォルトの値を取得
            } catch (IOException e) {
                return defaults.getProperty(key); //デフォルトの値を取得
            }
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
                inStream = LoggerProperties.class.getResourceAsStream(propertyFileName);
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
