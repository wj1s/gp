package gov.abrs.etms.service.exception;

public class CanNotDeleteException extends RuntimeException {

	private static final long serialVersionUID = -5504078515888944634L;

	public CanNotDeleteException() {
		super();
	}

	public CanNotDeleteException(String message, Throwable cause) {
		super(message, cause);
	}

	public CanNotDeleteException(String message) {
		super(message);
	}

	public CanNotDeleteException(Throwable cause) {
		super(cause);
	}

}
