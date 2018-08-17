package engsoft.allfood.util;

public class LoginReturnMessage extends ReturnMessage {

	private boolean isAdmin;

	public LoginReturnMessage() {
		super();
	}

	public LoginReturnMessage(boolean success, boolean isAdmin, Object message) {
		super(success, message);
		this.isAdmin = isAdmin;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

}
