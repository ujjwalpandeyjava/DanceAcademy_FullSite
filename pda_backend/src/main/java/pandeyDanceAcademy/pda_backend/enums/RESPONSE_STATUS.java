package pandeyDanceAcademy.pda_backend.enums;

public enum RESPONSE_STATUS {
	MESSAGE("Message"), SUCCESS("Success"), SUCCESSFULL("Successfull"), ERROR("Error"), EMPTY_BODY("Empty body");

	private String value;

	private RESPONSE_STATUS(String val) {
		this.value = val;
	}

	public String getVal() {
		return this.value;
	}
}
