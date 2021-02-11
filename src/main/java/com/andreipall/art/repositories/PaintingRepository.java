package com.andreipall.art.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andreipall.art.entities.Painting;

@Repository
public interface PaintingRepository extends JpaRepository<Painting, Integer> {
	Painting findBySlug(String slug);
	public List<Painting> findTop6ByOrderByCreatedAtDesc();
}
