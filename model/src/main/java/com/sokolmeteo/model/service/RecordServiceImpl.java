package com.sokolmeteo.model.service;

import com.sokolmeteo.model.entity.Record;
import com.sokolmeteo.model.repo.RecordRepo;
import com.sokolmeteo.utils.exception.NoDataFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {
    private final RecordRepo recordRepo;

    public RecordServiceImpl(RecordRepo recordRepo) {
        this.recordRepo = recordRepo;
    }

    @Override
    public Record get(Long id, String login) {
        return recordRepo.findByIdAndAuthor(id, login)
                .orElseThrow(() -> new NoDataFoundException("No data found", "Данные не найдены"));
    }

    @Override
    public List<Record> getAll(String login) {
        return recordRepo.findAllByAuthor(login);
    }

    @Override
    public List<Record> getAll(String login, int page, int count) {
        return recordRepo.findAllByAuthor(login, PageRequest.of(page, count, Sort.by("created").descending())).getContent();
    }
}
