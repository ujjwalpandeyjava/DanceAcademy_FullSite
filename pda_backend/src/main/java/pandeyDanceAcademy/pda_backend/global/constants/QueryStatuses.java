package pandeyDanceAcademy.pda_backend.global.constants;

public class QueryStatuses {
	public static final String New = "New";
	public static final String Contacted = "Contacted";
	public static final String Resolved = "Resolved";
	public static final String Admitted = "Admitted";

	public static boolean isValidStatus(String status) {
		return status.equals(New) || status.equals(Contacted) || status.equals(Resolved) || status.equals(Admitted);
	}

	public static String getAllStatus() {
		return String.join(", ", New, Contacted, Resolved, Admitted);
	}
}
