package com.bank.history.util;

import org.hibernate.envers.RevisionListener;

/**
 * A class designed to describe business logic
 * additional fields in HistoryRevInfo
 */
public class HistoryListener implements RevisionListener {
    /**
     * The method receives a HistoryRevInfo object and performs some logic
     * at the moment when an entry is added to the revinfo table
     * @param o the object itself, which we transform and make changes
     */
    @Override
    public void newRevision(Object o) {
        final HistoryRevInfo revInfo = (HistoryRevInfo) o;

        revInfo.setUsername("Freeman");
    }
}
