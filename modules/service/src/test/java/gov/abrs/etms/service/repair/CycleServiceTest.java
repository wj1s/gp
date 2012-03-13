/**
 * 
 */
package gov.abrs.etms.service.repair;

import static org.junit.Assert.*;
import gov.abrs.etms.model.repair.Card;
import gov.abrs.etms.model.repair.Cycle;
import gov.abrs.etms.model.repair.CycleCell;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.taiji.common.test.DatabaseTestCase;

/**
 * @author 赵振喜
 *
 */
@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class CycleServiceTest extends DatabaseTestCase {
	@Autowired
	private CycleService cycleService;//检修周期表service
	@Autowired
	private CycleCellService cycleCellService;//检修区间service
	@Autowired
	private CardService cardService;//检修卡片
	private int cycleCount = 0;
	private int cycleCellCount = 0;
	private int cardCount = 0;

	@Before
	public void setup() {
		cycleCount = this.getRowsCount(Cycle.class);//初始化count
		cycleCellCount = this.getRowsCount(CycleCell.class);//初始化count
		cardCount = this.getRowsCount(Card.class);//初始化count
	}

	//测试-增 删 改 查
	//测试保存周期表
	@Test
	public void testAddCycle() {
		String deptId = "1";
		String cycleName = "10kv检修周期表";
		String id = cycleService.addCycle(deptId, cycleName);//保存
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(Cycle.class, cycleCount + 1);
		Cycle cycle = cycleService.getCycleById(id);//查找
		assertNotNull(cycle);
		assertEquals(cycle.getDept().getId() + "", "1");
		assertEquals(cycle.getName(), "10kv检修周期表");
	}

	//测试删除周期表
	@Test
	public void testDeleteCycle() {
		String id[] = { "1", "2" };
		cycleService.deleteCycle(id);
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(Cycle.class, cycleCount - 2);
		Cycle cycle = cycleService.getCycleById(id[0]);//查找
		Cycle cycle1 = cycleService.getCycleById(id[1]);//查找
		assertNull(cycle);
		assertNull(cycle1);
	}

	//测试修改名称
	@Test
	public void testEditCycle() {
		String ids[] = { "1!1642in!10kv检修周期表", "2!1642in!10kv检修周期表2" };
		cycleService.editCycle(ids);
		this.flushSessionAndCloseSessionAndNewASession();
		Cycle cycle = cycleService.getCycleById("1");//查找
		Cycle cycle1 = cycleService.getCycleById("2");//查找
		assertNotNull(cycle);
		assertNotNull(cycle1);
		assertEquals(cycle.getName(), "10kv检修周期表");
		assertEquals(cycle1.getName(), "10kv检修周期表2");
	}

	//测试启用/停用周期表
	@Test
	public void testSetAbleCycle() {
		String id[] = { "1", "3" };
		cycleService.setAbleCycle(id, "0");
		Cycle cycle = cycleService.getCycleById("1");//查找
		Cycle cycle1 = cycleService.getCycleById("3");//查找
		assertNotNull(cycle);
		assertNotNull(cycle1);
		assertEquals(cycle.getActive(), "0");
		assertEquals(cycle1.getActive(), "0");
	}

	//测试删除前校验
	@Test
	public void testCheckDelete() {
		String id[] = { "1", "2" };//1不可以 、2可以
		String id1[] = { "2" };//2可以
		boolean flag = cycleService.checkDelete(id);
		boolean flag1 = cycleService.checkDelete(id1);
		assertEquals(flag, false);
		assertEquals(flag1, true);
	}

	//测试获取检修周期表执行情况
	@Test
	public void testGetCycle() {
		int id = 3;
		String year = "2011";
		List<Map> list = cycleService.getCycle(id, year);//长度为区间+卡片数目
		assertEquals(list.size(), 1);//有一个区间
		Map m = list.get(0);
		assertEquals(m.get("sysName") + "", "周期表3");//周期表名
		List<Map> cellList = (List) m.get("cellList");//区间
		assertEquals(cellList.size(), 1);//有一个区间
		Map mm = cellList.get(0);
		assertEquals(mm.get("cellName") + "", "自动化系统");//周期表名
		List<Map> cardList = (List) mm.get("cardList");
		assertEquals(cardList.size(), 1);//有一个卡片
		Map cardMap = cardList.get(0);
		assertEquals(cardMap.get("cardId") + "", "2");//card Id
		List repairList = (List) cardMap.get("repairList");//1月份有两条检修记录
		List rpOne = (List) repairList.get(0);
		assertEquals(rpOne.size(), 2);//1月份有两条检修记录
	}

}
