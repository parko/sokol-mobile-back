package com.sokolmeteo.sokol.tcp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sokolmeteo.dao.model.Record;
import com.sokolmeteo.dao.model.WeatherData;
import com.sokolmeteo.dao.repo.RecordDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class TcpInteractionImpl implements TcpInteraction {
    private final static Logger logger = LoggerFactory.getLogger(TcpInteractionImpl.class);

    private final ExecutorService executors;
    private final RecordDao recordDao;
    private final TcpClient tcpClient;

    public TcpInteractionImpl(ExecutorService executors, RecordDao recordDao, TcpClient tcpClient) {
        this.executors = executors;
        this.recordDao = recordDao;
        this.tcpClient = tcpClient;
    }

    @Override
    public Long send(WeatherData data) {
        Record record = recordDao.save(new Record(data.getAuthor(), data.getStation(), data.getStart(), data.getEnd()));
        logger.info("Sending data " + record.getId());
        executors.execute(() -> {
            long start = System.currentTimeMillis();
            List<Callable<String>> callables = new ArrayList<>();
            for (String message : data.getBlackMessages())
                callables.add(() -> tcpClient.execute(data.getLoginMessage(), message));
            try {
                List<Future<String>> results = executors.invokeAll(callables);
                Set<String> messages = new HashSet<>();
                for (Future<String> future : results)
                    if (!future.get().equals("OK")) messages.add(future.get());

                if (messages.size() > 0) record.setFault(new ObjectMapper().writeValueAsString(messages));
                else record.setSuccess();
                logger.info("Sending data {} complete in {} sec.", record.getId(), (System.currentTimeMillis() - start) / 1000);
            } catch (InterruptedException | ExecutionException | JsonProcessingException e) {
                logger.warn("Exception on sending data {}: {}", record.getId(), e);
                record.setFault("[\"Internal server error\"]");
            }
            recordDao.save(record);
        });
        return record.getId();
    }
}
