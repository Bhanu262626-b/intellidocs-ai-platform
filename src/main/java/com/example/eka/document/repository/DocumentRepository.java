package com.example.eka.document.repository;

import com.example.eka.document.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository
    extends JpaRepository<DocumentEntity, Long> {
}
