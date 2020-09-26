package com.sokolmeteo.dao.repo;

import com.sokolmeteo.dao.model.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordDao extends PagingAndSortingRepository<Record, Long> {
    Optional<Record> findByIdAndAuthor(Long id, String author);

    Page<Record> findAllByAuthor(String author, Pageable request);
}
