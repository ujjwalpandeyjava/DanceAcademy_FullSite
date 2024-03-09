package pandeyDanceAcademy.pda_backend.constants;

public class QueryStatuses {
	public static final String New = "New";
	public static final String Contacted = "Contacted";
	public static final String Resolved = "Resolved";

	public static boolean isValidStatus(String status) {
		return status.equals(New) || status.equals(Contacted) || status.equals(Resolved);
	}
}
