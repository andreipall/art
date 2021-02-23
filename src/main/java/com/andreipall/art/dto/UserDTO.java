package com.andreipall.art.dto;

import javax.validation.constraints.NotEmpty;

public class UserDTO {
	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
	private String role;
	private boolean enabled;

	public UserDTO() {
		super();
	}

	public UserDTO(@NotEmpty String username, @NotEmpty String password, String role, boolean enabled) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
