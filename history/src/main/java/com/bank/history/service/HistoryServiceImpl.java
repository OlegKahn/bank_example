package com.bank.history.service;

import com.bank.history.exception.NoSuchHistoryException;
import com.bank.history.entity.History;
import com.bank.history.repository.HistoryDao;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.data.history.Revisions;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A class that implements the HistoryService interface,
 * performs CRUD operations via HistoryDao
 */
@Service
@AllArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryDao historyDao;

    private final MeterRegistry meterRegistry;

    private final AtomicInteger atomicInteger;


    /**
     * The method gets all History from the database
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
     * The method gets History by id
     *
     * @exception NoSuchHistoryException throws
     * in the absence of History by id
     *
     * @param id searches for it History
     * @return History
     */
    @Override
    public History getById(long id) {
        return historyDao.findById(id).orElseThrow(()
                -> new NoSuchHistoryException("Impossible to find one! " +
                "There is no such history with id = " + id));
    }

    /**
     * Method that saves the History to database
     * @param history saved History object
     * @return History
     */
    @Override
    public History saveNew(History history) {

        if (history.getId() == 0 && isValueGreaterThanZero(history)) {

            meterRegistry.gauge("transfer_audit_id", atomicInteger);
            atomicInteger.set((int) history.getTransferAuditId());

            return historyDao.save(history);
        } else {
            throw new NoSuchHistoryException("Warning! Incorrect entered values: " +
                    "Id must be 0 or empty, but id = " + history.getId() + ", " +
                    incorrectData(history));
        }
    }

    /**
     * Method that updates the History database
     * @param history History object from the database
     * @return History
     */
    @Override
    public History saveOld(History history) {
        final long id = history.getId();
        historyDao.findById(id).orElseThrow(()
                -> new NoSuchHistoryException("Impossible to update! " +
                "There isn't exist history with id = " + id));

        if (isValueGreaterThanZero(history)) {
            return historyDao.save(history);
        } else {
            throw new NoSuchHistoryException("Warning! One of values is less than 0: " +
            incorrectData(history));
        }
    }

    /**
     * Method that deletes History by id
     * @param id deletes History using it
     */
    @Override
    public void delete(long id) {
        final History history = historyDao.findById(id).orElseThrow(()
                -> new NoSuchHistoryException("Impossible to delete! " +
                "There is not exist history with id = " + id));
        historyDao.delete(history);
    }

    /**
     * A method that gets all changes to a specific History by id
     * @param id searches for History changes using it
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
     * Method that gets the last change of a certain History by id
     * @param id searches for the latest History change
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

    /**
     * A method that returns text describing the error
     * @param history the argument in which it is revealed,
     *                that one or more fields have a null value
     * @return returns text
     */
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

    /**
     * A method that returns a boolean value
     * true - when none of the fields is less than zero,
     * false - when at least one field is less than zero
     * @param history object in which inequality is checked
     * @return boolean value
     */
    private boolean isValueGreaterThanZero(History history) {
        return history.getTransferAuditId() > 0 &&
                history.getProfileAuditId() > 0 &&
                history.getAccountAuditId() > 0 &&
                history.getAntiFraudAuditId() > 0 &&
                history.getAuthorizationAuditId() > 0 &&
                history.getPublicBankInfoAuditId() > 0;
    }
}
