package com.bank.history.service;

import com.bank.history.entity.History;
import org.springframework.data.history.RevisionMetadata;
import java.util.List;
import java.util.Map;

/**
 * Стандартый интерфейс сервис
 */
public interface HistoryService {

    /**
     * Не реализованный метод для получения всех History
     */
    List<History> getAll();

    /**
     * Не реализованный метод для получения History по id
     */
    History getById(long id);

    /**
     * Не реализованный метод для сохранения History
     */
    History saveNew(History history);

    /**
     * Не реализованный метод для обновления History
     */
    History saveOld(History history);

    /**
     * Не реализованный метод для удаления History по id
     */
    void delete(long id);

    /**
     * Не реализованный метод для получения всех изменений History по id
     */
    Map<RevisionMetadata<Long>, History> getAllAuditById(long id);

    /**
     * Не реализованный метод для получения последнего изменения History по id
     */
    Map<RevisionMetadata<Long>, History> getLastAuditById(long id);
}
