/**
 * 
 */
package gov.abrs.etms.service.repair;

import static org.junit.Assert.*;
import gov.abrs.etms.model.repair.Card;

import java.util.List;

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
public class CardServiceTest extends DatabaseTestCase {

	@Autowired
	private CardService cardService;//检修卡片
	private int cardCount = 0;

	@Before
	public void setup() {
		cardCount = this.getRowsCount(Card.class);//初始化count
	}

	//测试-增 删 改 查
	//测试保存卡片
	@Test
	public void testSaveCard() {
		String id = "1";
		String shutdownTime = "10.2";
		String processTime = "12";
		String tools = "酒精、毛刷、白布、试电笔、强光手电、螺丝刀";
		String measure = "断低压配电室闸刀、4CB1、4CB2并挂牌、验电、放电";
		String attention = "确保无电";
		String other = "用试电笔验母线排E1、E2、E3有无电，无电方可进行检修";
		String methods = "1.  用毛刷清洁3TB1端子板表面尘土，并用螺丝刀紧固各端子。";
		String technicalStandards = "技术标准";
		String remark = "备注";
		cardService.saveCard(id, shutdownTime, processTime, tools, measure, attention, other, methods,
				technicalStandards, remark, null);
		Card card = cardService.getCard(id);//查找
		assertNotNull(card);
		assertEquals(card.getShutdownTime() + "", "10.2");
		assertEquals(card.getProcessTime() + "", "12.0");
		assertEquals(card.getTools() + "", "酒精、毛刷、白布、试电笔、强光手电、螺丝刀");
		assertEquals(card.getMeasure() + "", "断低压配电室闸刀、4CB1、4CB2并挂牌、验电、放电");
		assertEquals(card.getAttention() + "", "确保无电");
		assertEquals(card.getOther() + "", "用试电笔验母线排E1、E2、E3有无电，无电方可进行检修");
		assertEquals(card.getMethods() + "", "1.  用毛刷清洁3TB1端子板表面尘土，并用螺丝刀紧固各端子。");
		assertEquals(card.getTechnicalStandards() + "", "技术标准");
		assertEquals(card.getRemark() + "", "备注");

	}

	@Test
	public void testAddCard() {
		String cellId = "1";
		String name = "校正高末栅偏压。";
		String periodId = "1";
		String id = cardService.addCard(cellId, name, periodId);//保存
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(Card.class, cardCount + 1);
		Card card = cardService.getCard(id);//查找
		assertNotNull(card);
		assertEquals(card.getPeriod().getId() + "", "1");
		assertEquals(card.getName(), "校正高末栅偏压。");
	}

	//测试删除卡片
	@Test
	public void testDeleteCard() {
		String id = "1";
		cardService.deleteCard(id);
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(Card.class, cardCount - 1);
		Card card = cardService.getCard(id);//查找
		assertNull(card);
	}

	//测试修改名称
	@Test
	public void testEditCard() {
		String id = "1";
		String name = "校正高末栅偏压。";
		String periodId = "2";
		cardService.editCard(id, name, periodId);
		this.flushSessionAndCloseSessionAndNewASession();
		Card card = cardService.getCard(id);//查找
		assertNotNull(card);
		assertEquals(card.getName(), "校正高末栅偏压。");
		assertEquals(card.getPeriod().getId() + "", "2");
	}

	//测试启用/停用卡片
	@Test
	public void testSetAbleItem() {
		String id = "1";
		cardService.setAbleItem(id, "0");
		Card card = cardService.getCard(id);//查找		
		assertNotNull(card);
		assertEquals(card.getActive(), "0");
	}

	//测试删除前校验
	@Test
	public void testCheckDelete() {
		String id = "1";//1不可以 		
		boolean flag = cardService.checkDelete(id);
		assertEquals(flag, false);
	}

	@Test
	public void testGetCards() {
		Long id = 1L;
		String active = "1";
		List<Card> cardList = cardService.getCards(id, active);
		assertNotNull(cardList);
		assertEquals(cardList.size(), 1);
	}

}
