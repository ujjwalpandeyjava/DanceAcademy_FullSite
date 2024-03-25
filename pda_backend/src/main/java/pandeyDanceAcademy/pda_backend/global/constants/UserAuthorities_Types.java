package pandeyDanceAcademy.pda_backend.global.constants;

public class UserAuthorities_Types {
	public static final String FACULTY = "FACULTY";
	public static final String ADMIN = "ADMIN";
	public static final String USER = "USER";

	public static boolean isValidStatus(String status) {
		return status.equals(FACULTY) || status.equals(ADMIN) || status.equals(USER);
	}

	public static String getAllStatus() {
		return String.join(", ", FACULTY, ADMIN, USER);
	}
}
