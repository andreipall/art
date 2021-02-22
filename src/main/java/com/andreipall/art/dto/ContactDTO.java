package com.andreipall.art.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class ContactDTO {
	@NotEmpty
	private String name;
	@Email
	@NotEmpty
	private String email;
	@NotEmpty
	private String message;

	public ContactDTO() {

	}

	public ContactDTO(String name, String email, String message) {
		super();
		this.name = name;
		this.email = email;
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
