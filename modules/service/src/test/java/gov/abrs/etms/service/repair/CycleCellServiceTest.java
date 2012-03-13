/**
 * 
 */
package gov.abrs.etms.service.repair;

import static org.junit.Assert.*;
import gov.abrs.etms.model.repair.CycleCell;

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
public class CycleCellServiceTest extends DatabaseTestCase {

	@Autowired
	private CycleCellService cycleCellService;//检修区间service

	private int cycleCellCount = 0;

	@Before
	public void setup() {
		cycleCellCount = this.getRowsCount(CycleCell.class);//初始化count
	}

	//测试-增 删 改 查
	//测试保存区间
	@Test
	public void testAddCycleCell() {
		String cycleId = "1";
		String cycleName = "射频机箱";
		String id = cycleCellService.addCycleCell(cycleName, cycleId);//保存
		this.flushSession();
		assertRowsCount(CycleCell.class, cycleCellCount + 1);
		CycleCell cycleCell = cycleCellService.get(Long.parseLong(id));//查找
		assertNotNull(cycleCell);
		assertEquals(cycleCell.getCycle().getId() + "", "1");
		assertEquals(cycleCell.getName(), "射频机箱");
	}

	//测试删除区间
	@Test
	public void testDeleteCycleCell() {
		String id = "1";
		cycleCellService.deleteCycleCell(id);
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(CycleCell.class, cycleCellCount - 1);
		CycleCell cycleCell = cycleCellService.get(Long.parseLong(id));//查找
		assertNull(cycleCell);
	}

	//测试修改名称
	@Test
	public void testEditCycleCell() {
		String id = "1";
		cycleCellService.editCycleCell("视频监控系统", id);
		this.flushSessionAndCloseSessionAndNewASession();
		CycleCell cycleCell = cycleCellService.get(Long.parseLong(id));//查找
		assertNotNull(cycleCell);
		assertEquals(cycleCell.getName(), "视频监控系统");
	}

	//测试删除前校验
	@Test
	public void testCheckDelete() {
		String id = "1";//bukeyi
		String id1 = "3";//keyi
		boolean flag = cycleCellService.checkDelete(id);
		boolean flag1 = cycleCellService.checkDelete(id1);
		assertEquals(flag, false);
		assertEquals(flag1, true);
	}

}
