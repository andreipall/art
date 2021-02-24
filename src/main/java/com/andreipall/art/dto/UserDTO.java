package com.andreipall.art.dto;

import javax.validation.constraints.NotEmpty;

import com.andreipall.art.validator.PasswordsMatch;

@PasswordsMatch
public class UserDTO {
	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
	@NotEmpty(message = "password confirmation is required.")
	private String passwordConfirmation;
	private String role;
	private boolean enabled = true;

	public UserDTO() {
		super();
	}

	public UserDTO(String username, String password, String passwordConfirmation, String role, boolean enabled) {
		super();
		this.username = username;
		this.password = password;
		this.passwordConfirmation = passwordConfirmation;
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

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
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
