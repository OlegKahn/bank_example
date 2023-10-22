package com.bank.history.service;

import com.bank.history.entity.History;
import org.springframework.data.history.RevisionMetadata;
import java.util.List;
import java.util.Map;

/**
 * Standard service interface
 */
public interface HistoryService {

    /**
     * Unimplemented method to get all History
     */
    List<History> getAll();

    /**
     * Unimplemented method for getting History by id
     */
    History getById(long id);

    /**
     * Unimplemented method for saving History
     */
    History saveNew(History history);

    /**
     * Unimplemented method for updating History
     */
    History saveOld(History history);

    /**
     * Unimplemented method for deleting History by id
     */
    void delete(long id);

    /**
     * Unimplemented method for getting all History changes by id
     */
    Map<RevisionMetadata<Long>, History> getAllAuditById(long id);

    /**
     * Unimplemented method for getting the last change History by id
     */
    Map<RevisionMetadata<Long>, History> getLastAuditById(long id);
}
