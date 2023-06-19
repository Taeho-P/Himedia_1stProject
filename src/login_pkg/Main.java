package login_pkg;

public class Main {

	public static void main(String[] args) {
		Login_Frame lf = new Login_Frame();

		
		String[] L_info = lf.loginSuccess();
		
		
		for (int i = 0; i < L_info.length; i++) {
			System.out.println(L_info[0]);
		}
		
		
	}

}
