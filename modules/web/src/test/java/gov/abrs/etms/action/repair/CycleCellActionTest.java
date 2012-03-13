/**
 * 
 */
package gov.abrs.etms.action.repair;

import static org.junit.Assert.*;
import gov.abrs.etms.action.BaseActionTest;
import gov.abrs.etms.model.baseinfo.Equip;
import gov.abrs.etms.model.repair.Card;
import gov.abrs.etms.model.repair.Cycle;
import gov.abrs.etms.service.baseinfo.EquipService;
import gov.abrs.etms.service.repair.CardService;
import gov.abrs.etms.service.repair.CycleCellService;
import gov.abrs.etms.service.security.SecurityService;
import gov.abrs.etms.service.util.UtilService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author 赵振喜
 *
 */
public class CycleCellActionTest extends BaseActionTest {
	private CycleCellAction cycleCellAction;
	private CycleCellService cycleCellService;
	private CardService cardService;
	private SecurityService securityService;
	private UtilService utilService;
	private EquipService equipService;

	@Before
	public void setup() {
		cycleCellAction = new CycleCellAction();
		cycleCellService = control.createMock(CycleCellService.class);
		cardService = control.createMock(CardService.class);
		utilService = control.createMock(UtilService.class);
		securityService = control.createMock(SecurityService.class);
		equipService = control.createMock(EquipService.class);
		cycleCellAction.setCycleCellService(cycleCellService);
		cycleCellAction.setCardService(cardService);
		cycleCellAction.setSecurityService(securityService);
		cycleCellAction.setUtilService(utilService);
	}

	@Test
	public void testShow() {
		Map formMap = new HashMap();//定义form
		String id[] = { "1" };
		formMap.put("id", id);
		cycleCellAction.setFormMap(formMap);
		EasyMock.expect(cycleCellService.getShowList((String) EasyMock.anyObject())).andReturn(new Cycle());
		EasyMock.expect(cycleCellService.getPeriod()).andReturn(new ArrayList());
		control.replay();//
		String result = cycleCellAction.show();//获取结果
		assertEquals(result, CycleCellAction.SUCCESS);
	}

	@Test
	public void testAdd() {
		Map formMap = new HashMap();//定义form
		String name[] = { "1" };
		String id[] = { "1" };
		formMap.put("name", name);
		formMap.put("id", id);
		cycleCellAction.setFormMap(formMap);
		EasyMock.expect(cycleCellService.addCycleCell(name[0], id[0])).andReturn("");
		control.replay();//
		String result = cycleCellAction.add();//获取结果
		assertEquals(result, "redirectCycleShow");
	}

	@Test
	public void testEdit() {
		Map formMap = new HashMap();//定义form
		String name[] = { "1" };
		String id[] = { "1" };
		String cycleCellId[] = { "1" };
		formMap.put("name", name);
		formMap.put("id", id);
		formMap.put("cycleCellId", cycleCellId);
		cycleCellAction.setFormMap(formMap);
		cycleCellService.editCycleCell(name[0], cycleCellId[0]);
		EasyMock.expectLastCall().anyTimes();
		control.replay();//
		String result = cycleCellAction.edit();//获取结果
		assertEquals(result, "redirectCycleShow");
	}

	@Test
	public void testCheckDeleteCell() {
		String ids = "1";
		cycleCellAction.setIds(ids);
		EasyMock.expect(cycleCellService.checkDelete(ids)).andReturn(true);
		control.replay();//
		String result = cycleCellAction.checkDeleteCell();//获取结果
		assertEquals(result, cycleCellAction.RIGHT);
	}

	@Test
	public void testCheckDeleteCard() {
		String ids = "1";
		cycleCellAction.setIds(ids);
		EasyMock.expect(cardService.checkDelete(ids)).andReturn(true);
		control.replay();//
		String result = cycleCellAction.checkDeleteCard();//获取结果
		assertEquals(result, cycleCellAction.RIGHT);
	}

	@Test
	public void testDelete() {
		Map formMap = new HashMap();//定义form
		String id[] = { "1" };
		String cycleCellId[] = { "1" };
		formMap.put("id", id);
		formMap.put("cycleCellId", cycleCellId);
		cycleCellAction.setFormMap(formMap);
		cycleCellService.deleteCycleCell(cycleCellId[0]);
		EasyMock.expectLastCall().anyTimes();
		control.replay();//
		String result = cycleCellAction.delete();//获取结果
		assertEquals(result, "redirectCycleShow");
	}

