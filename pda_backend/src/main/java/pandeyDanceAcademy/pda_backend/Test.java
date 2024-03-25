package pandeyDanceAcademy.pda_backend;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String string = "fullUrl http://localhost:8882/api/v1/auth/addNewUser";
		int lastIndexOf = string.lastIndexOf('/');
		System.out.println("fullUrl {}" + string + " , lastIndexOf " + lastIndexOf + " {}");
		string = string.substring(0, lastIndexOf);
		System.out.println("fullUrl {}" + string + " , lastIndexOf " + lastIndexOf + " {}");

	}

}
