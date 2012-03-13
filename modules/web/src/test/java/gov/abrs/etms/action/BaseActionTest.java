package gov.abrs.etms.action;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Ignore;

@Ignore
public class BaseActionTest {

	protected Boolean needVerify = true;
	protected IMocksControl control = EasyMock.createControl();

	@After
	public void tearDown() {
		if (needVerify) {
			control.verify();
		}
	}
}
