package com.sokolmeteo.sokol.tcp;

import com.sokolmeteo.dao.model.Record;
import com.sokolmeteo.dao.model.WeatherData;
import com.sokolmeteo.dao.repo.RecordDao;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class TcpInteractionImpl implements TcpInteraction {
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
        Record record = recordDao.save(new Record(data.getAuthor()));
        executors.execute(() -> {
            long start = System.nanoTime();
            List<Callable<String>> callables = new ArrayList<>();
            for (String message : data.getBlackMessages())
                callables.add(() -> tcpClient.execute(data.getLoginMessage(), message));
            try {
                List<Future<String>> results = executors.invokeAll(callables);
                List<String> messages = new ArrayList<>();
                for (Future<String> future : results)
                    if (!future.get().equals("OK")) messages.add(future.get());

                if (messages.size() > 0) record.setFault(messages.toString());
                else record.setSuccess();
                System.out.println("time on process: " + (System.nanoTime() - start));
            } catch (InterruptedException | ExecutionException e) {
                record.setFault("Внутренняя ошибка");
            }
            recordDao.save(record);
        });
        return record.getId();
    }
}
