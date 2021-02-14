package com.andreipall.art.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.andreipall.art.entities.Exhibition;
import com.andreipall.art.entities.ExhibitionImage;
import com.andreipall.art.repositories.ExhibitionImageRepository;
import com.andreipall.art.repositories.ExhibitionRepository;

@Service
public class ExhibitionServiceImpl implements ExhibitionService {
	private final ExhibitionRepository exhibitionRepository;
	private final ExhibitionImageRepository exhibitionImageRepository;

	@Autowired
	public ExhibitionServiceImpl(ExhibitionRepository exhibitionRepository, ExhibitionImageRepository exhibitionImageRepository) {
		super();
		this.exhibitionRepository = exhibitionRepository;
		this.exhibitionImageRepository = exhibitionImageRepository;
	}

	@Override
	public Page<Exhibition> findPaginated(int pageNo, int pageSize) {
		Sort sort = Sort.by("createdAt").descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		return this.exhibitionRepository.findAll(pageable);
	}
	
	@Override
	public Exhibition findBySlug(String slug) {
		return this.exhibitionRepository.findBySlug(slug);
	}

	@Override
	public void addExhibition(Exhibition exhibition) {

		this.exhibitionRepository.save(exhibition);
	}

	@Override
	public Exhibition findById(int id) {
		return this.exhibitionRepository.getOne(id);
	}

	@Override
	public void saveExhibition(Exhibition exhibition) {
		this.exhibitionRepository.save(exhibition);
	}

	@Override
	public void deleteExhibition(Exhibition exhibition) {
		this.exhibitionRepository.deleteById(exhibition.getId());
	}

	@Override
	public List<Exhibition> findLatestExhibitions() {
		return this.exhibitionRepository.findTop3ByOrderByCreatedAtDesc();
	}

	@Override
	public ExhibitionImage findExhibitionImage(String slug, String imageName) {
		return this.exhibitionImageRepository.findByExhibition_SlugAndImageName(slug, imageName);
	}

}
