package com.andreipall.art.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andreipall.art.entities.PaintingComment;

@Repository
public interface PaintingCommentRepository extends JpaRepository<PaintingComment, Integer> {

}
