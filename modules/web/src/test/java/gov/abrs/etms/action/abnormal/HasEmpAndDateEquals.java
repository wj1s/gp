package gov.abrs.etms.action.abnormal;

import static org.easymock.EasyMock.*;
import gov.abrs.etms.model.abnormal.Abnormal;

import org.easymock.IArgumentMatcher;

public class HasEmpAndDateEquals implements IArgumentMatcher {
	private final Abnormal expected;

	public HasEmpAndDateEquals(Abnormal expected) {
		this.expected = expected;
	}

	public static <T extends Abnormal> T hasEmpAndDateEquals(T in) {
		reportMatcher(new HasEmpAndDateEquals(in));
		return null;
	}

	public boolean matches(Object actual) {
		if (!(actual instanceof Abnormal)) {
			return false;
		}
		Abnormal res = (Abnormal) actual;
		if (actual == expected && res.getUpdDate() != null && res.getEmpName() != null) {
			return true;
		} else {
			return false;
		}
	}

	public void appendTo(StringBuffer buffer) {
		buffer.append("abnormal对象必须先设置empName和UdpDate");

	}
}