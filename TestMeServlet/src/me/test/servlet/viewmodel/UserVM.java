package me.test.servlet.viewmodel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.test.entity.user.User;

public class UserVM implements ViewModel {
	
	private Set<String> users = new HashSet<>();
	
	private String activeUser = null;
	
	public void addUser(String u) {
		users.add(u);
	}
	
	public void removeUser(User u) {
		users.remove(u);
	}
	
	public void addUser(List<String> u) {
		users.addAll(u);
	}
	
	public void clear() {
		users.clear();
	}
	
	public void setActiveUser(String u) {
		activeUser = u;
	}
	
	@Override
	public Map<String, Object> provideData() {
		Map<String, Object> ret = new HashMap<>();
		
		ret.put("isLoggedIn", Boolean.TRUE);
		ret.put("active-user", Boolean.TRUE);
		ret.put("loggedIn-username", activeUser);
		ret.put("users", getUserData());
		
		return ret;
	}
	
	private List<Map<String, Object>> getUserData() {
		List<Map<String, Object>> ret = new LinkedList<>();
		
		users.stream()
			.sorted((u1, u2) -> u1.compareTo(u2))
			.forEach(u -> {
				Map<String, Object> tmp = new HashMap<>();
				tmp.put("username", u);
				tmp.put("active", u.equals(activeUser));
				ret.add(tmp);
			});
		
		return ret;
	}

}
