package gov.abrs.etms.service.abnormal;

import static org.junit.Assert.*;
import gov.abrs.etms.dao.util.ExecuteDAO;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

//可以更换runner
//@RunWith(Parameterized.class)@Suite.SuiteClasses
@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class TechAccidentServiceTest {
	@Autowired
	private ExecuteDAO executeDAO;

	/**
	 *
	 @Parameters    
	public static Collection data() ...{
	        return Arrays.asList(new Object[][]...{
	                ...{2, 4},
	                ...{0, 0},
	                ...{－3, 9},
	        });
	}

	//构造函数，对变量进行初始化
	public SquareTest(int param, int result) ...{
	        this.param = param;
	            this.result = result;
	}

	 */
	//所有单元测试之前进行的操作 必须是public static的
	@BeforeClass
	public static void setUpClass() {

	}

	//所有单元测试之后进行的操作
	@AfterClass
	public static void tearDownClass() {

	}

	//每个单元测试之前进行的操作
	@Before
	public void setUp() throws Exception {}

	//每个单元测试之后进行的操作
	@After
	public void tearDown() throws Exception {}

	//对查询进行单元测试
	@Test
	public void testFindByInstance() {
		assertEquals(5, 5);//第一个参数填写期待结果，第二个参数填写实际结果
	}

	@Ignore("该方法还没有实现")
	@Test
	public void testGetSQL() {

	}

	@Ignore("该方法还没有实现")
	//测试时间不超过1000毫秒
	@Test(timeout = 1000)
	public void testSetQuery() {

	}
}
