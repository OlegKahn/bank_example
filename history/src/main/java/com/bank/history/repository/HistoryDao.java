package com.bank.history.repository;

import com.bank.history.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryDao extends JpaRepository<History, Long> {
}
