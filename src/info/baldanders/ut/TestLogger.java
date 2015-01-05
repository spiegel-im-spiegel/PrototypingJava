/**
 * Prototyping Java Codes
 * <br>These codes are licensed under CC0.
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */
package info.baldanders.ut;


import java.util.logging.Logger;

import info.baldanders.log.LoggerFactory;
import info.baldanders.log.LoggerProperties;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * {@link LoggerFactory} クラス用単体テスト
 */
public class TestLogger {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

    /**
     * {@link LoggerFactory} テスト
     */
    @Test
    public void testLoggerFactory() {
        System.out.println("java.util.logging.FileHandler.pattern=["+LoggerProperties.get("java.util.logging.FileHandler.pattern")+"]");
        System.out.println("java.util.logging.FileHandler.limit=["+LoggerProperties.get("java.util.logging.FileHandler.limit")+"]");
        System.out.println("java.util.logging.FileHandler.count=["+LoggerProperties.get("java.util.logging.FileHandler.count")+"]");
        System.out.println("java.util.logging.FileHandler.append=["+LoggerProperties.get("java.util.logging.FileHandler.append")+"]");
        System.out.println("java.util.logging.FileHandler.level=["+LoggerProperties.get("java.util.logging.FileHandler.level")+"]");
        System.out.println("java.util.logging.ConsoleHandler.level=["+LoggerProperties.get("java.util.logging.ConsoleHandler.level")+"]");
    	
    	Logger logger = LoggerFactory.getLogger("TestLoggerFactory");
    	logger.severe("致命的エラー");
    	logger.warning("警告");
    	logger.info("情報");
    	logger.config("設定");
    	logger.fine("詳細レベル(1)");
    	logger.finer("詳細レベル(2)");
    	logger.finest("詳細レベル(3)");
    }
}
