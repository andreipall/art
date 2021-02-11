package com.andreipall.art.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.andreipall.art.entities.Exhibition;

@Repository
public interface ExhibitionRepository extends JpaRepository<Exhibition, Integer> {
	Exhibition findBySlug(String slug);
	public List<Exhibition> findTop3ByOrderByCreatedAtDesc();
}
