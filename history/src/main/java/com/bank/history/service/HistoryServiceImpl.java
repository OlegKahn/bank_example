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
        final List<History> allHistory = historyDao.findAll();
        if (allHistory.isEmpty()) {
            throw new NoSuchHistoryException("Data base doesn't have any History");
        } else {
            return allHistory;
        }
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
                -> new NoSuchHistoryException("Impossible to find one! " +
                "There is no such history with id = " + id));
    }

    /**
     * Метод, который сохраняет в БД History
     * @param history сохраняемый объект History
     * @return History
     */
    @Override
    public History saveNew(History history) {

        if (history.getId() == 0 && isValueLessThanZero(history)) {
            return historyDao.save(history);
        } else {
            throw new NoSuchHistoryException("Warning! Incorrect entered values: " +
                    "Id must be 0 or empty, but id = " + history.getId() + ", " +
                    incorrectData(history));
        }
    }

    /**
     * Метод, который обновляет в БД History
     * @param history объект History из БД
     * @return History
     */
    @Override
    public History saveOld(History history) {
        final long id = history.getId();
        historyDao.findById(id).orElseThrow(()
                -> new NoSuchHistoryException("Impossible to update! " +
                "There isn't exist history with id = " + id));

        if (isValueLessThanZero(history)) {
            return historyDao.save(history);
        } else {
            throw new NoSuchHistoryException("Warning! One of values is less than 0: " +
            incorrectData(history));
        }
    }

    /**
     * Метод, который удаляет History по id
     * @param id удаляет по нему History
     */
    @Override
    public void delete(long id) {
        final History history = historyDao.findById(id).orElseThrow(()
                -> new NoSuchHistoryException("Impossible to delete! " +
                "There is not exist history with id = " + id));
        historyDao.delete(history);
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
        if (revisions.isEmpty()) {
            throw new NoSuchHistoryException("Impossible to find all! " +
                    "There is no such history changes with id = " + id);
        } else {
            revisions.stream().forEach(r -> hashMap.put(r.getMetadata(), r.getEntity()));
        }
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

    private String incorrectData(History history) {
        return "TransferAuditId must be > 0, but transferAuditId = " +
                history.getTransferAuditId() +
                ", ProfileAuditId must be > 0, but profileAuditId = " +
                history.getProfileAuditId() +
                ", AccountAuditId must be > 0, but accountAuditId = " +
                history.getAccountAuditId() +
                ", AntiFraudAuditId must be > 0, but antiFraudAuditId = " +
                history.getAntiFraudAuditId() +
                ", PublicBankInfoAuditId must be > 0, but publicBankInfoAuditId = " +
                history.getPublicBankInfoAuditId() +
                ", AuthorizationAuditId must be > 0, but authorizationAuditId = " +
                history.getAuthorizationAuditId();
    }

    private boolean isValueLessThanZero(History history) {
        return history.getTransferAuditId() > 0 &&
                history.getProfileAuditId() > 0 &&
                history.getAccountAuditId() > 0 &&
                history.getAntiFraudAuditId() > 0 &&
                history.getAuthorizationAuditId() > 0 &&
                history.getPublicBankInfoAuditId() > 0;
    }
}
