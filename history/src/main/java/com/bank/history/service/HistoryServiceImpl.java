package com.bank.history.service;

import com.bank.history.exception.NoSuchHistoryException;
import com.bank.history.model.History;
import com.bank.history.repository.HistoryDao;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryDao historyDao;

    @Override
    public List<History> getAll() {
        return historyDao.findAll();
    }

    @Override
    public History getById(long id) {
        return historyDao.findById(id).orElseThrow(()
                -> new NoSuchHistoryException("There is no history with id = " + id));
    }

    @Override
    public History saveNew(History history) {
        return historyDao.save(history);
    }

    @Override
    public void delete(long id) {
        historyDao.deleteById(id);
    }
}
