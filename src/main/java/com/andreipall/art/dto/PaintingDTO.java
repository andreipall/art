package com.andreipall.art.dto;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

public class PaintingDTO {
	@NotEmpty
	private String name;
	@NotEmpty
	private String description;
	private MultipartFile image;

	public PaintingDTO() {
		super();
	}

	public PaintingDTO(String name, String description, MultipartFile image) {
		super();
		this.name = name;
		this.description = description;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

}
