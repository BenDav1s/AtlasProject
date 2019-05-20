package character;

import java.util.List;

public class User {
	private String username;
	private String password;
	private boolean loggedIn;
	private List<Player> characters;
	public String getName() {
		return username;
	}
	public void setName(String name) {
		this.username = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isLoggedIn() {
		return loggedIn;
	}
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	public List<Player> getCharacters() {
		return characters;
	}
	public void setCharacters(List<Player> characters) {
		this.characters = characters;
	}
	
	
	
}
