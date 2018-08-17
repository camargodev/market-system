package engsoft.allfood.util;

public class ReturnMessage {

	private boolean success;
	private Object message;

	public ReturnMessage() {
		super();
	}

	public ReturnMessage(boolean success, Object message) {
		super();
		this.success = success;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

}
