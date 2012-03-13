package gov.abrs.etms.action.duty;

import static org.easymock.EasyMock.*;
import gov.abrs.etms.model.duty.Duty;

import org.easymock.IArgumentMatcher;

public class AssembllyModelUtil implements IArgumentMatcher {
	private final Duty expected;

	public AssembllyModelUtil(Duty expected) {
		this.expected = expected;
	}

	public static Duty setModelId(Duty in) {
		reportMatcher(new AssembllyModelUtil(in));
		return null;
	}

	@Override
	public void appendTo(StringBuffer buffer) {}

	@Override
	public boolean matches(Object actual) {
		expected.setId(1L);
		return true;
	}

}