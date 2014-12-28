/**
 * Prototyping Java Codes
 * <br>These codes are licensed under CC0.
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */
package info.baldanders.ut;

import static org.junit.Assert.*;

import java.util.Date;

import info.baldanders.Util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * {@link Util} クラス用単体テスト
 */
public class TestUtil {

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
	 * {@link Util.stringJoin} テスト
	 * <br>{@code null} 値または空オブジェクト
	 */
	@Test
	public void testStringJoin1() {
		assertEquals("", Util.stringJoin(null, null));
		assertEquals("", Util.stringJoin("", null));
		assertEquals("", Util.stringJoin(",", null));
		String [] arry = {}; //入力値（空の配列）
		assertEquals("", Util.stringJoin(",", arry));
	}

	/**
	 * {@link Util.stringJoin} テスト
	 * <br>デリミタが {@code null} 値または空
	 */
	@Test
	public void testStringJoin2() {
		String [] arry = {"abc", "def"}; //入力値
		String result = "abcdef"; //期待値
		assertEquals(result, Util.stringJoin(null, arry));
		assertEquals(result, Util.stringJoin("", arry));
	}

	/**
	 * {@link Util.stringJoin} テスト
	 * <br>正常系
	 */
	@Test
	public void testStringJoin3() {
		String [] arry = {"abc", "def"}; //入力値
		String result = "abc,def"; //期待値
		assertEquals(result, Util.stringJoin(",", arry));
	}

	/**
	 * {@link Util.stringJoin} テスト
	 * <br>正常系（{@link Util.Quote}.{@code TYPE_NONE}）
	 */
	@Test
	public void testStringJoin4() {
		String [] arry = {"abc", "def"}; //入力値
		String result = "abc,def"; //期待値
		assertEquals(result, Util.stringJoin(",", arry, Util.Quote.TYPE_NONE));
	}

	/**
	 * {@link Util.stringJoin} テスト
	 * <br>正常系（{@link Util.Quote}.{@code TYPE_SQL}）
	 */
	@Test
	public void testStringJoin5() {
		String [] arry = {"abc", "def", "g'h", "i\"j"}; //入力値
		String result = "'abc','def','g''h','i\"j'"; //期待値
		assertEquals(result, Util.stringJoin(",", arry, Util.Quote.TYPE_SQL));
	}

	/**
	 * {@link Util.stringJoin} テスト
	 * <br>正常系（{@link Util.Quote}.{@code TYPE_CSV}）
	 */
	@Test
	public void testStringJoin6() {
		String [] arry = {"abc", "def", "g'h", "i\"j"}; //入力値
		String result = "\"abc\",\"def\",\"g'h\",\"i\"\"j\""; //期待値
		assertEquals(result, Util.stringJoin(",", arry, Util.Quote.TYPE_CSV));
	}

	/**
	 * {@link Util.stringJoin} テスト
	 * <br>文字列以外の配列（{@link Util.Quote}.{@code TYPE_NONE}）
	 */
	@Test
	public void testStringJoin7() {
		Object [] arry = {null, 100}; //入力値
		String result = ",100"; //期待値
		System.out.println("output7=["+Util.stringJoin(",", arry, Util.Quote.TYPE_NONE)+"]");
		assertEquals(result, Util.stringJoin(",", arry, Util.Quote.TYPE_NONE));
	}

	/**
	 * {@link Util.stringJoin} テスト
	 * <br>文字列以外の配列（{@link Util.Quote}.{@code TYPE_NONE}）
	 */
	@Test
	public void testStringJoin8() {
		Object [] arry = {null, 100}; //入力値
		String result = "'','100'"; //期待値
		System.out.println("output8=["+Util.stringJoin(",", arry, Util.Quote.TYPE_SQL)+"]");
		assertEquals(result, Util.stringJoin(",", arry, Util.Quote.TYPE_SQL));
	}

	/**
	 * {@link Util.stringJoin} テスト
	 * <br>文字列以外の配列（{@link Util.Quote}.{@code TYPE_NONE}）
	 */
	@Test
	public void testStringJoin9() {
		Object [] arry = {null, 100}; //入力値
		String result = "\"\",\"100\""; //期待値
		System.out.println("output9=["+Util.stringJoin(",", arry, Util.Quote.TYPE_CSV)+"]");
		assertEquals(result, Util.stringJoin(",", arry, Util.Quote.TYPE_CSV));
	}

