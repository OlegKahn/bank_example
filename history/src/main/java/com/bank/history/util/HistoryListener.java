package com.bank.history.util;

import org.hibernate.envers.RevisionListener;

/**
 * Класс предназначенный для описания бизнес-логики
 * дополнительных полей в HistoryRevInfo
 */
public class HistoryListener implements RevisionListener {
    /**
     * Метод получает объект HistoryRevInfo, выполняет некую логику
     * в тот момент когда добавляется запись в таблицу revinfo
     * @param o сам обьект, который преобразуем и вносим изменения
     */
    @Override
    public void newRevision(Object o) {
        final HistoryRevInfo revInfo = (HistoryRevInfo) o;

        revInfo.setUsername("Freeman");
    }
}
