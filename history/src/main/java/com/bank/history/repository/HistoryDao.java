package com.bank.history.repository;

import com.bank.history.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

/**
 * Стандартный интерфейс репозиторий
 */
@Repository
public interface HistoryDao extends JpaRepository<History, Long>,
        RevisionRepository<History, Long, Long> {
}
