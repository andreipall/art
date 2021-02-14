package com.andreipall.art.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

@Entity
public class Exhibition extends DateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false, unique = true)
	private String slug;
	@Column(nullable = false)
	private String imageName;
	@Column(nullable = false)
	private String imageType;
	@Lob
	private byte[] imageData;
	@Column(columnDefinition = "TEXT", nullable = false)
	private String description;
	@OneToMany(mappedBy = "exhibition", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ExhibitionImage> images = new ArrayList<>();

	public Exhibition() {
	}

	public Exhibition(String name, String slug, String imageName, String imageType, byte[] imageData,
			String description) {
		super();
		this.name = name;
		this.slug = slug;
		this.imageName = imageName;
		this.imageType = imageType;
		this.imageData = imageData;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ExhibitionImage> getImages() {
		return images;
	}
	
	public void addAll(List<ExhibitionImage> images) {
		this.images.addAll(images);
	}

	public void setImages(List<ExhibitionImage> images) {
		this.images.clear();
		this.images.addAll(images);
	}

}