	/**
	 * {@link Util.date2String} テスト
	 * <br>正常系
	 */
	@Test
	public void testDate2String1() {
		String dateStr = "2014-12-31T23:59:59+0900"; //入力値＝期待値
		Date dt = Util.string2Date(dateStr);
		assertEquals(dateStr, Util.date2String(dt));
	}

	/**
	 * {@link Util.date2String} テスト
	 * <br>{@code null} 値
	 */
	@Test
	public void testDate2String2() {
		assertEquals("", Util.date2String(null));
	}

	/**
	 * {@link Util.date2String} テスト
	 * <br>ロケールが {@code null} 値
	 */
	@Test
	public void testDate2String3() {
		Date dt = Util.string2Date("2014-12-31T23:59:59+0900");
		assertEquals("", Util.date2String(dt, null));
	}

	/**
	 * {@link Util.date2String} テスト
	 * <br>異常値
	 */
	@Test
	public void testString2Date4() {
		assertEquals(null, Util.string2Date(null));
		assertEquals(null, Util.string2Date(""));
		assertEquals(null, Util.string2Date("dummy"));
		assertEquals(null, Util.string2Date("2014-12-31T23:59:59+0900", null)); //ロケールがnull
	}

	/**
	 * {@link Util.string2Integer} テスト
	 * <br>正常系
	 */
	@Test
	public void testString2Integer1() {
		assertEquals(123, Util.string2Integer("123"));
	}

	/**
	 * {@link Util.string2Integer} テスト
	 * <br>異常値
	 */
	@Test
	public void testString2Integer2() {
		assertEquals(0, Util.string2Integer(null));
		assertEquals(0, Util.string2Integer(""));
		assertEquals(0, Util.string2Integer("dummy"));
		assertEquals(0, Util.string2Integer("100yen"));
	}

	/**
	 * {@link Util.null2String} テスト
	 * <br>正常系
	 */
	@Test
	public void testNull2String() {
		assertEquals("", Util.null2String(null));
		assertEquals("", Util.null2String(""));
		assertEquals("test", Util.null2String("test"));
		assertEquals("100", Util.null2String(100));
	}

	/**
	 * {@link Util.isBlank} テスト
	 * <br>正常系
	 */
	@Test
	public void testIsBlank() {
		assertEquals(true, Util.isBlank(null));
		assertEquals(true, Util.isBlank(""));
		assertEquals(false, Util.isBlank("test"));
	}

	/**
	 * {@link Util.isNull} テスト
	 * <br>正常系
	 */
	@Test
	public void testIsNull() {
		assertEquals(true, Util.isNull(null));
		assertEquals(false, Util.isNull(""));
		assertEquals(false, Util.isNull("test"));
		assertEquals(false, Util.isNull(100));
	}

	/**
	 * {@link Util.canWriteFile} テスト
	 * <br>正常系
	 */
	@Test
	public void testCanWriteFile() {
		String baseDir = System.getProperty("user.dir") + "\\doc\\";
		System.out.println("baseDir=["+baseDir+"]");
		assertEquals(false, Util.canWriteFile(null));
		assertEquals(false, Util.canWriteFile(""));
		assertEquals(false, Util.canWriteFile(baseDir+"test")); //ディレクトリ
		assertEquals(false, Util.canWriteFile(baseDir+"test\\cannotWrite.txt")); //書き込み不可なファイル
		assertEquals(true, Util.canWriteFile(baseDir+"test\\dummy.txt")); //存在しないファイル
		assertEquals(true, Util.canWriteFile(baseDir+"test\\canWrite.txt")); //書き込み可能なファイル
	}

	/**
	 * {@link Util.canReadFile} テスト
	 * <br>正常系
	 */
	@Test
	public void testCanReadFile() {
		String baseDir = System.getProperty("user.dir") + "\\doc\\";
		System.out.println("baseDir=["+baseDir+"]");
		assertEquals(false, Util.canReadFile(null));
		assertEquals(false, Util.canReadFile(""));
		assertEquals(false, Util.canReadFile(baseDir+"test")); //ディレクトリ
		assertEquals(true, Util.canReadFile(baseDir+"test\\cannotWrite.txt")); //書き込み不可なファイル
		assertEquals(false, Util.canReadFile(baseDir+"test\\dummy.txt")); //存在しないファイル
		assertEquals(true, Util.canReadFile(baseDir+"test\\canWrite.txt")); //書き込み可能なファイル
	}
}
