package com.andreipall.art.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.andreipall.art.entities.ExhibitionImage;

@Repository
public interface ExhibitionImageRepository extends JpaRepository<ExhibitionImage, Integer> {
	
	public ExhibitionImage findByExhibition_SlugAndImageName(String slug, String imageName);
}
