package com.andreipall.art.dto;

import javax.validation.constraints.NotEmpty;

public class CommentDTO {
	@NotEmpty
	private String name;
	@NotEmpty
	private String comment;

	public CommentDTO() {

	}

	public CommentDTO(String name, String comment) {
		super();
		this.name = name;
		this.comment = comment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
