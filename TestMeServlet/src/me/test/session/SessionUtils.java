package me.test.session;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class SessionUtils {
	
	public static Map<String, Object> handleSession(HttpSession session) {
		Map<String, Object> ret = new HashMap<>();
		
		boolean isLoggedIn = !session.isNew();
				
		ret.put("loggedIn", isLoggedIn);
		if (isLoggedIn) { 
			ret.put("username", session.getAttribute("username"));
		} else {
			session.invalidate();
		}
		
		return ret;
	}
	
	public static boolean isLoggedIn(Map<String, Object> renderData) {
		boolean ret = false;
		if (renderData.containsKey("loggedIn")) {
			Object o = renderData.get("loggedIn");
			if (o != null && o instanceof Boolean) {
				ret = (Boolean)o;
			}
		}
		return ret;
	}

}
