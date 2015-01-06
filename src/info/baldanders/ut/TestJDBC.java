/**
 * Prototyping Java Codes
 * <br>These codes are licensed under CC0.
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */
package info.baldanders.ut;

import static org.junit.Assert.*;

import java.sql.Types;
import java.util.ArrayList;

import info.baldanders.jdbc.DataAccess;
import info.baldanders.jdbc.DataAccessProperties;
import info.baldanders.jdbc.DataSet;
import info.baldanders.jdbc.DbParameter;
import info.baldanders.jdbc.DbStatement;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * {@link DataAccess} クラス用単体テスト
 */
public class TestJDBC {

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

    @Test
    public void testAll() throws Exception {
        test01DataAccessProperties();
        test01GetInstance();
        test02Select();
        test03Insert();
        test02Select();
        test04aUpdate();
        test02Select();
        test04bUpdate();
        test02Select();
        test05Delete();
        test02Select();
    }

    private void test01DataAccessProperties() throws Exception {
        System.out.println("url=["+DataAccessProperties.get("url")+"]");
        System.out.println("user=["+DataAccessProperties.get("user")+"]");
        System.out.println("password=["+DataAccessProperties.get("password")+"]");
        System.out.println("(null)=["+DataAccessProperties.get(null)+"]");
        System.out.println("(blank)=["+DataAccessProperties.get("")+"]");
    }

    private void test01GetInstance() throws Exception {
        DataAccess dao = DataAccess.getInstance();
        System.out.println(dao.getDbInfo()); //出力先のデータベース情報を表示する
    }
    private void test02Select() throws Exception {
        DataAccess dao = DataAccess.getInstance();
        DbStatement statement = dao.getStatement("SELECT * FROM M_PERSON;");
        DataSet ds = statement.select();
        if (ds.sizeRow() == 0) {
            System.out.println("データがない");
            assertEquals("", ds.toString());
        } else {
            System.out.println(ds.toString()); //結果を CSV 形式で出力
        }
    }

    private void test03Insert() throws Exception {
        DataAccess dao = DataAccess.getInstance();
        ArrayList<DbParameter> params = new ArrayList<DbParameter>();
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO M_PERSON (");
        sql.append(" PERSON_NAME");
        sql.append(",AGE");
        sql.append(",UPD_TIME");
        sql.append(") VALUE (");
        sql.append(" ?");
        params.add(new DbParameter("Alice", Types.VARCHAR));
        sql.append(",?");
        params.add(new DbParameter(24, Types.SMALLINT));
        sql.append(",SYSDATE()");
        sql.append(");");
        DbStatement statement1 = dao.getStatement(sql.toString(), params);
        assertEquals(true, statement1.insert());
        assertEquals(true, dao.commit());
    }

    private void test04aUpdate() throws Exception {
        DataAccess dao = DataAccess.getInstance();
        ArrayList<DbParameter> params = new ArrayList<DbParameter>();
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE M_PERSON SET");
        sql.append(" PERSON_NAME = ?");
        params.add(new DbParameter("Bob", Types.VARCHAR));
        sql.append(",AGE = ?");
        params.add(new DbParameter(42, Types.SMALLINT));
        sql.append(",UPD_TIME = SYSDATE()");
        sql.append(" WHERE");
        sql.append(" PERSON_NAME = ?");
        params.add(new DbParameter("Alice", Types.VARCHAR));
        sql.append(";");
        DbStatement statement1 = dao.getStatement(sql.toString(), params);
        assertEquals(true, statement1.update());
        assertEquals(true, dao.rollback());
    }

    private void test04bUpdate() throws Exception {
        DataAccess dao = DataAccess.getInstance();
        ArrayList<DbParameter> params = new ArrayList<DbParameter>();
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE M_PERSON SET");
        sql.append(" PERSON_NAME = ?");
        params.add(new DbParameter("Bob", Types.VARCHAR));
        sql.append(",AGE = ?");
        params.add(new DbParameter(42, Types.SMALLINT));
        sql.append(",UPD_TIME = SYSDATE()");
        sql.append(" WHERE");
        sql.append(" PERSON_NAME = ?");
        params.add(new DbParameter("Alice", Types.VARCHAR));
        sql.append(";");
        DbStatement statement1 = dao.getStatement(sql.toString(), params);
        assertEquals(true, statement1.update());
        assertEquals(true, dao.commit());
    }

    private void test05Delete() throws Exception {
        DataAccess dao = DataAccess.getInstance();
        ArrayList<DbParameter> params = new ArrayList<DbParameter>();
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM M_PERSON");
        sql.append(" WHERE");
        sql.append(" PERSON_NAME = ?");
        params.add(new DbParameter("Bob", Types.VARCHAR));
        sql.append(";");
        DbStatement statement1 = dao.getStatement(sql.toString(), params);
        assertEquals(true, statement1.delete());
        assertEquals(true, dao.commit());
    }
}
