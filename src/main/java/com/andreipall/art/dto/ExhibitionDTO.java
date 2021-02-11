package com.andreipall.art.dto;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

public class ExhibitionDTO {
	@NotEmpty
	private String name;
	@NotEmpty
	private String description;
	private MultipartFile image;
	private MultipartFile[] images;

	public ExhibitionDTO() {
		super();
	}

	public ExhibitionDTO(String name, String description, MultipartFile image, MultipartFile[] images) {
		super();
		this.name = name;
		this.description = description;
		this.image = image;
		this.images = images;
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

	public MultipartFile[] getImages() {
		return images;
	}

	public void setImages(MultipartFile[] images) {
		this.images = images;
	}

}
