package com.andreipall.art.services;

import java.util.List;
import org.springframework.data.domain.Page;
import com.andreipall.art.entities.Exhibition;
import com.andreipall.art.entities.ExhibitionImage;

public interface ExhibitionService {
	public abstract List<Exhibition> findLatestExhibitions();
	public abstract Page<Exhibition> findPaginated(int pageNo, int pageSize);
	public abstract Exhibition findById(int id);
	public abstract Exhibition findBySlug(String slug);
	public abstract void addExhibition(Exhibition exhibition);
	public abstract void saveExhibition(Exhibition exhibition);
	public abstract void deleteExhibition(Exhibition exhibition);
	public abstract ExhibitionImage findExhibitionImage(String slug, String imageName);
}
