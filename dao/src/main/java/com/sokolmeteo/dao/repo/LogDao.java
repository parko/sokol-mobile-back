package com.sokolmeteo.dao.repo;

import com.sokolmeteo.dao.model.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogDao extends PagingAndSortingRepository<Log, Long> {
    Log findByIdAndAuthor(Long id, String author);

    Page<Log> findAllByAuthor(String author, Pageable request);
}
