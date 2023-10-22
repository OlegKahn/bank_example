package com.bank.history.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The class is used to change, or rather add fields to the revinfo table
 */
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@RevisionEntity(HistoryListener.class)
@Table(name = "REVINFO", schema = "history")
public class HistoryRevInfo extends DefaultRevisionEntity {

    private String username;

}
