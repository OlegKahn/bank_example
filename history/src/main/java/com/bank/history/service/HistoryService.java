package com.bank.history.service;

import com.bank.history.model.History;

import java.util.List;

public interface HistoryService {

    List<History> getAll();

    History getById(long id);

    History saveNew(History history);

    void delete(long id);


}
