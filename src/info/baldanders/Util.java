/**
 * Prototyping Java Codes
 * <br>These codes are licensed under CC0.
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */
package info.baldanders;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 雑多な関数群（utility functions）
 */
public final class Util {

    /**
     * 文字列連結（{@link Util.StringJoin}）時のフォーマット指定
     */
    public static enum Quote {
        /** 無指定 */
        TYPE_NONE,
        /** SQL形式の quote */
        TYPE_SQL,
        /** CSV形式の quote */
        TYPE_CSV
    }

    /**
     * 配列を文字列に変換し結合する（Java 8 の {@link String#join} 機能相当）
     * <br>※文字列に変換できるオブジェクトの配列が前提
     *
     * @param delimiter : {@link String}   : 分離文字（{@code null} および空文字列なら "'" に変換する）
     * @param array     : {@link Object}[] : オブジェクトの配列（{@code null} は空と見なす）
     * @return {@link String} ; 変換後の文字列（配列が空の場合は空文字列）
     */
    public static String stringJoin(String delimiter, Object [] array) {
        return stringJoin(delimiter, array, Quote.TYPE_NONE);
    }

    /**
     * 配列を文字列に変換し結合する（Java 8 の {@link String#join} 機能相当＋α）
     * <br>※文字列に変換できるオブジェクトの配列が前提
     *
     * @param delimiter : {@link String}   : 分離文字（{@code null} および空文字列なら "," に変換する）
     * @param array     : {@link Object}[]   : オブジェクトの配列（{@code null} は空と見なす）
     * @param quoteType : {@link Util.Quote} : quote のタイプ
     * @return {@link String} ; 変換後の文字列（配列が空の場合は空文字列）
     */
    public static String stringJoin(String delimiter, Object [] array, Quote quoteType) {
        if (isNull(array)) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            delimiter = null2String(delimiter);
            if (isBlank(delimiter)) {
            	delimiter = ",";
            }
            int count = 0;
            for (Object str : array) {
                if (count > 0) {
                    sb.append(delimiter);
                }
                if (quoteType == Quote.TYPE_SQL) {
                    sb.append(quoteSql(str));
                } else if (quoteType == Quote.TYPE_CSV) {
                    sb.append(quoteCsv(str));
                } else {
                    sb.append(null2String(str));
                }
                count++;
            }
            return sb.toString();
        }
    }

    /**
     * オブジェクトを文字列に変換しシングルクォートで囲む。
     * また文字列内にシングルクォートがある場合は SQL の形式に従ってエスケープする。
     * <br>※文字列に変換できるオブジェクトが前提
     *
     * @param obj : {@link Object} : インスタンスオブジェクト（{@code null} は空文字列と見なす）
     * @return {@link String} ; 変換後の文字列
     */
    public static String quoteSql(Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append("'").append(null2String(obj).replace("'", "''")).append("'");
        return sb.toString();
    }

    /**
     * オブジェクトを文字列に変換しダブルクォートで囲む。
     * また文字列内にダブルクォートがある場合は CSV の形式に従ってエスケープする。
     * <br>※文字列に変換できるオブジェクトが前提
     *
     * @param obj : {@link Object} : インスタンスオブジェクト（{@code null} は空文字列と見なす）
     * @return {@link String} ; 変換後の文字列
     */
    public static String quoteCsv(Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append("\"").append(null2String(obj).replace("\"", "\"\"")).append("\"");
        return sb.toString();
    }

    /** 日付変換用フォーマット */
    private static final String datetimeformat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    /**
     * 日付を文字列に変換する。
     * <br>"2014-12-31T23:59:59.999+0900" 形式に変換。
     *
     * @param {@link Date} dt ; 日付
     * @return {@link String} ; "2014-12-31T23:59:59.999+0900" 形式。日付が {@code null} なら空文字列を返す
     */
    public static String date2String(Date dt) {
        return date2String(dt, Locale.getDefault());
    }

    /**
     * 日付を文字列に変換する（ロケールを指定）。
     * <br>"2014-12-31T23:59:59+0900" 形式に変換。
     *
     * @param dt       : {@link Date}   : 日付
     * @param locale   : {@link Locale} : ロケール
     * @return {@link String} ; "2014-12-31T23:59:59.999+0900" 形式。日付・ロケールが {@code null} なら空文字列を返す
     */
    public static String date2String(Date dt, Locale locale) {
        if (isNull(dt) || isNull(locale)) {
            return "";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(datetimeformat, locale);
            return sdf.format(dt);
        }
    }

    /**
     * 文字列を日付に変換する。
     *
     * @param str : {@link String} : 日付を表す文字列（"2014-12-31T23:59:59+0900" 形式）
     * @return {@link Date} ; 引数が {@code null} または空文字列の場合は {@code null} を返す。
     *                         parse に失敗した場合は例外ではなく {@code null} を返す。
     */
    public static Date string2Date(String str) {
        return string2Date(str, Locale.getDefault());
    }

    /**
     * 文字列を日付に変換する（ロケールを指定）。
     *
     * @param str    : {@link String} : 日付を表す文字列（"2014-12-31T23:59:59.999+0900" 形式）
     * @param locale : {@link Locale} : ロケール
     * @return {@link Date} ; 引数が {@code null} または空文字列の場合は例外ではなく {@code null} を返す。
     *                         parse に失敗した場合は例外ではなく {@code null} を返す。
     */
    public static Date string2Date(String str, Locale locale) {
        str = null2String(str).trim(); //整形
        if (isBlank(str) || isNull(locale)) {
            return null;
        } else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(datetimeformat, locale);
                return sdf.parse(str);
            } catch (ParseException e) {
                return null;
            }
        }
    }

    /**
     * 文字列を数値（int）に変換する。
     *
     * @param str : {@link String} : 数値を表す文字列
     * @return int ; 引数が {@code null} または空文字列の場合は {@code 0} を返す。
     *                parse に失敗した場合は例外ではなく {@code 0} を返す。
     */
    public static int string2Integer(String str) {
        str = null2String(str).trim(); //整形
        if (isBlank(str)) {
            return 0;
        } else {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
    }

    /**
     * 文字列を真偽値（boolean）に変換する。
     *
     * @param str : {@link String} : 数値を表す文字列
     * @return boolean ; 引数が {@code null} または空文字列の場合は {@code false} を返す。
     *                    parse に失敗した場合は例外ではなく {@code false} を返す。
     */
    public static boolean string2Boolean(String str) {
        str = null2String(str).trim(); //整形
        if (isBlank(str)) {
            return false;
        } else {
            try {
                return  Boolean.parseBoolean(str);
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    /**
     * オブジェクトが {@code null} なら空文字列を返す。
     *
     * @param str : {@link String} : 任意の文字列
     * @return {@link String}
     */
    public static String null2String(String str) {
        if (isNull(str)) {
            return "";
        } else {
            return str;
        }
    }

    /**
     * オブジェクトが {@code null} なら空文字列を返す。
     * {@code null} 以外なら {@link String} に変換して返却
     *
     * @param  obj : {@link Object} : 任意のインスタンスオブジェクト
     * @return {@link String}
     */
    public static String null2String(Object obj) {
        if (isNull(obj)) {
            return "";
        } else {
            return obj.toString();
        }
    }

    /**
     * 文字列が {@code null} または空文字列なら {@code true} を返す。
     *
     * @param str : {@link String} : 任意の文字列
     * @return boolean
     */
    public static boolean isBlank(String str) {
        return (isNull(str) || str.length() == 0);
    }

    /**
     * オブジェクトが {@code null} なら {@code true} を返す。
     *
     * @param  obj : {@link Object} : 任意のインスタンスオブジェクト
     * @return boolean
     */
    public static boolean isNull(Object obj) {
        return (obj == null);
    }

    /**
     * ファイルが書き込み可能かどうかチェックする
     *
     * @param  filePath : {@link String} : ファイル名
     * @return boolean ; ファイル名が {@code null} または空文字列なら書き込み不可 {@code false} とする。
     *                    ファイルが存在しないなら書き込み可 {@code true} とする。
     */
    public static boolean canWriteFile(String filePath){
        if (isBlank(filePath)) {
            return false;
        } else {
            File file = new File(filePath);
            return canWriteFile(file);
        }
    }

    /**
     * ファイルが書き込み可能かどうかチェックする
     *
     * @param  file : {@link File} : ファイル
     * @return boolean ; ファイルが {@code null} なら書き込み不可 {@code false} とする。
     *                    ファイルが存在しないなら書き込み可 {@code true} とする。
     */
    public static boolean canWriteFile(File file){
        if (isNull(file)) {
            return false;
        } else if (file.exists()) {
            if (file.isFile() && file.canWrite()) {
                return true;
            } else {
                return false;
            }
        } else {
            return true; //存在しないなら書き込み可とする
        }
    }

    /**
     * ファイルが読み込み可能かどうかチェックする
     *
     * @param  filePath : {@link String} : ファイル名
     * @return boolean ; ファイル名が {@code null} または空文字列なら読み込み不可 {@code false} とする。
     *                    ファイルが存在しないなら読み込み不可 {@code false} とする
     */
    public static boolean canReadFile(String filePath){
        if (isBlank(filePath)) {
            return false;
        } else {
            File file = new File(filePath);
            return canReadFile(file);
        }
    }

    /**
     * ファイルが読み込み可能かどうかチェックする
     *
     * @param  file : {@link File} : ファイル
     * @return boolean ; ファイルが {@code null} なら読み込み不可 {@code false} とする。
     *                    ファイルが存在しないなら読み込み不可 {@code false} とする
     */
    public static boolean canReadFile(File file){
        if (isNull(file)) {
            return false;
        } else if (file.exists()) {
            if (file.isFile() && file.canRead()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
