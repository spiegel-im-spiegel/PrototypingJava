/**
 * Prototyping Java Codes
 * <br>These codes are licensed under CC0.
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */
package info.baldanders.log;

import info.baldanders.Util;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Logger Factory クラス
 * <br>java.util.logging.Logger インスタンスの取得クラス
 */
public final class LoggerFactory {

    /** file logging handler */
    private static FileHandler fileHandler;

    /** console logging handler */
    private static ConsoleHandler consoleHandler;

    /** アプリケーション名 */
    private static String appName;

	static {
		try {
	        //プロパティを取得する
			String pattern = LoggerProperties.get("java.util.logging.FileHandler.pattern"); //ログファイル名
			int limit = Util.string2Integer(LoggerProperties.get("java.util.logging.FileHandler.limit")); //最大バイト数（0で制限なし）
			int count = Util.string2Integer(LoggerProperties.get("java.util.logging.FileHandler.count")); //最大バイト数
			boolean append = Util.string2Boolean(LoggerProperties.get("java.util.logging.FileHandler.append")); //追記モード
			//ハンドラを生成
			fileHandler = new FileHandler(pattern, limit, count, append);
			consoleHandler = new ConsoleHandler();
			//出力レベル
			fileHandler.setLevel(getLevel4File());
			consoleHandler.setLevel(getLevel4Console());
			//フォーマッタの指定
			fileHandler.setFormatter(new LogFormatter());
			consoleHandler.setFormatter(new LogFormatter());
			//アプリケーション名を取得（最後にピリオドをセットする）
			try {
				InitialContext ctx = new InitialContext();
				appName = ctx.lookup("java:app/AppName").toString() + ".";
			} catch (NamingException e) {
				appName = "PrototypingJava.";
			}
		} catch (SecurityException e) {
            e.printStackTrace(); //どうしようもないので例外情報を標準エラー出力に吐き出す
		} catch (IOException e) {
            e.printStackTrace(); //どうしようもないので例外情報を標準エラー出力に吐き出す
		}
	}

    /**
     * {@link Logger} インスタンスの取得
     *
     * @param name : {@link String} : logger 名
     * @return {@link Logger} インスタンス
     */
	public static Logger getLogger(String name) {
		return getLogger(name, null);
	}

    /**
     * {@link Logger} インスタンスの取得（レベル指定あり）
     *
     * @param name  : {@link String} : logger 名
     * @param level : {@link Level}  : 出力レベル
     * @return {@link Logger} インスタンス
     */
	public static Logger getLogger(String name, Level level) {
		Logger logger = Logger.getLogger(appName + name);
		//ログファイルのハンドラ
		if (fileHandler != null) {
			logger.addHandler(fileHandler);
		}
		if (consoleHandler != null) {
			logger.addHandler(consoleHandler);
		}
		//出力レベル
		if (Util.isNull(level)) {
			level = getLevel(); //レベルの指定がない場合はプロパティファイルから取得する
		}
		logger.setLevel(level);
		//親 logger に送信しない
		logger.setUseParentHandlers(false);

		return logger;
	}

    /**
     * プロパティファイルから出力レベルを取得する
     *
     * @return {@link Level} 出力レベル。
     *                        ファイル用と標準エラー出力用で優先順位の低いほうを返す。
     */
	private static Level getLevel() {
		Level levelFile = getLevel4File();
		Level levelConsole = getLevel4Console();
		return  (levelFile.intValue() < levelConsole.intValue()) ? levelFile : levelConsole;
	}

	/**
     * プロパティファイルから出力レベルを取得する（ファイル出力用）
     *
     * @return {@link Level} 出力レベル
     */
	private static Level getLevel4File() {
		Level level = Level.INFO; //出力レベルのデフォルト
		try {
			level = Level.parse(LoggerProperties.get("java.util.logging.FileHandler.level"));
		} catch (Exception e) {
			//なにもしない
		}
		return level;
	}

    /**
     * プロパティファイルから出力レベルを取得する（標準エラー出力用）
     *
     * @return {@link Level} 出力レベル
     */
	private static Level getLevel4Console() {
		Level level = Level.INFO; //出力レベルのデフォルト
		try {
			level = Level.parse(LoggerProperties.get("java.util.logging.ConsoleHandler.level"));
		} catch (Exception e) {
			//なにもしない
		}
		return level;
	}
}
