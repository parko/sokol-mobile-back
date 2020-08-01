package com.sokolmeteo.dao.repo;

import com.sokolmeteo.dao.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogDao extends JpaRepository<Log, Long> {
    Log findByIdAndAuthor(Long id, String author);
}
