package com.bank.history.service;

import com.bank.history.exception.NoSuchHistoryException;
import com.bank.history.entity.History;
import com.bank.history.repository.HistoryDao;
import lombok.AllArgsConstructor;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.data.history.Revisions;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс который реализирует интерфейс HistoryService,
 * через HistoryDao выполняет CRUD операции
 */
@Service
@AllArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryDao historyDao;


    /**
     * Метод получает все History из БД
     * @return List<History>
     */
    @Override
    public List<History> getAll() {
        return historyDao.findAll();
    }

    /**
     * Метод получает History по id
     *
     * @exception NoSuchHistoryException выбрасывает
     * при отсутствии History по id
     *
     * @param id ищет по нему History
     * @return History
     */
    @Override
    public History getById(long id) {
        return historyDao.findById(id).orElseThrow(()
                -> new NoSuchHistoryException("There is no history with id = " + id));
    }

    /**
     * Метод, который сохраняет в БД History
     * @param history сохраняемый объект History
     * @return History
     */
    @Override
    public History saveNew(History history) {
        return historyDao.save(history);
    }

    /**
     * Метод, который удаляет History по id
     * @param id удаляет по нему History
     */
    @Override
    public void delete(long id) {
        historyDao.deleteById(id);
    }

    /**
     * Метод, который получает все изменения определенного History по id
     * @param id ищет по нему History изменения
     * @return HashMap<RevisionMetadata<Long>, History>
     */
    @Override
    public Map<RevisionMetadata<Long>, History> getAllAuditById(long id) {
        final Map<RevisionMetadata<Long>, History> hashMap = new HashMap<>();
        final Revisions<Long, History> revisions = historyDao.findRevisions(id);
        revisions.stream().forEach(r -> hashMap.put(r.getMetadata(), r.getEntity()));
        return hashMap;
    }

    /**
     * Метод, который получает последнее изменение определенного History по id
     * @param id ищет по нему последнее History изменение
     * @return HashMap<RevisionMetadata<Long>, History>
     */
    @Override
    public Map<RevisionMetadata<Long>, History> getLastAuditById(long id) {
        final Map<RevisionMetadata<Long>, History> hashMap = new HashMap<>();
        final Revision<Long, History> revision = historyDao.findLastChangeRevision(id)
                .orElseThrow(() -> new NoSuchHistoryException("There is no change " +
                        "history with id = " + id));
        hashMap.put(revision.getMetadata(), revision.getEntity());
        return hashMap;
    }
}
