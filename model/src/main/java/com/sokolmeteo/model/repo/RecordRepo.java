package com.sokolmeteo.model.repo;

import com.sokolmeteo.model.entity.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepo extends PagingAndSortingRepository<Record, Long> {
    Optional<Record> findByIdAndAuthor(Long id, String author);

    List<Record> findAllByAuthor(String author);

    Page<Record> findAllByAuthor(String author, Pageable request);
}
