package com.andreipall.art.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.andreipall.art.entities.Painting;
import com.andreipall.art.entities.PaintingComment;
import com.andreipall.art.repositories.PaintingCommentRepository;
import com.andreipall.art.repositories.PaintingRepository;

@Service
public class PaintingServiceImpl implements PaintingService {
	private final PaintingRepository paintingRepository;
	private final PaintingCommentRepository paintingCommentRepository;

	@Autowired
	public PaintingServiceImpl(PaintingRepository paintingRepository, PaintingCommentRepository paintingCommentRepository) {
		super();
		this.paintingRepository = paintingRepository;
		this.paintingCommentRepository = paintingCommentRepository;
	}

	@Override
	public Page<Painting> findPaginated(int pageNo, int pageSize) {
		Sort sort = Sort.by("createdAt").descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		return this.paintingRepository.findAll(pageable);
	}
	
	@Override
	public Painting findBySlug(String slug) {
		return this.paintingRepository.findBySlug(slug);
	}

	@Override
	public void addPainting(Painting painting) {

		this.paintingRepository.save(painting);
	}

	@Override
	public void addPaintingComment(PaintingComment paintingComment) {
		this.paintingCommentRepository.save(paintingComment);
	}

	@Override
	public Painting findById(int id) {
		return this.paintingRepository.getOne(id);
	}

	@Override
	public void savePainting(Painting painting) {
		this.paintingRepository.save(painting);
	}

	@Override
	public void deletePaintingComment(PaintingComment paintingComment) {
		this.paintingCommentRepository.deleteById(paintingComment.getId());
	}

	@Override
	public void deletePainting(Painting painting) {
		this.paintingRepository.deleteById(painting.getId());
	}
	
}
