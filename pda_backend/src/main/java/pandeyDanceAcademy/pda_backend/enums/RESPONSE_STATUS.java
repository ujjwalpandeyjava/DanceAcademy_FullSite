package pandeyDanceAcademy.pda_backend.enums;

public enum RESPONSE_STATUS {
	MESSAGE("Message"), SUCCESS("Success"), SUCCESSFUL("Successful"), ERROR("Error");

	private String value;

	private RESPONSE_STATUS(String val) {
		this.value = val;
	}

	public String getVal() {
		return this.value;
	}
}
