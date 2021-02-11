package com.andreipall.art.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class ExhibitionImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private String imageName;
	@Column(nullable = false)
	private String imageType;
	@Lob
	private byte[] imageData;
	@ManyToOne
	@JoinColumn(name = "exhibition_id")
	private Exhibition exhibition;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Exhibition getExhibition() {
		return exhibition;
	}

	public void setExhibition(Exhibition exhibition) {
		this.exhibition = exhibition;
	}
}
