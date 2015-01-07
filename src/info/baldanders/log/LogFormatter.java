/**
 * Prototyping Java Codes
 * <br>These codes are licensed under CC0.
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */
package info.baldanders.log;

import info.baldanders.Util;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Formatter クラス（継承クラス）
 * @see java.util.logging.Formatter
 */
public class LogFormatter extends Formatter {

    /**
     * ログレコードをフォーマット
     * <br>ログレコードを以下の形式に整形する。
     * <blockquote>2015-01-05T17:49:25.129+0900 [SEVERE ] : Therad(10) : info.baldanders.ut.TestLogger (testLoggerFactory) : 致命的エラー</blockquote>
     * @see java.util.logging.Formatter#format(java.util.logging.LogRecord)
     */
    @Override
    public synchronized String format(LogRecord record) {
        final StringBuffer msg = new StringBuffer(128);

        //時刻
        msg.append(Util.date2String(new Date(record.getMillis()))).append(" ");
        //出力レベル
        Level level = record.getLevel();
        if (level == Level.SEVERE) {
            msg.append("[SEVERE ] : ");
        } else if (level == Level.WARNING) {
            msg.append("[WARNING] : ");
        } else if (level == Level.INFO) {
            msg.append("[INFO   ] : ");
        } else if (level == Level.CONFIG) {
            msg.append("[CONFIG ] : ");
        } else if (level == Level.FINE) {
            msg.append("[FINE   ] : ");
        } else if (level == Level.FINER) {
            msg.append("[FINER  ] : ");
        } else if (level == Level.FINEST) {
            msg.append("[FINEST ] : ");
        } else {
            msg.append("[").append(level.getName()).append("] : ");
        }
        //スレッドID
        msg.append("Thread(").append(record.getThreadID()).append(") : ");
        //クラス名
        msg.append(Util.null2String(record.getSourceClassName())).append(" ");
        //メソッド名
        msg.append("(").append(Util.null2String(record.getSourceMethodName())).append(") : ");
        //メッセージ
        msg.append(formatMessage(record)).append(System.getProperty("line.separator"));
        //例外情報の出力（もしあれば）
        Throwable throwable = record.getThrown();
        if (!Util.isNull(throwable)) {
            msg.append(throwable.toString()).append(System.getProperty("line.separator"));
            for (StackTraceElement trace : throwable.getStackTrace()) {
                msg.append('\t').append(trace.toString()).append(System.getProperty("line.separator"));

            }
        }
        return msg.toString();
    }
}