	@Test
	public void testAddCard() {
		Map formMap = new HashMap();//定义form
		String name[] = { "1" };
		String id[] = { "1" };
		String cycleCellId[] = { "1" };
		String periodId[] = { "1" };
		formMap.put("periodId", periodId);
		formMap.put("name", name);
		formMap.put("id", id);
		formMap.put("cycleCellId", cycleCellId);
		cycleCellAction.setFormMap(formMap);
		EasyMock.expect(cardService.addCard(cycleCellId[0], name[0], periodId[0])).andReturn("").anyTimes();
		control.replay();//
		String result = cycleCellAction.addCard();//获取结果
		assertEquals(result, "redirectCycleShow");
	}

	@Test
	public void testEditCard() {
		Map formMap = new HashMap();//定义form
		String name[] = { "1" };
		String id[] = { "1" };
		String cardId[] = { "1" };
		String periodId[] = { "1" };
		formMap.put("periodId", periodId);
		formMap.put("name", name);
		formMap.put("id", id);
		formMap.put("cardId", cardId);
		cycleCellAction.setFormMap(formMap);
		cardService.editCard(cardId[0], name[0], periodId[0]);
		EasyMock.expectLastCall().anyTimes();
		control.replay();//
		String result = cycleCellAction.editCard();//获取结果
		assertEquals(result, "redirectCycleShow");
	}

	@Test
	public void testDeleteCard() {
		Map formMap = new HashMap();//定义form
		String id[] = { "1" };
		String cardId[] = { "1" };
		formMap.put("id", id);
		formMap.put("cardId", cardId);
		cycleCellAction.setFormMap(formMap);
		cardService.deleteCard(cardId[0]);
		EasyMock.expectLastCall().anyTimes();
		control.replay();//
		String result = cycleCellAction.deleteCard();//获取结果
		assertEquals(result, "redirectCycleShow");
	}

	@Test
	public void testEnableCard() {
		String ids = "1";
		cycleCellAction.setIds(ids);
		EasyMock.expect(cardService.setAbleItem(ids, "1")).andReturn(true);
		control.replay();//
		String result = cycleCellAction.enableCard();//获取结果
		assertEquals(result, cycleCellAction.RIGHT);
	}

	@Test
	public void testDisableCard() {
		String ids = "1";
		cycleCellAction.setIds(ids);
		EasyMock.expect(cardService.setAbleItem(ids, "0")).andReturn(true);
		control.replay();//
		String result = cycleCellAction.disableCard();//获取结果
		assertEquals(result, cycleCellAction.RIGHT);
	}

	@Test
	public void testShowCard() {
		Map formMap = new HashMap();//定义form
		String id[] = { "1" };
		String cardId[] = { "1" };
		formMap.put("id", id);
		formMap.put("cardId", cardId);
		cycleCellAction.setFormMap(formMap);
		Card c = new Card();
		c.setShutdownTime(0);
		c.setProcessTime(0);
		EasyMock.expect(cardService.getCard(cardId[0])).andReturn(c);
		control.replay();//
		String result = cycleCellAction.showCard();//获取结果
		assertEquals(result, "input");
	}

	@Ignore()
	@Test
	public void testSaveCardDeal() {
		Map formMap = new HashMap();//定义form
		String cardId[] = { "0" };
		String shutdownTime[] = { "0" };
		String processTime[] = { "0" };
		String tools[] = { "0" };
		String measure[] = { "0" };
		String attention[] = { "0" };
		String other[] = { "0" };
		String methods[] = { "0" };
		String technicalStandards[] = { "0" };
		String remark[] = { "0" };
		String id[] = { "0" };
		String equipName = "上变频器";
		Equip equip = new Equip();
		equip.setId(1L);
		equip.setName(equipName);
		formMap.put("cardId", cardId);
		formMap.put("shutdownTime", shutdownTime);
		formMap.put("processTime", processTime);
		formMap.put("tools", tools);
		formMap.put("measure", measure);
		formMap.put("attention", attention);
		formMap.put("other", other);
		formMap.put("methods", methods);
		formMap.put("technicalStandards", technicalStandards);
		formMap.put("remark", remark);
		formMap.put("id", id);
		cycleCellAction.setFormMap(formMap);
		cycleCellAction.setEquipName(equipName);
		//EasyMock.expect(cardService.getCard(cardId[0])).andReturn(c);
		EasyMock.expect(equipService.getByName(equipName)).andReturn(equip);
		cardService.saveCard(cardId[0], shutdownTime[0], processTime[0], tools[0], measure[0], attention[0], other[0],
				methods[0], technicalStandards[0], remark[0], equip);
		EasyMock.expectLastCall().anyTimes();
		control.replay();//
		String result = cycleCellAction.saveCardDeal();//获取结果
		assertEquals(result, "redirectCycleShow");
	}
}
