package com.andreipall.art.services;

import org.springframework.data.domain.Page;

import com.andreipall.art.entities.Painting;
import com.andreipall.art.entities.PaintingComment;

public interface PaintingService {
	public abstract Page<Painting> findPaginated(int pageNo, int pageSize);
	public abstract Painting findById(int id);
	public abstract Painting findBySlug(String slug);
	public abstract void addPainting(Painting painting);
	public abstract void addPaintingComment(PaintingComment paintingComment);
	public abstract void deletePaintingComment(PaintingComment paintingComment);
	public abstract void savePainting(Painting painting);
}
