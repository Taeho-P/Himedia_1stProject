package login_pkg;

import db_pkg.ChildVo;
import main_pkg.OpenSgst;

public class Main {

	public static void main(String[] args) {
		 
		//Login_Frame lf = new Login_Frame();
		ChildVo cInfo = new ChildVo();
		new OpenSgst(cInfo);
	}

}
