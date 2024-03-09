package pandeyDanceAcademy.pda_backend.enums;

public enum EG {
	MESSAGE("Message");

	private String value;

	private EG(String val) {
		this.value = val;
	}

	public String getVal() {
		return this.value;
	}
}
